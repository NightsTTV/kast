# Langflow flow & prompt template (Phase 3)

The backend no longer sends a raw snapshot every lap. It sends an **enriched
packet only when a trigger fires** (see `backend/triggers.py`,
`backend/commentary.py`). The packet is JSON-serialised into Langflow's
`input_value`.

## Packet contract

```json
{
  "trigger": "fast_lap | slow_lap | sector_outlier | pit_anomaly | drs_battle",
  "snapshot": {
    "lap_num": 57, "driver": "VER",
    "lap_time_ms": 103464, "sector_1_ms": 28427, "sector_2_ms": 41000, "sector_3_ms": 21000,
    "compound": "MEDIUM", "tire_age": 30, "drs": null, "gap_ahead_ms": 3344,
    "track_temp_c": 31.0, "air_temp_c": 19.0, "pit_duration_ms": null
  },
  "context": {
    "// fast_lap/slow_lap":  "driver_all_time_best_ms, driver_historical_avg_ms, total_historical_laps",
    "// sector_outlier":     "driver_best_s1_ms, driver_best_s2_ms, driver_best_s3_ms, driver_best_lap_ms",
    "// pit_anomaly":        "historical_avg_ms, historical_stops, previous_stops[]",
    "// drs_battle":         "gap_ahead_ms"
  }
}
```

All times are **milliseconds**. `drs` is `null` in replay mode (telemetry not
loaded), so `drs_battle` only fires once live telemetry is wired in Phase 4+.

## Flow shape

```
ChatInput  →  Prompt  →  Ollama (granite3.3)  →  ChatOutput
```

- **ChatInput** receives the JSON packet string (this is the `input_value` the
  backend POSTs).
- **Ollama** component: Base URL `http://localhost:11434`, model `granite3.3`
  (`ollama pull granite3.3` first). Swap for the IBM watsonx.ai component if
  using cloud Granite — the prompt below is provider-agnostic.
- **ChatOutput** text is read back by `langflow_client.extract_text()` at
  `outputs[0].outputs[0].results.message.text`.

## Prompt component template

Paste this into the Prompt component. `{packet}` is wired to the ChatInput
message (the JSON string above).

```
You are an expert Formula 1 broadcast commentator. You are given a single
race event as JSON, already filtered to be noteworthy — every event you
receive is worth one short remark on air.

The JSON has three parts:
- "trigger": what just happened (fast_lap, slow_lap, sector_outlier,
  pit_anomaly, or drs_battle).
- "snapshot": the live numbers for this lap. All times are in milliseconds;
  divide by 1000 for seconds.
- "context": historical reference values for this driver at this circuit,
  pulled from past races. Use these to judge whether the moment is impressive,
  routine, or a problem.

Write ONE punchy sentence (max ~25 words) of live commentary about this event.
Refer to the driver by their 3-letter code. Compare the live number to the
historical context when it adds meaning (e.g. "his quickest sector here in
three years"). Never invent data not present in the JSON. No preamble, no
markdown — just the spoken line.

Event:
{packet}
```

## Tuning the trigger rate

If commentary feels too frequent or too sparse, adjust the thresholds in one
place: the constants block at the top of `backend/triggers.py`. They are
calibrated against the 2023 Monaco field (~3.8% of driver-laps fire). Re-run
`python backend/run_replay.py --dry-run --tick 0` to see the new rate before
touching Langflow.
```
