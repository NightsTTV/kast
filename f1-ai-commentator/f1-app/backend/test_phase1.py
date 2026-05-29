"""
Phase 1 test — verify 2023 Monaco is loaded and queries return sane results.
Run AFTER load_history.py --test completes.
"""
import sys
from pathlib import Path
sys.path.insert(0, str(Path(__file__).parent))

from db import init_db, get_conn
from history import get_pit_stops, get_sector_benchmarks, get_driver_history, get_avg_pit_duration


def test_row_counts():
    with get_conn() as conn:
        for table in ("tracks", "drivers", "laps", "pit_stops", "race_results"):
            n = conn.execute(f"SELECT COUNT(*) AS n FROM {table}").fetchone()["n"]
            print(f"  {table}: {n} rows")
            assert n > 0, f"Expected rows in {table}"


def test_ver_pit_stops():
    stops = get_pit_stops("Monaco Grand Prix", "VER")
    print(f"\n  VER pit stops at Monaco: {len(stops)}")
    for s in stops:
        print(f"    year={s['year']} lap={s['lap_number']} "
              f"duration_ms={s['duration_ms']} compound_out={s['compound_out']}")
    assert len(stops) >= 1, "Expected at least one VER pit stop"


def test_sector_benchmarks():
    benchmarks = get_sector_benchmarks("Monaco Grand Prix")
    print(f"\n  Sector benchmarks (top 5 by best lap):")
    for code, b in list(benchmarks.items())[:5]:
        print(f"    {code}: S1={b['best_s1']}ms S2={b['best_s2']}ms "
              f"S3={b['best_s3']}ms lap={b['best_lap']}ms")
    assert "VER" in benchmarks


def test_driver_history():
    laps = get_driver_history("VER", "Monaco Grand Prix")
    print(f"\n  VER lap history at Monaco: {len(laps)} laps")
    for lap in laps[:3]:
        print(f"    year={lap['year']} lap={lap['lap_number']} "
              f"time={lap['lap_time_ms']}ms compound={lap['compound']}")
    assert len(laps) > 0


def test_avg_pit():
    avg = get_avg_pit_duration("Monaco Grand Prix", "VER")
    print(f"\n  VER avg pit duration at Monaco: {avg:.0f} ms" if avg else "\n  No avg pit data")


if __name__ == "__main__":
    init_db()
    print("=== Phase 1 Tests ===\n")
    print("Row counts:")
    test_row_counts()
    test_ver_pit_stops()
    test_sector_benchmarks()
    test_driver_history()
    test_avg_pit()
    print("\nAll assertions passed.")
