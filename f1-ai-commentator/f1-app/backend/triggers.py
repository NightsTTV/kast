"""
Trigger engine — Phase 3.

Pure functions over a snapshot + rolling buffer. No I/O, no DB calls, no Langflow.
Each function returns a FiredTrigger or None.
evaluate() runs all of them and returns the list that fired.

Thresholds calibrated against the full 2023 Monaco field (1,423 driver-laps),
empirically measured to keep commentary sparse and meaningful. The naive
"5% off best" cut-points fired on ~61% of laps because race pace runs far off
ultimate pace — at Monaco the *median* race sector is already 1.05x the
driver's all-time best (DRS trains, tyre/fuel management). Real outliers live
in the distribution tail:

  Sector vs track-best:  p50=1.05  p90=1.33  p99=1.45   → fire at 1.45 (tail)
  Lap vs rolling avg:    p50=1.00  p90=1.03  faster-tail≈0.92 / slower≈1.20
  Pit lane times:        min 13,904ms  avg ~24,700ms  max 59,099ms

Net effect at these values: ~64 triggers / 1,423 driver-laps (3.8%) — roughly
one commentary moment every lap or two across the whole field, vs the 1,148
the spec defaults produced. Tune in one place here if the feel is off.
"""
import logging
from dataclasses import dataclass, field
from typing import Optional

log = logging.getLogger(__name__)

# ── Thresholds ────────────────────────────────────────────────────────────────

FAST_LAP_PCT      = 0.92   # >8% faster than rolling avg  → "fast_lap"
SLOW_LAP_PCT      = 1.20   # >20% slower than rolling avg → "slow_lap"
MIN_BUFFER_LAPS   = 3      # need at least this many laps before trusting baseline

SECTOR_PCT        = 1.45   # >45% over driver's own track best → "sector_outlier"

PIT_FAST_PCT      = 0.92   # >8% faster than driver's historical avg → "pit_anomaly" fast
PIT_SLOW_PCT      = 1.12   # >12% slower                            → "pit_anomaly" slow

DRS_GAP_MS        = 1000   # gap < 1 s with DRS open → "drs_battle"


# ── Result type ───────────────────────────────────────────────────────────────

@dataclass
class FiredTrigger:
    name: str          # "fast_lap" | "slow_lap" | "sector_outlier" | "pit_anomaly" | "drs_battle"
    data: dict = field(default_factory=dict)   # numbers the commentary assembler needs


# ── Internal helpers ──────────────────────────────────────────────────────────

def _buffer_avg_lap(buffer: list[dict]) -> Optional[float]:
    """Mean lap_time_ms over the buffer, ignoring None/missing values."""
    times = [s["lap_time_ms"] for s in buffer if s.get("lap_time_ms") is not None]
    return sum(times) / len(times) if times else None


# ── Trigger functions ─────────────────────────────────────────────────────────

def fast_slow_lap(snapshot: dict, buffer: list[dict]) -> Optional[FiredTrigger]:
    """
    Compare current lap time against the driver's rolling average (last N laps in buffer).
    Requires MIN_BUFFER_LAPS laps of history before trusting the baseline.
    """
    current = snapshot.get("lap_time_ms")
    if current is None or len(buffer) < MIN_BUFFER_LAPS:
        return None

    avg = _buffer_avg_lap(buffer)
    if avg is None:
        return None

    ratio = current / avg

    if ratio <= FAST_LAP_PCT:
        return FiredTrigger("fast_lap", {
            "current_ms":    current,
            "buffer_avg_ms": round(avg),
            "delta_ms":      current - round(avg),
            "delta_pct":     round((ratio - 1) * 100, 1),
        })

    if ratio >= SLOW_LAP_PCT:
        return FiredTrigger("slow_lap", {
            "current_ms":    current,
            "buffer_avg_ms": round(avg),
            "delta_ms":      current - round(avg),
            "delta_pct":     round((ratio - 1) * 100, 1),
        })

    return None


def sector_outlier(snapshot: dict, driver_benchmarks: Optional[dict]) -> Optional[FiredTrigger]:
    """
    Compare each sector against the driver's own best at this track (from DB).
    Fires on the worst-offending sector if any exceed the threshold.
    driver_benchmarks: the entry for this driver from get_sector_benchmarks(track).
    """
    if not driver_benchmarks:
        return None

    checks = [
        ("sector_1_ms", "best_s1", "S1"),
        ("sector_2_ms", "best_s2", "S2"),
        ("sector_3_ms", "best_s3", "S3"),
    ]

    worst_ratio  = 0.0
    worst_result = None

    for snap_key, bench_key, label in checks:
        current = snapshot.get(snap_key)
        best    = driver_benchmarks.get(bench_key)
        if current is None or not best:
            continue

        ratio = current / best
        if ratio >= SECTOR_PCT and ratio > worst_ratio:
            worst_ratio  = ratio
            worst_result = FiredTrigger("sector_outlier", {
                "sector":        label,
                "current_ms":    current,
                "benchmark_ms":  best,
                "delta_ms":      current - best,
                "delta_pct":     round((ratio - 1) * 100, 1),
            })

    return worst_result


def pit_stop_anomaly(snapshot: dict, avg_pit_ms: Optional[float]) -> Optional[FiredTrigger]:
    """
    Only fires on pit-out laps (snapshot['pit_duration_ms'] is set by the poller).
    Compares the actual duration against the driver's historical average at this track.
    """
    current = snapshot.get("pit_duration_ms")
    if current is None:
        return None  # not a pit-out lap

    if avg_pit_ms is None:
        return None  # no history to compare against — skip rather than guess

    ratio = current / avg_pit_ms

    if ratio <= PIT_FAST_PCT:
        return FiredTrigger("pit_anomaly", {
            "direction": "fast",
            "current_ms": current,
            "avg_ms":     round(avg_pit_ms),
            "delta_ms":   current - round(avg_pit_ms),
            "delta_pct":  round((ratio - 1) * 100, 1),
        })

    if ratio >= PIT_SLOW_PCT:
        return FiredTrigger("pit_anomaly", {
            "direction": "slow",
            "current_ms": current,
            "avg_ms":     round(avg_pit_ms),
            "delta_ms":   current - round(avg_pit_ms),
            "delta_pct":  round((ratio - 1) * 100, 1),
        })

    return None


def drs_battle(snapshot: dict) -> Optional[FiredTrigger]:
    """
    Purely from the live snapshot — no DB needed.
    Fires when DRS is open and the gap to the car ahead is < 1 s.

    NOTE: drs is None in replay mode (telemetry not loaded). This trigger
    will only fire once live telemetry is connected in Phase 4+.
    """
    drs = snapshot.get("drs")
    gap = snapshot.get("gap_ahead_ms")

    if drs is True and gap is not None and gap < DRS_GAP_MS:
        return FiredTrigger("drs_battle", {
            "gap_ahead_ms": gap,
        })

    return None


# ── Main entry point ──────────────────────────────────────────────────────────

def evaluate(
    snapshot:          dict,
    buffer:            list[dict],
    driver_benchmarks: Optional[dict],
    avg_pit_ms:        Optional[float],
) -> list[FiredTrigger]:
    """
    Run all triggers. Return every one that fired. Never raises — broken
    trigger functions are logged and skipped so the polling loop stays alive.
    """
    fired   = []
    checks  = [
        ("fast_slow_lap",    lambda: fast_slow_lap(snapshot, buffer)),
        ("sector_outlier",   lambda: sector_outlier(snapshot, driver_benchmarks)),
        ("pit_stop_anomaly", lambda: pit_stop_anomaly(snapshot, avg_pit_ms)),
        ("drs_battle",       lambda: drs_battle(snapshot)),
    ]

    for name, fn in checks:
        try:
            result = fn()
            if result:
                fired.append(result)
        except Exception as exc:
            log.error(
                "Trigger %s raised for lap=%s driver=%s: %s",
                name, snapshot.get("lap_num"), snapshot.get("driver"), exc,
            )

    return fired
