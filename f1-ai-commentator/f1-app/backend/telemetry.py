"""
FastF1 replay poller — Phase 2 / Phase 3.

Replays a completed race lap-by-lap on a timer, yielding one Snapshot per
(driver, lap) pair in the order cars actually crossed the finish line.

Phase 3 additions:
  - Rolling buffer (deque, maxlen=5) per driver — passed to trigger functions
  - pit_duration_ms pre-computed for all pit-out laps at init time
  - stream() yields (snapshot, buffer_before, raw_lap_row) so callers have all
    three without reaching back into the poller

Config (env vars):
  REPLAY_MODE=true       set false to switch to live OpenF1 polling (Phase 4+)
  REPLAY_YEAR=2023
  REPLAY_ROUND=6         Monaco is round 6 in 2023
  REPLAY_SESSION=R
  REPLAY_TICK_S=2.0      seconds between snapshots in replay (wall-clock pacing)

Verified FastF1 columns used (v3.8.3, 2023 Monaco):
  Driver          str   — 3-letter code
  DriverNumber    str   — cast to str for dict lookups
  LapNumber       float — cast to int
  LapTime         timedelta / NaT
  Sector1/2/3Time timedelta / NaT
  Compound        str / NaN
  TyreLife        float / NaN
  Position        float — used for gap calculation
  Time            timedelta — session time when car crossed finish line
  IsAccurate      bool  — filter to accurate laps only
  PitInTime       timedelta / NaT — set when car enters pit at end of lap
  PitOutTime      timedelta / NaT — set when car exits pit at start of lap

  weather_data columns: Time, AirTemp, TrackTemp
"""
import logging
import os
import time
from collections import deque
from pathlib import Path
from typing import Iterator, Optional

import pandas as pd
import fastf1

from snapshot import Snapshot

log = logging.getLogger(__name__)

CACHE_DIR = Path(__file__).parent.parent / "data" / "cache"
fastf1.Cache.enable_cache(str(CACHE_DIR))

# ---------------------------------------------------------------------------
# Config
# ---------------------------------------------------------------------------
REPLAY_MODE    = os.getenv("REPLAY_MODE", "true").lower() == "true"
REPLAY_YEAR    = int(os.getenv("REPLAY_YEAR",    "2023"))
REPLAY_ROUND   = int(os.getenv("REPLAY_ROUND",   "6"))    # Monaco 2023 = round 6
REPLAY_SESSION = os.getenv("REPLAY_SESSION", "R")
REPLAY_TICK_S  = float(os.getenv("REPLAY_TICK_S", "2.0"))


# ---------------------------------------------------------------------------
# Helpers
# ---------------------------------------------------------------------------

def _td_to_ms(td) -> Optional[int]:
    if pd.isnull(td):
        return None
    return int(td.total_seconds() * 1000)


def _weather_at(weather_df: pd.DataFrame, session_time) -> tuple[Optional[float], Optional[float]]:
    """Return (track_temp_c, air_temp_c) closest to the given session timedelta."""
    if weather_df is None or weather_df.empty:
        return None, None
    try:
        idx = (weather_df["Time"] - session_time).abs().idxmin()
        row = weather_df.loc[idx]
        track = float(row["TrackTemp"]) if not pd.isnull(row["TrackTemp"]) else None
        air   = float(row["AirTemp"])   if not pd.isnull(row["AirTemp"])   else None
        return track, air
    except Exception:
        return None, None


def _gap_ahead_ms(
    driver_num: str,
    lap_num: int,
    position: float,
    laps: pd.DataFrame,
) -> Optional[int]:
    """
    Gap to the car immediately ahead, in milliseconds.

    Method: diff in session Time (finish-line crossing) between this car and
    the car one position ahead on the same lap. This matches what broadcast
    graphics show between cars on the same lap count.

    Returns 0 for the race leader, None when unavailable.
    """
    if pd.isnull(position) or position <= 1:
        return 0  # leader

    lap_slice = laps[laps["LapNumber"] == lap_num]

    this_rows = lap_slice[lap_slice["DriverNumber"] == driver_num]
    if this_rows.empty:
        return None
    this_time = this_rows.iloc[0]["Time"]

    ahead_pos = position - 1
    ahead_rows = lap_slice[lap_slice["Position"] == ahead_pos]
    if ahead_rows.empty:
        return None
    ahead_time = ahead_rows.iloc[0]["Time"]

    gap = this_time - ahead_time
    return max(0, _td_to_ms(gap))


# ---------------------------------------------------------------------------
# Replay poller
# ---------------------------------------------------------------------------

class ReplayPoller:
    """
    Yields Snapshots by replaying a completed race session, one per driver per lap,
    in the order cars crossed the finish line. Waits tick_s between each yield.

    Tracks last_sent_lap per driver to guard against duplicate sends if the caller
    re-enters (mirrors the contract the live poller must also satisfy).
    """

    BUFFER_SIZE = 5   # rolling window: last N laps per driver

    def __init__(
        self,
        year: int = REPLAY_YEAR,
        event: int | str = REPLAY_ROUND,
        session_type: str = REPLAY_SESSION,
        tick_s: float = REPLAY_TICK_S,
    ):
        log.info("Loading replay session %s round %s %s ...", year, event, session_type)
        self.sess = fastf1.get_session(year, event, session_type)
        self.sess.load(telemetry=False, weather=True, messages=False)

        self.laps         = self.sess.laps
        self.weather      = getattr(self.sess, "weather_data", None)
        self.tick_s       = tick_s
        self.track_name   = self.sess.event["EventName"]

        self._last_sent:    dict[str, int]             = {}  # driver_num → last lap sent
        self._buffer:       dict[str, deque]           = {}  # driver_code → deque of snapshot dicts
        self._pit_durations: dict[tuple[str,int], int] = self._compute_pit_durations()

        total = len(self.laps["LapNumber"].dropna().unique())
        log.info(
            "Replay ready: %s — %d lap numbers, %d pit durations pre-computed, tick=%.1fs",
            self.track_name, total, len(self._pit_durations), tick_s,
        )

    def _compute_pit_durations(self) -> dict[tuple[str, int], int]:
        """
        Pre-compute pit lane transit time for every pit stop in the session.
        Key: (driver_number_str, exit_lap_number).
        Value: duration in ms = PitOutTime(exit lap) - PitInTime(entry lap).
        """
        durations: dict[tuple[str, int], int] = {}
        pit_out = self.laps[self.laps["PitOutTime"].notna()]
        pit_in  = self.laps[self.laps["PitInTime"].notna()]

        for _, out_row in pit_out.iterrows():
            driver = str(out_row["DriverNumber"])
            exit_lap = int(out_row["LapNumber"])
            entry_lap = exit_lap - 1

            in_rows = pit_in[
                (pit_in["DriverNumber"] == out_row["DriverNumber"]) &
                (pit_in["LapNumber"]    == entry_lap)
            ]
            if in_rows.empty:
                continue

            diff = out_row["PitOutTime"] - in_rows.iloc[0]["PitInTime"]
            ms   = _td_to_ms(diff)
            if ms is not None and 10_000 < ms < 120_000:   # sanity: 10s–120s only
                durations[(driver, exit_lap)] = ms

        return durations

    def get_buffer(self, driver_code: str) -> list[dict]:
        """Return a copy of the rolling buffer for a driver (by 3-letter code)."""
        return list(self._buffer.get(driver_code, []))

    def _build_snapshot(self, lap_row: pd.Series) -> Snapshot:
        driver_num = str(lap_row["DriverNumber"])
        lap_num    = int(lap_row["LapNumber"])
        position   = lap_row["Position"]

        track_temp, air_temp = _weather_at(self.weather, lap_row["Time"])

        return Snapshot(
            lap_num         = lap_num,
            driver          = str(lap_row["Driver"]),
            lap_time_ms     = _td_to_ms(lap_row["LapTime"]),
            sector_1_ms     = _td_to_ms(lap_row["Sector1Time"]),
            sector_2_ms     = _td_to_ms(lap_row["Sector2Time"]),
            sector_3_ms     = _td_to_ms(lap_row["Sector3Time"]),
            compound        = str(lap_row["Compound"]) if not pd.isnull(lap_row["Compound"]) else None,
            tire_age        = int(lap_row["TyreLife"])  if not pd.isnull(lap_row["TyreLife"])  else None,
            drs             = None,   # requires telemetry — not loaded in replay
            gap_ahead_ms    = _gap_ahead_ms(driver_num, lap_num, position, self.laps),
            track_temp_c    = track_temp,
            air_temp_c      = air_temp,
            pit_duration_ms = self._pit_durations.get((driver_num, lap_num)),
        )

    def stream(self) -> Iterator[tuple[Snapshot, list[dict], pd.Series]]:
        """
        Yield (snapshot, buffer_before, raw_lap_row) in chronological order.

        buffer_before: the driver's last N snapshots BEFORE this lap — what
        trigger functions see as "recent history". The buffer is updated with
        the current snapshot AFTER yielding so triggers compare current vs past.

        Skips inaccurate laps. Respects last_sent_lap deduplication.
        Sleeps tick_s between each yield. Never raises — logs and continues.
        """
        laps = self.laps[self.laps["IsAccurate"] == True].copy()
        laps = laps.sort_values(["LapNumber", "Time"])

        for _, lap_row in laps.iterrows():
            driver_num  = str(lap_row["DriverNumber"])
            driver_code = str(lap_row["Driver"])
            lap_num     = int(lap_row["LapNumber"])

            if self._last_sent.get(driver_num) == lap_num:
                continue
            self._last_sent[driver_num] = lap_num

            try:
                snapshot = self._build_snapshot(lap_row)
            except Exception as exc:
                log.error("Snapshot build failed lap=%s driver=%s: %s", lap_num, driver_num, exc)
                continue

            # Snapshot of buffer BEFORE this lap (what triggers compare against)
            buffer_before = list(self._buffer.get(driver_code, []))

            yield snapshot, buffer_before, lap_row

            # Update buffer AFTER yielding
            if driver_code not in self._buffer:
                self._buffer[driver_code] = deque(maxlen=self.BUFFER_SIZE)
            self._buffer[driver_code].append(snapshot.model_dump())

            time.sleep(self.tick_s)
