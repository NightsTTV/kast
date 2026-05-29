"""
FastF1 historical data loader and query helpers.

Verified against FastF1 v3.8.3 / 2023 Monaco Race.
Column notes:
  - DriverNumber in laps is dtype object (string) — cast to int before storing
  - RoundNumber from sess.event is numpy int64 — must cast to int() or SQLite stores as blob
  - LapTime / Sector*Time / PitInTime / PitOutTime are timedelta64[ns]
  - Pit stop duration = PitOutTime(lap N+1) - PitInTime(lap N), in milliseconds
  - Deleted column flags laps whose time was officially removed
"""
import logging
import time
from pathlib import Path
from typing import Optional
import pandas as pd
import fastf1
from fastf1.exceptions import RateLimitExceededError

from db import init_db, get_conn

log = logging.getLogger(__name__)

CACHE_DIR = Path(__file__).parent.parent / "data" / "cache"
CACHE_DIR.mkdir(parents=True, exist_ok=True)
fastf1.Cache.enable_cache(str(CACHE_DIR))

# Pit-lane transit time sanity bounds (ms). FastF1's PitOut-PitIn diff is the
# full pit-lane time (~20-30s). Values outside this band are artifacts:
# red-flag/safety-car stationary periods or lap-matching errors. Same filter
# the replay poller applies in telemetry._compute_pit_durations.
PIT_MIN_MS = 10_000
PIT_MAX_MS = 120_000


# ---------------------------------------------------------------------------
# Time conversion helpers
# ---------------------------------------------------------------------------

def _td_to_ms(td) -> Optional[int]:
    """Convert a timedelta (or NaT) to integer milliseconds."""
    if pd.isnull(td):
        return None
    return int(td.total_seconds() * 1000)


# ---------------------------------------------------------------------------
# Loader
# ---------------------------------------------------------------------------

def _already_loaded(year: int, round_number: int) -> bool:
    """Return True if this round already has results in the DB."""
    with get_conn() as conn:
        row = conn.execute(
            "SELECT 1 FROM race_results WHERE year=? AND round=? LIMIT 1",
            (year, round_number),
        ).fetchone()
    return row is not None


def load_session(year: int, event: str | int, session_type: str = "R") -> None:
    """
    Load a single race session from FastF1 and upsert into SQLite.

    Args:
        year: Season year, e.g. 2023
        event: Event name ("Monaco") or round number (6)
        session_type: "R" (Race), "Q" (Qualifying), "FP1"/"FP2"/"FP3", "S" (Sprint)
    """
    log.info(f"Loading {year} {event} {session_type} ...")

    sess = fastf1.get_session(year, event, session_type)
    sess.load(telemetry=False, weather=False, messages=False)

    laps = sess.laps
    results = sess.results
    # Cast to int — FastF1 returns numpy int64 which SQLite stores as a blob
    round_number = int(sess.event["RoundNumber"])
    track_name = sess.event["EventName"]
    country = sess.event["Country"]

    with get_conn() as conn:
        # -- track --------------------------------------------------------
        conn.execute(
            "INSERT OR IGNORE INTO tracks(name, country) VALUES (?,?)",
            (track_name, country),
        )
        track_id = conn.execute(
            "SELECT id FROM tracks WHERE name = ?", (track_name,)
        ).fetchone()["id"]

        # -- drivers ------------------------------------------------------
        for driver_num in sess.drivers:
            driver = sess.get_driver(driver_num)
            conn.execute(
                """INSERT OR REPLACE INTO drivers(driver_number, code, full_name, team)
                   VALUES (?,?,?,?)""",
                (
                    int(driver_num),
                    driver["Abbreviation"],
                    driver["FullName"],
                    driver["TeamName"],
                ),
            )

        # -- race results -------------------------------------------------
        for _, row in results.iterrows():
            try:
                conn.execute(
                    """INSERT OR REPLACE INTO race_results
                       (year, round, track_id, driver_number,
                        grid_position, finish_position, points, status)
                       VALUES (?,?,?,?,?,?,?,?)""",
                    (
                        year,
                        round_number,
                        track_id,
                        int(row["DriverNumber"]),
                        None if pd.isnull(row["GridPosition"]) else int(row["GridPosition"]),
                        None if pd.isnull(row["Position"]) else int(row["Position"]),
                        float(row["Points"]) if not pd.isnull(row["Points"]) else None,
                        str(row["Status"]),
                    ),
                )
            except Exception as exc:
                log.warning(f"Skipping result for {row['Abbreviation']}: {exc}")

        # -- laps ---------------------------------------------------------
        valid_laps = laps[laps["IsAccurate"] == True].copy()

        for _, lap in valid_laps.iterrows():
            try:
                conn.execute(
                    """INSERT OR REPLACE INTO laps
                       (year, round, track_id, driver_number, lap_number,
                        lap_time_ms, sector1_ms, sector2_ms, sector3_ms,
                        compound, tire_age, is_valid)
                       VALUES (?,?,?,?,?,?,?,?,?,?,?,?)""",
                    (
                        year,
                        round_number,
                        track_id,
                        int(lap["DriverNumber"]),
                        int(lap["LapNumber"]),
                        _td_to_ms(lap["LapTime"]),
                        _td_to_ms(lap["Sector1Time"]),
                        _td_to_ms(lap["Sector2Time"]),
                        _td_to_ms(lap["Sector3Time"]),
                        lap["Compound"] if not pd.isnull(lap["Compound"]) else None,
                        int(lap["TyreLife"]) if not pd.isnull(lap["TyreLife"]) else None,
                        0 if lap.get("Deleted") else 1,
                    ),
                )
            except Exception as exc:
                log.warning(f"Skipping lap {int(lap['LapNumber'])} {lap['Driver']}: {exc}")

        # -- pit stops ----------------------------------------------------
        # PitInTime is set on the lap the car enters the pits (end of that lap).
        # PitOutTime is set on the following lap (car exits at the start).
        # Duration = PitOutTime(exit lap) - PitInTime(entry lap).

        pit_in_laps = laps[laps["PitInTime"].notna()].copy()
        pit_out_laps = laps[laps["PitOutTime"].notna()].copy()

        for _, pit_row in pit_in_laps.iterrows():
            driver_num = int(pit_row["DriverNumber"])
            lap_num = int(pit_row["LapNumber"])
            pit_in_time = pit_row["PitInTime"]
            compound_in = pit_row["Compound"]  # tyre being removed

            # Find the exit lap for the same driver
            exit_rows = pit_out_laps[
                (pit_out_laps["DriverNumber"] == pit_row["DriverNumber"])
                & (pit_out_laps["LapNumber"] == pit_row["LapNumber"] + 1)
            ]

            duration_ms = None
            compound_out = None
            if not exit_rows.empty:
                pit_out_time = exit_rows.iloc[0]["PitOutTime"]
                duration_ms = _td_to_ms(pit_out_time - pit_in_time)
                compound_out = exit_rows.iloc[0]["Compound"]

            try:
                conn.execute(
                    """INSERT OR REPLACE INTO pit_stops
                       (year, round, track_id, driver_number,
                        lap_number, duration_ms, compound_out)
                       VALUES (?,?,?,?,?,?,?)""",
                    (
                        year, round_number, track_id,
                        driver_num, lap_num, duration_ms, compound_out,
                    ),
                )
            except Exception as exc:
                log.warning(f"Skipping pit stop lap {lap_num} driver {driver_num}: {exc}")

    log.info(
        f"Stored {year} {track_name} — "
        f"{len(valid_laps)} laps, "
        f"{len(pit_in_laps)} pit stops, "
        f"{len(results)} results"
    )


def load_season(year: int, session_type: str = "R") -> None:
    """Load every round of a season. Skips already-stored rounds; retries rate limits."""
    try:
        schedule = fastf1.get_event_schedule(year, include_testing=False)
    except RateLimitExceededError as exc:
        log.error(f"Rate-limited fetching {year} schedule: {exc}. Try again later.")
        return
    except Exception as exc:
        log.error(f"Could not fetch {year} schedule: {exc}")
        return

    for _, event in schedule.iterrows():
        round_num = int(event["RoundNumber"])
        event_name = event["EventName"]

        if _already_loaded(year, round_num):
            log.info(f"Skipping {year} round {round_num} {event_name} (already in DB)")
            continue

        for attempt in range(3):
            try:
                load_session(year, round_num, session_type)
                break  # success
            except RateLimitExceededError as exc:
                wait = 120 * (attempt + 1)  # 2min, 4min, 6min
                log.warning(
                    f"Rate-limited on {year} r{round_num}. "
                    f"Waiting {wait}s (attempt {attempt+1}/3)..."
                )
                time.sleep(wait)
            except Exception as exc:
                log.error(f"Skipping {year} round {round_num} {event_name}: {exc}")
                break


# ---------------------------------------------------------------------------
# Query helpers
# ---------------------------------------------------------------------------

def get_pit_stops(track_name: str, driver_code: str) -> list[dict]:
    """
    Return all pit stops for a driver at a track, across all years.
    Durations are in milliseconds (total pit-lane transit time).
    """
    with get_conn() as conn:
        rows = conn.execute(
            """SELECT ps.year, ps.round, ps.lap_number,
                      ps.duration_ms, ps.compound_out
               FROM pit_stops ps
               JOIN tracks t   ON t.id = ps.track_id
               JOIN drivers d  ON d.driver_number = ps.driver_number
               WHERE t.name = ? AND d.code = ?
                 AND ps.duration_ms BETWEEN ? AND ?
               ORDER BY ps.year, ps.lap_number""",
            (track_name, driver_code.upper(), PIT_MIN_MS, PIT_MAX_MS),
        ).fetchall()
    return [dict(r) for r in rows]


def get_sector_benchmarks(track_name: str) -> dict:
    """
    Return the best sector times per driver at a track (across all years).
    Values are in milliseconds.
    """
    with get_conn() as conn:
        rows = conn.execute(
            """SELECT d.code,
                      MIN(l.sector1_ms) AS best_s1,
                      MIN(l.sector2_ms) AS best_s2,
                      MIN(l.sector3_ms) AS best_s3,
                      MIN(l.lap_time_ms) AS best_lap
               FROM laps l
               JOIN tracks t  ON t.id = l.track_id
               JOIN drivers d ON d.driver_number = l.driver_number
               WHERE t.name = ?
                 AND l.is_valid = 1
                 AND l.lap_time_ms IS NOT NULL
               GROUP BY d.code
               ORDER BY best_lap""",
            (track_name,),
        ).fetchall()
    return {r["code"]: dict(r) for r in rows}


def get_driver_history(driver_code: str, track_name: str) -> list[dict]:
    """
    Return every lap for a driver at a track (all years), ordered by year then lap.
    """
    with get_conn() as conn:
        rows = conn.execute(
            """SELECT l.year, l.round, l.lap_number,
                      l.lap_time_ms, l.sector1_ms, l.sector2_ms, l.sector3_ms,
                      l.compound, l.tire_age
               FROM laps l
               JOIN tracks t  ON t.id = l.track_id
               JOIN drivers d ON d.driver_number = l.driver_number
               WHERE t.name = ? AND d.code = ?
                 AND l.is_valid = 1
               ORDER BY l.year, l.lap_number""",
            (track_name, driver_code.upper()),
        ).fetchall()
    return [dict(r) for r in rows]


def get_avg_pit_duration(track_name: str, driver_code: str) -> Optional[float]:
    """Return average pit stop duration in ms for a driver at a track."""
    with get_conn() as conn:
        row = conn.execute(
            """SELECT AVG(ps.duration_ms) AS avg_ms
               FROM pit_stops ps
               JOIN tracks t  ON t.id = ps.track_id
               JOIN drivers d ON d.driver_number = ps.driver_number
               WHERE t.name = ? AND d.code = ?
                 AND ps.duration_ms BETWEEN ? AND ?""",
            (track_name, driver_code.upper(), PIT_MIN_MS, PIT_MAX_MS),
        ).fetchone()
    return row["avg_ms"] if row else None
