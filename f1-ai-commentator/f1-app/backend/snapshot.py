"""
Snapshot contract — the exact JSON Python sends to the Langflow flow per new lap.

Keep this file as the single source of truth for field names.
Both telemetry.py (producer) and langflow_client.py (sender) import from here.
"""
from typing import Optional
from pydantic import BaseModel


class Snapshot(BaseModel):
    lap_num:         int
    driver:          str            # 3-letter code e.g. "VER"
    lap_time_ms:     Optional[int]  # None for pit-out / in-laps
    sector_1_ms:     Optional[int]
    sector_2_ms:     Optional[int]
    sector_3_ms:     Optional[int]
    compound:        Optional[str]  # SOFT / MEDIUM / HARD / INTER / WET
    tire_age:        Optional[int]  # laps on current set
    drs:             Optional[bool] # None in replay mode (requires telemetry)
    gap_ahead_ms:    Optional[int]  # gap to car immediately ahead; 0 for leader
    track_temp_c:    Optional[float]
    air_temp_c:      Optional[float]
    pit_duration_ms: Optional[int] = None  # pit-lane transit time; only set on pit-out laps

    def to_langflow_input(self) -> str:
        """Serialise to the JSON string that goes into Langflow's input_value field."""
        return self.model_dump_json()
