"""
SQLite database layer. All times stored as integer milliseconds.
Call init_db() once at startup; use get_conn() for queries.
"""
import sqlite3
from pathlib import Path
from contextlib import contextmanager

DB_PATH = Path(__file__).parent.parent / "data" / "f1_history.db"

SCHEMA = """
PRAGMA journal_mode = WAL;
PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS drivers (
    driver_number   INTEGER PRIMARY KEY,
    code            TEXT NOT NULL,       -- e.g. "VER"
    full_name       TEXT NOT NULL,
    team            TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS tracks (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    name            TEXT NOT NULL,       -- EventName from FastF1
    country         TEXT NOT NULL,
    UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS race_results (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    year            INTEGER NOT NULL,
    round           INTEGER NOT NULL,
    track_id        INTEGER NOT NULL REFERENCES tracks(id),
    driver_number   INTEGER NOT NULL REFERENCES drivers(driver_number),
    grid_position   INTEGER,
    finish_position INTEGER,
    points          REAL,
    status          TEXT,               -- "Finished", "DNF", etc.
    UNIQUE(year, round, driver_number)
);

CREATE TABLE IF NOT EXISTS laps (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    year            INTEGER NOT NULL,
    round           INTEGER NOT NULL,
    track_id        INTEGER NOT NULL REFERENCES tracks(id),
    driver_number   INTEGER NOT NULL REFERENCES drivers(driver_number),
    lap_number      INTEGER NOT NULL,
    lap_time_ms     INTEGER,            -- NULL for in/out laps
    sector1_ms      INTEGER,
    sector2_ms      INTEGER,
    sector3_ms      INTEGER,
    compound        TEXT,               -- SOFT/MEDIUM/HARD/INTER/WET
    tire_age        INTEGER,            -- laps on this set
    is_valid        INTEGER DEFAULT 1,  -- 0 if deleted lap time
    UNIQUE(year, round, driver_number, lap_number)
);

CREATE TABLE IF NOT EXISTS pit_stops (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    year            INTEGER NOT NULL,
    round           INTEGER NOT NULL,
    track_id        INTEGER NOT NULL REFERENCES tracks(id),
    driver_number   INTEGER NOT NULL REFERENCES drivers(driver_number),
    lap_number      INTEGER NOT NULL,   -- lap on which pit occurred
    duration_ms     INTEGER,            -- stationary time in pit box
    compound_out    TEXT,               -- compound fitted on exit
    UNIQUE(year, round, driver_number, lap_number)
);

CREATE INDEX IF NOT EXISTS idx_laps_driver_track ON laps(driver_number, track_id);
CREATE INDEX IF NOT EXISTS idx_laps_year_round    ON laps(year, round);
CREATE INDEX IF NOT EXISTS idx_pit_driver_track   ON pit_stops(driver_number, track_id);
"""


def init_db() -> None:
    """Create tables and indexes. Safe to call multiple times."""
    DB_PATH.parent.mkdir(parents=True, exist_ok=True)
    with sqlite3.connect(DB_PATH) as conn:
        conn.executescript(SCHEMA)


@contextmanager
def get_conn():
    """Yield a SQLite connection with row_factory set."""
    conn = sqlite3.connect(DB_PATH)
    conn.row_factory = sqlite3.Row
    conn.execute("PRAGMA foreign_keys = ON")
    try:
        yield conn
        conn.commit()
    except Exception:
        conn.rollback()
        raise
    finally:
        conn.close()
