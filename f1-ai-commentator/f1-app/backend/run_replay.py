"""
Phase 3 entrypoint — replay a race, fire triggers, send only enriched packets.

The gated loop:
    poller.stream() yields (snapshot, buffer_before, raw_lap_row)
      → triggers.evaluate(snapshot, buffer, driver_benchmarks, avg_pit_ms)
      → for each fired trigger:
            packet = commentary.assemble(trigger, snapshot, track_name)
            langflow_client.send(packet)

This is the key difference from Phase 2: commentary is only requested when a
trigger fires, not once per lap. A full ~78-lap race should yield a handful of
commentary calls, not one per lap.

Usage:
    python run_replay.py                  # uses REPLAY_YEAR/ROUND from env
    python run_replay.py --dry-run        # evaluate triggers, skip Langflow send
    python run_replay.py --max-laps 200   # stop after N laps processed
    python run_replay.py --tick 0         # no wall-clock pacing (fast dry-run)

Env vars (see .env.example):
    REPLAY_YEAR, REPLAY_ROUND, REPLAY_SESSION, REPLAY_TICK_S
    LANGFLOW_URL, LANGFLOW_FLOW_ID, LANGFLOW_API_KEY
"""
import argparse
import logging
import sys
from collections import Counter
from pathlib import Path

sys.path.insert(0, str(Path(__file__).parent))

from dotenv import load_dotenv
load_dotenv(Path(__file__).parent.parent / ".env")

from telemetry import ReplayPoller
from triggers import evaluate
from commentary import assemble
from history import get_sector_benchmarks, get_avg_pit_duration
import langflow_client as lf

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s %(levelname)s %(message)s",
    datefmt="%H:%M:%S",
)
log = logging.getLogger(__name__)


def main():
    parser = argparse.ArgumentParser(description="F1 replay → triggers → Langflow")
    parser.add_argument("--dry-run",  action="store_true",
                        help="Evaluate triggers and build packets, but skip Langflow send")
    parser.add_argument("--max-laps", type=int, default=None,
                        help="Stop after N laps processed (across all drivers)")
    parser.add_argument("--tick", type=float, default=None,
                        help="Override REPLAY_TICK_S (use 0 for an un-paced fast run)")
    args = parser.parse_args()

    poller = ReplayPoller() if args.tick is None else ReplayPoller(tick_s=args.tick)
    track_name = poller.track_name

    # Cache DB-backed baselines once. Sector benchmarks come back keyed by driver
    # in a single query; per-driver pit averages are fetched lazily and memoised.
    sector_bench_all = get_sector_benchmarks(track_name)
    avg_pit_cache: dict[str, float | None] = {}

    def avg_pit_for(driver: str):
        if driver not in avg_pit_cache:
            avg_pit_cache[driver] = get_avg_pit_duration(track_name, driver)
        return avg_pit_cache[driver]

    laps_seen     = 0
    triggers_fired = Counter()
    commentary_calls = 0

    log.info("Replaying %s — gated commentary loop (dry-run=%s)", track_name, args.dry_run)

    for snapshot, buffer, _raw in poller.stream():
        laps_seen += 1
        snap = snapshot.model_dump()
        driver = snapshot.driver

        fired = evaluate(
            snap,
            buffer,
            sector_bench_all.get(driver),
            avg_pit_for(driver),
        )

        for trig in fired:
            triggers_fired[trig.name] += 1
            packet = assemble(trig, snap, track_name)

            log.info(
                "🔔 LAP %3d | %-3s | %-14s | %s",
                snapshot.lap_num, driver, trig.name, _summarise(trig),
            )

            if not args.dry_run:
                response = lf.send(packet)
                text = lf.extract_text(response)
                commentary_calls += 1
                if text:
                    log.info("   ↳ %s", text)

        if args.max_laps and laps_seen >= args.max_laps:
            log.info("--max-laps %d reached, stopping.", args.max_laps)
            break

    # ── Summary — the acceptance signal ───────────────────────────────────────
    total_fired = sum(triggers_fired.values())
    log.info("─" * 60)
    log.info("Replay finished. %d laps processed.", laps_seen)
    log.info("Triggers fired: %d total — %s",
             total_fired, dict(triggers_fired) or "none")
    if not args.dry_run:
        log.info("Commentary calls sent to Langflow: %d", commentary_calls)
    if laps_seen:
        log.info("Gating ratio: %.1f%% of laps produced commentary",
                 100 * total_fired / laps_seen)


def _summarise(trig) -> str:
    """One-line human summary of a fired trigger's payload for the console."""
    d = trig.data
    if trig.name in ("fast_lap", "slow_lap"):
        return f"{d.get('current_ms')}ms vs avg {d.get('buffer_avg_ms')}ms ({d.get('delta_pct')}%)"
    if trig.name == "sector_outlier":
        return f"{d.get('sector')} {d.get('current_ms')}ms vs best {d.get('benchmark_ms')}ms (+{d.get('delta_pct')}%)"
    if trig.name == "pit_anomaly":
        return f"{d.get('direction')} pit {d.get('current_ms')}ms vs avg {d.get('avg_ms')}ms ({d.get('delta_pct')}%)"
    if trig.name == "drs_battle":
        return f"gap {d.get('gap_ahead_ms')}ms"
    return str(d)


if __name__ == "__main__":
    main()
