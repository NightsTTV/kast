"""
Langflow ingestion seam.

Verified against Langflow docs (2025):
  POST /api/v1/run/{flow_id}?stream=false
  Header:  x-api-key: <key>
  Body:    { "input_value": "<json string>", "input_type": "chat", "output_type": "chat" }
           input_type MUST be "chat": the flow's input is a ChatInput component, so
           "chat" routes input_value into it. With "text" Langflow looks for a
           TextInput, finds none, and the prompt variable stays empty — the model
           then hallucinates with no event data. (Diagnosed 2026-05; this was the
           "Ollama timeout/JSON" bug — the data never reached the model.)
  Response path to text: outputs[0].outputs[0].results.message.text

All config lives in env vars — nothing is hardcoded here.
"""
import json
import logging
import os

import requests

log = logging.getLogger(__name__)

_BASE_URL  = os.getenv("LANGFLOW_URL", "").rstrip("/")
_FLOW_ID   = os.getenv("LANGFLOW_FLOW_ID", "")
_API_KEY   = os.getenv("LANGFLOW_API_KEY", "")
_TIMEOUT_S = int(os.getenv("LANGFLOW_TIMEOUT_S", "30"))


def _endpoint() -> str:
    return f"{_BASE_URL}/api/v1/run/{_FLOW_ID}?stream=false"


def _ident(packet: dict) -> tuple:
    """
    Pull (lap, driver) for logging, tolerating either payload shape:
      - Phase 3 enriched packet: {"trigger", "snapshot": {...}, "context"}
      - bare snapshot dict:      {"lap_num", "driver", ...}
    """
    snap = packet.get("snapshot", packet)
    return snap.get("lap_num"), snap.get("driver")


def send(packet: dict) -> dict:
    """
    POST one payload to the Langflow flow's run endpoint.

    Args:
        packet: the Phase 3 enriched packet from commentary.assemble()
                ({"trigger", "snapshot", "context"}), or a bare snapshot dict.
                The whole thing is JSON-serialised into input_value.

    Returns:
        Parsed response dict from Langflow, or {} if unconfigured / on error.
        Caller should not crash if this returns {} — a failed send must not
        kill the polling loop.
    """
    lap, driver = _ident(packet)

    if not _BASE_URL or not _FLOW_ID:
        log.warning(
            "Langflow not configured (LANGFLOW_URL / LANGFLOW_FLOW_ID missing) — "
            "payload logged but not sent: lap=%s driver=%s",
            lap, driver,
        )
        return {}

    headers = {
        "Content-Type": "application/json",
        "x-api-key": _API_KEY,
    }
    payload = {
        "input_value": json.dumps(packet),
        "input_type": "chat",   # routes into the flow's ChatInput; "text" leaves it empty
        "output_type": "chat",
    }

    try:
        resp = requests.post(_endpoint(), headers=headers, json=payload, timeout=_TIMEOUT_S)
        resp.raise_for_status()
        data = resp.json()
        log.debug("Langflow OK lap=%s driver=%s", lap, driver)
        return data
    except requests.HTTPError as exc:
        log.error(
            "Langflow HTTP %s for lap=%s driver=%s — %s",
            exc.response.status_code, lap, driver, exc.response.text[:500],
        )
    except requests.Timeout:
        log.error("Langflow timeout after %ss for lap=%s driver=%s", _TIMEOUT_S, lap, driver)
    except Exception as exc:
        log.error("Langflow send failed for lap=%s driver=%s — %s", lap, driver, exc)
    return {}


def extract_text(response: dict) -> str | None:
    """Pull the commentary string out of Langflow's response structure."""
    try:
        return response["outputs"][0]["outputs"][0]["results"]["message"]["text"]
    except (KeyError, IndexError, TypeError):
        return None
