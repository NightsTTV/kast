"""
Phase 3 tests — run in two parts.

Part A: triggers with synthetic snapshots (no DB, no Langflow).
Part B: commentary assembler with real DB (requires f1_history.db populated).

Usage:
    python test_phase3.py          # both parts
    python test_phase3.py --no-db  # Part A only (triggers)
"""
import sys
import argparse
from pathlib import Path
sys.path.insert(0, str(Path(__file__).parent))

from triggers import (
    fast_slow_lap, sector_outlier, pit_stop_anomaly, drs_battle,
    evaluate, FiredTrigger,
)

# ── Shared synthetic data ─────────────────────────────────────────────────────

BASE_SNAP = dict(
    lap_num=10, driver="VER",
    lap_time_ms=80000,
    sector_1_ms=20000, sector_2_ms=37000, sector_3_ms=21000,
    compound="MEDIUM", tire_age=10,
    drs=None, gap_ahead_ms=5000,
    track_temp_c=48.0, air_temp_c=26.0,
    pit_duration_ms=None,
)

# 5-lap buffer at consistent ~80.2 s pace
BUFFER = [{**BASE_SNAP, "lap_time_ms": 80200, "lap_num": i} for i in range(5, 10)]

# Synthetic benchmark calibrated so BASE_SNAP sectors are all ~4% over best —
# well under the SECTOR_PCT=1.45 threshold, keeping "normal" tests quiet.
# (Real race laps sit ~5% over best at the median, so the tail threshold is high.)
BENCH = {"best_s1": 19200, "best_s2": 35500, "best_s3": 20100, "best_lap": 74800}


# ── Part A: trigger unit tests ────────────────────────────────────────────────

def test_fast_lap_fires():
    snap = {**BASE_SNAP, "lap_time_ms": 73000}   # 9.0% faster than 80.2s avg (≤0.92)
    r = fast_slow_lap(snap, BUFFER)
    assert r and r.name == "fast_lap" and r.data["delta_pct"] < -8
    print(f"  ✓ fast_lap fires: delta={r.data['delta_pct']}%")

def test_slow_lap_fires():
    snap = {**BASE_SNAP, "lap_time_ms": 97000}   # 20.9% slower (≥1.20)
    r = fast_slow_lap(snap, BUFFER)
    assert r and r.name == "slow_lap" and r.data["delta_pct"] > 20
    print(f"  ✓ slow_lap fires: delta={r.data['delta_pct']}%")

def test_normal_lap_silent():
    snap = {**BASE_SNAP, "lap_time_ms": 80100}   # <1% off
    assert fast_slow_lap(snap, BUFFER) is None
    print("  ✓ normal lap: silent")

def test_cold_buffer_silent():
    snap = {**BASE_SNAP, "lap_time_ms": 74000}   # would fire with full buffer
    assert fast_slow_lap(snap, BUFFER[:2]) is None  # only 2 laps — below MIN
    print("  ✓ cold buffer (<3 laps): silent")

def test_sector_outlier_fires():
    snap = {**BASE_SNAP, "sector_2_ms": 52000}   # +46.5% over best S2 (35500ms) (≥1.45)
    r = sector_outlier(snap, BENCH)
    assert r and r.name == "sector_outlier" and r.data["sector"] == "S2"
    print(f"  ✓ sector_outlier fires: S2 +{r.data['delta_pct']}%")

def test_sector_normal_silent():
    snap = {**BASE_SNAP, "sector_2_ms": 38500}   # +8.5% — normal race pace, under 45% tail
    assert sector_outlier(snap, BENCH) is None
    print("  ✓ sector within threshold: silent")

def test_sector_no_bench_silent():
    assert sector_outlier(BASE_SNAP, None) is None
    print("  ✓ no benchmarks: silent")

def test_fast_pit_fires():
    snap = {**BASE_SNAP, "pit_duration_ms": 21000}  # vs avg 26000 → -19%
    r = pit_stop_anomaly(snap, avg_pit_ms=26000)
    assert r and r.data["direction"] == "fast"
    print(f"  ✓ fast_pit fires: {r.data['current_ms']}ms vs avg {r.data['avg_ms']}ms")

def test_slow_pit_fires():
    snap = {**BASE_SNAP, "pit_duration_ms": 30000}  # vs avg 26000 → +15%
    r = pit_stop_anomaly(snap, avg_pit_ms=26000)
    assert r and r.data["direction"] == "slow"
    print(f"  ✓ slow_pit fires: {r.data['current_ms']}ms vs avg {r.data['avg_ms']}ms")

def test_no_pit_silent():
    assert pit_stop_anomaly({**BASE_SNAP, "pit_duration_ms": None}, 26000) is None
    print("  ✓ no pit on this lap: silent")

def test_normal_pit_silent():
    snap = {**BASE_SNAP, "pit_duration_ms": 25500}   # -1.9% of avg — within band
    assert pit_stop_anomaly(snap, avg_pit_ms=26000) is None
    print("  ✓ normal pit duration: silent")

def test_drs_fires():
    snap = {**BASE_SNAP, "drs": True, "gap_ahead_ms": 750}
    r = drs_battle(snap)
    assert r and r.name == "drs_battle"
    print(f"  ✓ drs_battle fires: gap={r.data['gap_ahead_ms']}ms")

def test_drs_gap_too_large():
    assert drs_battle({**BASE_SNAP, "drs": True, "gap_ahead_ms": 1500}) is None
    print("  ✓ DRS but gap > 1s: silent")

def test_drs_not_active():
    assert drs_battle({**BASE_SNAP, "drs": False, "gap_ahead_ms": 500}) is None
    print("  ✓ gap < 1s but no DRS: silent")

def test_drs_none_replay_silent():
    assert drs_battle({**BASE_SNAP, "drs": None, "gap_ahead_ms": 500}) is None
    print("  ✓ drs=None (replay mode): silent")

def test_evaluate_multi():
    snap = {**BASE_SNAP, "lap_time_ms": 73000, "sector_2_ms": 52000}
    fired = evaluate(snap, BUFFER, BENCH, avg_pit_ms=None)
    names = [t.name for t in fired]
    assert "fast_lap" in names and "sector_outlier" in names
    print(f"  ✓ evaluate() multi-trigger: {names}")

def test_evaluate_quiet():
    fired = evaluate(BASE_SNAP, BUFFER, BENCH, avg_pit_ms=None)
    assert fired == []
    print("  ✓ evaluate() quiet lap: no triggers")


# ── Part B: commentary assembler with real DB ─────────────────────────────────

def test_commentary_fast_lap():
    from db import init_db
    from commentary import assemble

    init_db()
    trigger = FiredTrigger("fast_lap", {
        "current_ms": 76000, "buffer_avg_ms": 80200,
        "delta_ms": -4200, "delta_pct": -5.2,
    })
    packet = assemble(trigger, {**BASE_SNAP, "lap_time_ms": 76000}, "Monaco Grand Prix")

    assert packet["trigger"] == "fast_lap"
    assert "snapshot" in packet and "context" in packet
    ctx = packet["context"]
    assert ctx.get("driver_all_time_best_ms") is not None
    print(f"  ✓ fast_lap packet: best={ctx['driver_all_time_best_ms']}ms "
          f"hist_avg={ctx['driver_historical_avg_ms']}ms "
          f"n_laps={ctx['total_historical_laps']}")

def test_commentary_pit():
    from db import init_db
    from commentary import assemble

    init_db()
    trigger = FiredTrigger("pit_anomaly", {
        "direction": "fast", "current_ms": 22000,
        "avg_ms": 25454, "delta_ms": -3454, "delta_pct": -13.6,
    })
    packet = assemble(
        trigger,
        {**BASE_SNAP, "pit_duration_ms": 22000},
        "Monaco Grand Prix",
    )
    ctx = packet["context"]
    assert ctx.get("historical_stops") is not None
    print(f"  ✓ pit_anomaly packet: {ctx['historical_stops']} historical stops, "
          f"hist_avg={ctx['historical_avg_ms']}ms")

def test_commentary_sector():
    from db import init_db
    from commentary import assemble

    init_db()
    trigger = FiredTrigger("sector_outlier", {
        "sector": "S2", "current_ms": 38500,
        "benchmark_ms": 35369, "delta_ms": 3131, "delta_pct": 8.9,
    })
    packet = assemble(trigger, {**BASE_SNAP, "sector_2_ms": 38500}, "Monaco Grand Prix")
    ctx = packet["context"]
    assert ctx.get("driver_best_s2_ms") is not None
    print(f"  ✓ sector_outlier packet: driver best S2={ctx['driver_best_s2_ms']}ms")


# ── Runner ────────────────────────────────────────────────────────────────────

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--no-db", action="store_true")
    args = parser.parse_args()

    print("=== Phase 3 — Part A: trigger unit tests (no DB) ===\n")

    print("  Lap time:")
    test_fast_lap_fires()
    test_slow_lap_fires()
    test_normal_lap_silent()
    test_cold_buffer_silent()

    print("\n  Sector:")
    test_sector_outlier_fires()
    test_sector_normal_silent()
    test_sector_no_bench_silent()

    print("\n  Pit stop:")
    test_fast_pit_fires()
    test_slow_pit_fires()
    test_no_pit_silent()
    test_normal_pit_silent()

    print("\n  DRS battle:")
    test_drs_fires()
    test_drs_gap_too_large()
    test_drs_not_active()
    test_drs_none_replay_silent()

    print("\n  evaluate():")
    test_evaluate_multi()
    test_evaluate_quiet()

    if not args.no_db:
        print("\n=== Phase 3 — Part B: commentary assembler (real DB) ===\n")
        test_commentary_fast_lap()
        test_commentary_pit()
        test_commentary_sector()

    print("\nAll Phase 3 tests passed.")
