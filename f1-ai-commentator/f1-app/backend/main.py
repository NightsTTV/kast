"""
Phase 4 — FastAPI + WebSocket server.

Wraps the Phase 1-3 modules (telemetry, triggers, commentary, langflow_client,
history) in an async server that:
  1. replays a cached race in a background task,
  2. broadcasts telemetry + commentary to all connected WebSocket clients,
  3. exposes REST endpoints for status / history / standings.

Existing modules are imported and called as-is — nothing in them is modified.

Run:
    uvicorn main:app --reload --port 8000      # from backend/
    # or: python main.py
"""
import asyncio
import concurrent.futures
import contextlib
import logging
import os
from collections.abc import AsyncIterator
from pathlib import Path

from dotenv import load_dotenv
from fastapi import FastAPI, WebSocket, WebSocketDisconnect
from fastapi.middleware.cors import CORSMiddleware

# Same .env that run_replay.py uses. Load before reading any REPLAY_* / LANGFLOW_*.
# load_dotenv does NOT override real shell env vars, so REPLAY_TICK_S=0.2 on the
# command line still wins for quick tests.
load_dotenv(Path(__file__).parent.parent / ".env")

from telemetry import ReplayPoller          # noqa: E402  (import after load_dotenv)
from triggers import evaluate                # noqa: E402
from commentary import assemble              # noqa: E402
from langflow_client import send, extract_text  # noqa: E402
from history import (  # noqa: E402
    get_sector_benchmarks, get_avg_pit_duration,
    get_driver_history, get_pit_stops,
)

logging.basicConfig(level=logging.INFO, format="%(asctime)s %(levelname)s %(message)s",
                    datefmt="%H:%M:%S")
log = logging.getLogger("f1.main")

# ---------------------------------------------------------------------------
# Shared server state
# ---------------------------------------------------------------------------
clients: set[WebSocket] = set()

state: dict = {
    "running":        False,
    "current_lap":    0,
    "total_laps":     0,
    "triggers_fired": 0,
    "paused":         False,
    "poller":         None,
}

# Pause gate: when SET the replay advances; when CLEARED the loop blocks on
# resume_event.wait() before the next lap, freezing telemetry without dropping
# the WebSocket. Starts set (running).
resume_event = asyncio.Event()
resume_event.set()

# Latest known state per driver, for /api/standings and the connect-time replay.
#   driver_code -> {"position": int | None, "snapshot": dict}
standings: dict[str, dict] = {}

# Full per-driver lap history for the CURRENT race only (driver-focus view).
# Lets a client that joined mid-race backfill a driver's complete history via
# GET /api/race-history/{driver}.  driver_code -> [snapshot dict, ...] in lap order.
race_history: dict[str, list[dict]] = {}

# Dedicated pool for commentary (assemble + blocking Langflow POST). Kept separate
# from asyncio's default executor so a burst of slow Langflow calls can never
# starve the generator pulls that drive telemetry. This is what keeps telemetry
# flowing while commentary generates.
commentary_pool = concurrent.futures.ThreadPoolExecutor(
    max_workers=8, thread_name_prefix="commentary")

# The local LLM (Ollama/Granite behind Langflow) serves requests one at a time.
# Firing a burst of trigger calls at it makes every request queue and blow past
# the timeout. This limiter keeps concurrent Langflow calls low — mirroring the
# serial behaviour run_replay.py relied on. During a trigger burst commentary
# simply trails (which the spec allows); telemetry is never affected.
commentary_sem = asyncio.Semaphore(2)

_SENTINEL = object()   # signals "generator exhausted" across the thread boundary


def _next(gen):
    """next(gen) wrapped so StopIteration becomes a sentinel return value.

    StopIteration can't cross an asyncio.to_thread boundary cleanly, so we
    convert "race finished" into a plain value the async loop can check.
    """
    try:
        return next(gen)
    except StopIteration:
        return _SENTINEL


# ---------------------------------------------------------------------------
# Broadcast
# ---------------------------------------------------------------------------
async def broadcast(message: dict) -> None:
    """Send a JSON message to every connected client. Drop any that error out."""
    dead = []
    for ws in clients:
        try:
            await ws.send_json(message)
        except Exception:
            dead.append(ws)
    for ws in dead:
        clients.discard(ws)


# ---------------------------------------------------------------------------
# Commentary generation (spawned per fired trigger, never blocks the loop)
# ---------------------------------------------------------------------------
async def generate_commentary(trigger, snap: dict, track_name: str) -> None:
    """Build the context packet, call Langflow, broadcast COMMENTARY_UPDATE.

    Runs as its own task so the 1-3s Langflow round-trip doesn't stall telemetry.
    assemble() hits SQLite and send() is a blocking HTTP call, so both run in a
    thread. Any failure (Langflow down, malformed response) is logged and skipped
    — telemetry keeps flowing regardless.
    """
    loop = asyncio.get_running_loop()
    try:
        packet = await loop.run_in_executor(commentary_pool, assemble, trigger, snap, track_name)
        async with commentary_sem:   # cap concurrent Langflow calls
            text = await loop.run_in_executor(commentary_pool, lambda: extract_text(send(packet)))
    except Exception as exc:
        log.error("Commentary failed (trigger=%s lap=%s driver=%s): %s",
                  trigger.name, snap.get("lap_num"), snap.get("driver"), exc)
        return

    if not text:
        # Langflow unconfigured / down / empty response — skip, telemetry unaffected.
        return
    # driver is included so the frontend can colour-code / filter commentary by
    # driver without parsing it out of the text. (Sourced from the snapshot.)
    await broadcast({"type": "COMMENTARY_UPDATE",
                     "payload": {"trigger": trigger.name,
                                 "driver":  snap.get("driver"),
                                 "text":    text}})


# ---------------------------------------------------------------------------
# Replay loop (background task)
# ---------------------------------------------------------------------------
async def replay_loop() -> None:
    year    = int(os.getenv("REPLAY_YEAR", "2023"))
    rnd     = int(os.getenv("REPLAY_ROUND", "6"))
    session = os.getenv("REPLAY_SESSION", "R")
    tick    = float(os.getenv("REPLAY_TICK_S", "2.0"))

    # Fresh slate — matters when this loop is restarted by POST /api/reset.
    standings.clear()
    race_history.clear()
    state.update(running=False, current_lap=0, total_laps=0, triggers_fired=0,
                 paused=False, poller=None)
    resume_event.set()   # a fresh/reset race always starts un-paused

    # ReplayPoller.__init__ runs a blocking FastF1 sess.load(); do it off-loop.
    # tick_s=0 disables the poller's internal time.sleep — we pace in async below.
    log.info("Loading replay %s round %s %s ...", year, rnd, session)
    poller = await asyncio.to_thread(ReplayPoller, year, rnd, session, 0.0)
    state["poller"]     = poller
    # ASSUMPTION (verify): poller.laps is the public laps DataFrame; max LapNumber
    # is the race distance. There's no dedicated total_laps attribute on the poller.
    state["total_laps"] = int(poller.laps["LapNumber"].dropna().max())
    state["running"]    = True

    # Static per-race baselines, computed ONCE (not per lap):
    #  - sector benchmarks: a single query returns best sectors for ALL drivers.
    #  - avg pit duration: per-driver, so memoised lazily on first sighting.
    # Same values run_replay.py feeds triggers.evaluate(); they never change mid-race.
    track = poller.track_name
    benchmarks: dict = await asyncio.to_thread(get_sector_benchmarks, track)
    avg_pit_cache: dict[str, float | None] = {}

    async def avg_pit_for(driver: str):
        if driver not in avg_pit_cache:
            avg_pit_cache[driver] = await asyncio.to_thread(get_avg_pit_duration, track, driver)
        return avg_pit_cache[driver]

    log.info("Replay loaded: %s — %d laps, benchmarks for %d drivers. Streaming...",
             track, state["total_laps"], len(benchmarks))

    gen = poller.stream()   # yields (snapshot, buffer_before, raw_lap_row)
    while True:
        await resume_event.wait()   # blocks here while paused; resumes on /api/resume
        item = await asyncio.to_thread(_next, gen)
        if item is _SENTINEL:
            break
        snapshot, buffer, raw = item
        snap = snapshot.model_dump()
        driver = snapshot.driver
        state["current_lap"] = snapshot.lap_num

        # Track standings from the raw FastF1 lap row's Position. It can be NaN on
        # in/out laps — when so, KEEP the driver's previous position rather than
        # writing None (which would break the numeric sort). NaN != NaN catches it.
        pos = raw["Position"]
        valid = pos is not None and pos == pos
        position = int(pos) if valid else standings.get(driver, {}).get("position")
        standings[driver] = {"position": position, "snapshot": snap}

        # Record this lap in the per-driver current-race history (focus view).
        if driver not in race_history:
            race_history[driver] = []
        race_history[driver].append(snap)

        # Telemetry goes out every lap, unconditionally.
        await broadcast({"type": "TELEMETRY_UPDATE", "payload": snap})

        # Triggers gate commentary. evaluate() is pure/sync (no I/O) so it's safe
        # to call inline; it never raises (wraps each trigger in try/except).
        fired = evaluate(snap, buffer, benchmarks.get(driver), await avg_pit_for(driver))
        for trig in fired:
            state["triggers_fired"] += 1
            # Fire-and-forget: telemetry keeps streaming while commentary generates.
            asyncio.create_task(generate_commentary(trig, snap, track))

        await asyncio.sleep(tick)

    state["running"] = False
    log.info("Replay finished (%d laps streamed, %d triggers fired).",
             state["current_lap"], state["triggers_fired"])
    await broadcast({"type": "RACE_FINISHED", "payload": {}})


# The single replay background task. Held module-level so /api/reset can cancel
# and restart it.
replay_task: "asyncio.Task | None" = None


def start_replay() -> None:
    global replay_task
    replay_task = asyncio.create_task(replay_loop())


async def stop_replay() -> None:
    global replay_task
    if replay_task and not replay_task.done():
        replay_task.cancel()
        with contextlib.suppress(asyncio.CancelledError):
            await replay_task


@contextlib.asynccontextmanager
async def lifespan(app: FastAPI) -> AsyncIterator[None]:
    start_replay()
    log.info("Replay background task started.")
    try:
        yield
    finally:
        await stop_replay()
        log.info("Replay background task stopped.")


app = FastAPI(title="F1 Telemetry Commentary", lifespan=lifespan)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


# ---------------------------------------------------------------------------
# Routes
# ---------------------------------------------------------------------------
@app.get("/")
async def root():
    return {"status": "running"}


@app.get("/api/status")
async def api_status():
    return {
        "running":           state["running"],
        "current_lap":       state["current_lap"],
        "total_laps":        state["total_laps"],
        "connected_clients": len(clients),
        "triggers_fired":    state["triggers_fired"],
        "paused":            state["paused"],
    }


@app.post("/api/pause")
async def api_pause():
    """Freeze the replay where it is — telemetry stops advancing, WS stays open."""
    resume_event.clear()
    state["paused"] = True
    log.info("Replay paused at lap %s.", state["current_lap"])
    return {"ok": True, "paused": True}


@app.post("/api/resume")
async def api_resume():
    """Resume a paused replay from where it left off."""
    resume_event.set()
    state["paused"] = False
    log.info("Replay resumed at lap %s.", state["current_lap"])
    return {"ok": True, "paused": False}


@app.post("/api/reset")
async def api_reset():
    """Restart the replay from lap 1: cancel the running loop and start a fresh one.
    The new loop clears standings/state, so clients re-fill from the first lap."""
    await stop_replay()
    start_replay()
    log.info("Race reset — replay restarting from lap 1.")
    return {"ok": True, "message": "race reset"}


@app.get("/api/race-history/{driver}")
async def api_race_history(driver: str):
    """Full lap-by-lap history for one driver in the CURRENT race, plus a summary.

    Lets the driver-focus view backfill a client that joined mid-race; the live
    WebSocket TELEMETRY_UPDATE stream keeps it current after that."""
    code = driver.upper()
    laps = race_history.get(code, [])

    lap_times = [l["lap_time_ms"] for l in laps if l.get("lap_time_ms")]
    latest = laps[-1] if laps else {}

    def _best(key):
        vals = [l[key] for l in laps if l.get(key)]
        return min(vals) if vals else None

    summary = {
        "laps_completed": len(laps),
        "best_lap_ms":    min(lap_times) if lap_times else None,
        "avg_lap_ms":     round(sum(lap_times) / len(lap_times)) if lap_times else None,
        "best_s1_ms":     _best("sector_1_ms"),
        "best_s2_ms":     _best("sector_2_ms"),
        "best_s3_ms":     _best("sector_3_ms"),
        "compound":       latest.get("compound"),
        "tire_age":       latest.get("tire_age"),
    }
    return {"driver": code, "laps": laps, "summary": summary}


@app.get("/api/standings")
async def api_standings():
    """Current order, sorted by position. Drivers with no known position sink
    to the bottom rather than breaking the sort."""
    rows = [{"position": e["position"], **e["snapshot"]} for e in standings.values()]
    rows.sort(key=lambda r: (r["position"] is None, r["position"] or 1_000))
    return rows


@app.get("/api/history/{driver}")
async def api_history(driver: str):
    """Per-driver historical context at the current track: lap history + pit stops.
    Both queries hit SQLite, so they run off the event loop."""
    poller = state["poller"]
    if poller is None:
        return {"error": "replay not loaded yet"}
    track = poller.track_name
    code = driver.upper()
    laps = await asyncio.to_thread(get_driver_history, code, track)
    pits = await asyncio.to_thread(get_pit_stops, track, code)
    return {"driver": code, "track": track, "laps": laps, "pit_stops": pits}


@app.get("/api/race-history/{driver}")
async def api_race_history(driver: str):
    """Get the current race's lap history array for a given driver."""
    code = driver.upper()
    return race_history.get(code, [])


@app.websocket("/ws/race")
async def ws_race(websocket: WebSocket):
    await websocket.accept()
    clients.add(websocket)
    log.info("Client connected (%d total).", len(clients))
    # Send current standings immediately so a client joining mid-race isn't blank.
    # Reuses TELEMETRY_UPDATE (one per driver) rather than inventing a message type.
    try:
        for entry in standings.values():
            await websocket.send_json({"type": "TELEMETRY_UPDATE", "payload": entry["snapshot"]})
    except Exception:
        pass
    try:
        # We don't expect inbound messages; this await parks the coroutine until
        # the client disconnects, which raises WebSocketDisconnect.
        while True:
            await websocket.receive_text()
    except WebSocketDisconnect:
        pass
    finally:
        clients.discard(websocket)
        log.info("Client disconnected (%d total).", len(clients))


if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="0.0.0.0", port=8000)
