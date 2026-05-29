"""
Phase 1 loader script — run once to populate f1_history.db.

Usage:
    python load_history.py                     # load 2022-2024 races
    python load_history.py --years 2023 2024   # specific years
    python load_history.py --test              # 2023 Monaco only (quick smoke test)
"""
import sys
import argparse
import logging
from pathlib import Path

sys.path.insert(0, str(Path(__file__).parent))

from db import init_db
from history import load_session, load_season

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s %(levelname)s %(message)s",
    datefmt="%H:%M:%S",
)
log = logging.getLogger(__name__)


def main():
    parser = argparse.ArgumentParser(description="Load historical F1 data into SQLite")
    parser.add_argument("--years", type=int, nargs="+", default=[2022, 2023, 2024])
    parser.add_argument(
        "--test",
        action="store_true",
        help="Quick smoke test: load 2023 Monaco only",
    )
    args = parser.parse_args()

    init_db()
    log.info("Database initialized")

    if args.test:
        log.info("--- SMOKE TEST: 2023 Monaco Race ---")
        load_session(2023, "Monaco", "R")
        log.info("Smoke test complete")
        return

    for year in args.years:
        log.info(f"=== Loading {year} season ===")
        load_season(year, session_type="R")

    log.info("All done.")


if __name__ == "__main__":
    main()
