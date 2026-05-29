"""
Context assembler — Phase 3.

Takes a fired trigger + live snapshot, runs the matching DB query,
and merges them into the enriched packet that goes to Langflow.

Packet shape (the real Langflow input contract from Phase 3 onwards):
    {
      "trigger":  str,         -- which event fired
      "snapshot": {...},       -- live 12-field snapshot
      "context":  {...}        -- DB-backed historical context
    }

Each trigger maps to one query helper:
    fast_lap / slow_lap  →  get_driver_history
    sector_outlier       →  get_sector_benchmarks
    pit_anomaly          →  get_pit_stops  (+ avg)
    drs_battle           →  no DB needed
"""
import logging
from typing import Optional

from triggers import FiredTrigger
from history import (
    get_driver_history,
    get_sector_benchmarks,
    get_pit_stops,
    get_avg_pit_duration,
)

log = logging.getLogger(__name__)


def assemble(trigger: FiredTrigger, snapshot: dict, track_name: str) -> dict:
    """
    Build the enriched Langflow packet for a fired trigger.

    Returns the dict that langflow_client.send() will JSON-serialise and POST.
    """
    context = _build_context(trigger, snapshot, track_name)
    return {
        "trigger":  trigger.name,
        "snapshot": snapshot,
        "context":  context,
    }


# ── Context builders — one per trigger family ─────────────────────────────────

def _build_context(trigger: FiredTrigger, snapshot: dict, track_name: str) -> dict:
    driver = snapshot["driver"]
    try:
        if trigger.name in ("fast_lap", "slow_lap"):
            return _ctx_lap_time(driver, track_name, trigger.data)
        if trigger.name == "sector_outlier":
            return _ctx_sector(driver, track_name, trigger.data)
        if trigger.name == "pit_anomaly":
            return _ctx_pit(driver, track_name, trigger.data)
        if trigger.name == "drs_battle":
            return trigger.data   # purely snapshot-derived
    except Exception as exc:
        log.warning("context build failed trigger=%s driver=%s track=%s: %s",
                    trigger.name, driver, track_name, exc)
    return trigger.data   # fallback: at least include the trigger's own numbers


def _ctx_lap_time(driver: str, track_name: str, trigger_data: dict) -> dict:
    """Historical lap context for fast_lap / slow_lap."""
    laps  = get_driver_history(driver, track_name)
    times = [l["lap_time_ms"] for l in laps if l.get("lap_time_ms")]
    if not times:
        return trigger_data
    return {
        **trigger_data,
        "driver_all_time_best_ms":  min(times),
        "driver_historical_avg_ms": round(sum(times) / len(times)),
        "total_historical_laps":    len(times),
    }


def _ctx_sector(driver: str, track_name: str, trigger_data: dict) -> dict:
    """Best-sector benchmarks for the driver at this track."""
    benchmarks   = get_sector_benchmarks(track_name)
    driver_bench = benchmarks.get(driver, {})
    return {
        **trigger_data,
        "driver_best_s1_ms":  driver_bench.get("best_s1"),
        "driver_best_s2_ms":  driver_bench.get("best_s2"),
        "driver_best_s3_ms":  driver_bench.get("best_s3"),
        "driver_best_lap_ms": driver_bench.get("best_lap"),
    }


def _ctx_pit(driver: str, track_name: str, trigger_data: dict) -> dict:
    """Historical pit stop records for the driver at this track."""
    stops     = get_pit_stops(track_name, driver)
    durations = [s["duration_ms"] for s in stops if s.get("duration_ms")]
    avg       = round(sum(durations) / len(durations)) if durations else None
    return {
        **trigger_data,
        "historical_avg_ms":  avg,
        "historical_stops":   len(stops),
        "previous_stops":     stops[-3:],   # last 3 for narrative colour
    }
