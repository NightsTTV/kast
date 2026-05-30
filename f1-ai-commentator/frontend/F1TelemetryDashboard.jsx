import React, { useState, useEffect, useRef, useCallback } from 'react';
import wizardImg from './cartoon-of-a-wise-old-wizard-in-a-blue-robe-and-hat-holding-a-glowing-staff-isolated-on-a-transparent-background-png.webp';

// ---------------------------------------------------------------------------
// Backend (Phase 4) endpoints. Override with VITE_API_BASE if hosted elsewhere.
//   WS:   ws://localhost:8000/ws/race   — TELEMETRY_UPDATE / COMMENTARY_UPDATE / RACE_FINISHED
//   REST: /api/status, /api/standings, /api/history/{driver}
// ---------------------------------------------------------------------------
const API = import.meta.env?.VITE_API_BASE || 'http://localhost:8000';
const WS_URL = API.replace(/^http/, 'ws') + '/ws/race';

const FEED_LIMIT = 20;

// Team colours keyed by 3-letter code (covers 2023-2026 grids; default grey).
const TEAM_COLORS = {
  VER: '#3671C6', PER: '#3671C6',                          // Red Bull
  LEC: '#E8002D', SAI: '#E8002D', HAM: '#E8002D',          // Ferrari (HAM from 2025)
  RUS: '#27F4D2', ANT: '#27F4D2',                          // Mercedes
  NOR: '#FF8000', PIA: '#FF8000',                          // McLaren
  ALO: '#229971', STR: '#229971',                          // Aston Martin
  GAS: '#0093CC', OCO: '#0093CC', COL: '#0093CC',          // Alpine
  ALB: '#64C4FF', SAR: '#64C4FF',                          // Williams
  TSU: '#6692FF', RIC: '#6692FF', LAW: '#6692FF',          // RB / AlphaTauri
  HAD: '#6692FF', LIN: '#6692FF', DEV: '#6692FF',
  BOT: '#52E252', ZHO: '#52E252', BOR: '#52E252', HUL: '#52E252',  // Sauber
  MAG: '#B6BABD', BEA: '#B6BABD',                          // Haas
};
const teamColor = (code) => TEAM_COLORS[code] || '#8b8b8b';

const COMPOUND_COLORS = {
  SOFT: '#ff3b3b', MEDIUM: '#ffd23b', HARD: '#e8e8e8',
  INTERMEDIATE: '#3bd16f', WET: '#3b8bff',
};
const compoundColor = (c) => COMPOUND_COLORS[c] || '#8b8b8b';

const TRIGGER_LABELS = {
  fast_lap: 'FAST LAP', slow_lap: 'SLOW LAP', sector_outlier: 'SECTOR',
  pit_anomaly: 'PIT', drs_battle: 'DRS',
};

// ── formatting helpers ──────────────────────────────────────────────────────
const fmtLap = (ms) => {
  if (ms == null) return '—';
  const m = Math.floor(ms / 60000);
  const s = ((ms % 60000) / 1000).toFixed(3).padStart(6, '0');
  return `${m}:${s}`;
};
const fmtSec = (ms) => (ms == null ? '—' : (ms / 1000).toFixed(3));
const fmtGap = (ms) => {
  if (ms == null) return '—';
  if (ms === 0) return 'LEADER';
  return `+${(ms / 1000).toFixed(3)}`;
};

// ===========================================================================
// Wizard commentary card — shows the latest line with a typing effect.
// ===========================================================================
function WizardCard({ latest }) {
  const [typed, setTyped] = useState('');
  const text = latest?.text || '';

  useEffect(() => {
    if (!text) { setTyped(''); return; }
    let i = 0;
    setTyped('');
    const id = setInterval(() => {
      i += 1;
      setTyped(text.slice(0, i));
      if (i >= text.length) clearInterval(id);
    }, 18);
    return () => clearInterval(id);
  }, [text]);

  return (
    <div className="flex items-stretch gap-3 rounded-2xl p-3"
         style={{ backgroundColor: '#b0c4de', minHeight: 130 }}>
      <div className="flex items-center justify-center rounded-xl overflow-hidden shrink-0"
           style={{ width: 96, height: 96, backgroundColor: '#7b96c8' }}>
        <img src={wizardImg} alt="commentator" className="w-full h-full object-contain" />
      </div>
      <div className="flex-1 flex items-center">
        {latest ? (
          <p className="leading-relaxed"
             style={{ color: '#1e2d78', fontFamily: "'VT323', monospace", fontSize: 20 }}>
            <span className="font-bold" style={{ color: teamColor(latest.driver) }}>
              {latest.driver}{' '}
            </span>
            {typed}<span className="animate-pulse">▮</span>
          </p>
        ) : (
          <p style={{ color: '#41508a', fontFamily: "'VT323', monospace", fontSize: 20 }}>
            Awaiting the first moment worth talking about…
          </p>
        )}
      </div>
    </div>
  );
}

// ===========================================================================
// Main dashboard
// ===========================================================================
export default function F1Dashboard() {
  const [connected, setConnected] = useState(false);
  const [status, setStatus] = useState({ running: false, current_lap: 0, total_laps: 0, triggers_fired: 0 });
  const [standings, setStandings] = useState([]);        // [{position, ...snapshot}]
  const [latestTelemetry, setLatestTelemetry] = useState(null);
  const [feed, setFeed] = useState([]);                  // [{id, trigger, driver, text}]
  const [raceFinished, setRaceFinished] = useState(false);
  const [selected, setSelected] = useState(null);        // {driver, laps, pit_stops}
  const feedId = useRef(0);

  // ── WebSocket: telemetry + commentary + race end ─────────────────────────
  useEffect(() => {
    let ws, retry, alive = true;
    const connect = () => {
      ws = new WebSocket(WS_URL);
      ws.onopen = () => setConnected(true);
      ws.onclose = () => {
        setConnected(false);
        if (alive) retry = setTimeout(connect, 1500);   // auto-reconnect
      };
      ws.onerror = () => ws.close();
      ws.onmessage = (e) => {
        const msg = JSON.parse(e.data);
        if (msg.type === 'TELEMETRY_UPDATE') {
          setLatestTelemetry(msg.payload);
        } else if (msg.type === 'COMMENTARY_UPDATE') {
          feedId.current += 1;
          const entry = { id: feedId.current, ...msg.payload };
          setFeed((f) => [entry, ...f].slice(0, FEED_LIMIT));
        } else if (msg.type === 'RACE_FINISHED') {
          setRaceFinished(true);
        }
      };
    };
    connect();
    return () => { alive = false; clearTimeout(retry); ws && ws.close(); };
  }, []);

  // ── Poll status + standings (positions come from REST, not the WS) ───────
  useEffect(() => {
    let alive = true;
    const tick = async () => {
      try {
        const [s, st] = await Promise.all([
          fetch(`${API}/api/status`).then((r) => r.json()),
          fetch(`${API}/api/standings`).then((r) => r.json()),
        ]);
        if (!alive) return;
        setStatus(s);
        if (Array.isArray(st)) setStandings(st);
      } catch { /* backend not up yet — keep last known */ }
    };
    tick();
    const id = setInterval(tick, 1500);
    return () => { alive = false; clearInterval(id); };
  }, []);

  const openHistory = useCallback(async (driver) => {
    try {
      const data = await fetch(`${API}/api/history/${driver}`).then((r) => r.json());
      setSelected(data);
    } catch { /* ignore */ }
  }, []);

  const latestFeed = feed[0] || null;

  return (
    <div className="min-h-screen text-white" style={{ background: '#0a0a0f' }}>
      {/* Header */}
      <header className="flex items-center justify-between px-6 py-3 border-b border-white/10"
              style={{ background: 'linear-gradient(90deg,#15151f,#0a0a0f)' }}>
        <div className="flex items-baseline gap-3">
          <span className="text-xl font-bold tracking-widest" style={{ color: '#e10600' }}>F1</span>
          <span className="text-xl font-bold tracking-widest">LIVE TELEMETRY</span>
        </div>
        <div className="flex items-center gap-6 text-sm">
          <Stat label="LAP" value={`${status.current_lap} / ${status.total_laps || '—'}`} />
          <Stat label="TRIGGERS" value={status.triggers_fired} />
          <div className="flex items-center gap-2">
            <span className="w-2.5 h-2.5 rounded-full"
                  style={{ background: connected ? '#3bd16f' : '#e10600' }} />
            <span className="text-white/60">{connected ? 'LIVE' : 'OFFLINE'}</span>
          </div>
        </div>
      </header>

      {raceFinished && (
        <div className="text-center py-2 font-bold tracking-widest"
             style={{ background: '#e10600' }}>🏁 RACE FINISHED 🏁</div>
      )}

      <main className="grid gap-4 p-4" style={{ gridTemplateColumns: '1.4fr 1fr' }}>
        {/* Standings */}
        <section className="rounded-xl border border-white/10 overflow-hidden"
                 style={{ background: '#12121a' }}>
          <h2 className="px-4 py-2 text-xs tracking-widest text-white/50 border-b border-white/10">STANDINGS</h2>
          <table className="w-full text-sm">
            <thead className="text-white/40 text-xs">
              <tr>
                <th className="text-left px-3 py-2">P</th>
                <th className="text-left px-2 py-2">DRIVER</th>
                <th className="text-right px-2 py-2">LAST LAP</th>
                <th className="text-right px-2 py-2">GAP</th>
                <th className="text-center px-3 py-2">TYRE</th>
              </tr>
            </thead>
            <tbody>
              {standings.map((d) => (
                <tr key={d.driver}
                    onClick={() => openHistory(d.driver)}
                    className="border-t border-white/5 hover:bg-white/5 cursor-pointer">
                  <td className="px-3 py-2 text-white/50">{d.position ?? '—'}</td>
                  <td className="px-2 py-2 font-bold">
                    <span className="inline-block w-1 h-4 mr-2 align-middle rounded"
                          style={{ background: teamColor(d.driver) }} />
                    {d.driver}
                  </td>
                  <td className="px-2 py-2 text-right tabular-nums">{fmtLap(d.lap_time_ms)}</td>
                  <td className="px-2 py-2 text-right tabular-nums text-white/70">{fmtGap(d.gap_ahead_ms)}</td>
                  <td className="px-3 py-2 text-center">
                    {d.compound ? (
                      <span className="text-xs font-bold" style={{ color: compoundColor(d.compound) }}>
                        {d.compound[0]}{d.tire_age != null ? `·${d.tire_age}` : ''}
                      </span>
                    ) : '—'}
                  </td>
                </tr>
              ))}
              {standings.length === 0 && (
                <tr><td colSpan={5} className="px-3 py-6 text-center text-white/30">
                  Waiting for telemetry…
                </td></tr>
              )}
            </tbody>
          </table>
        </section>

        {/* Right column: telemetry card + commentary */}
        <section className="flex flex-col gap-4">
          {/* Latest telemetry */}
          <div className="rounded-xl border border-white/10 p-4" style={{ background: '#12121a' }}>
            <h2 className="text-xs tracking-widest text-white/50 mb-3">LATEST LAP</h2>
            {latestTelemetry ? (
              <>
                <div className="flex items-center justify-between mb-3">
                  <div className="flex items-center gap-2">
                    <span className="inline-block w-1.5 h-8 rounded"
                          style={{ background: teamColor(latestTelemetry.driver) }} />
                    <span className="text-3xl font-bold">{latestTelemetry.driver}</span>
                    <span className="text-white/40 text-sm">L{latestTelemetry.lap_num}</span>
                  </div>
                  <span className="text-3xl font-bold tabular-nums">{fmtLap(latestTelemetry.lap_time_ms)}</span>
                </div>
                <div className="grid grid-cols-3 gap-2 text-center">
                  {[['S1', latestTelemetry.sector_1_ms], ['S2', latestTelemetry.sector_2_ms], ['S3', latestTelemetry.sector_3_ms]]
                    .map(([k, v]) => (
                      <div key={k} className="rounded-lg py-2" style={{ background: '#1c1c28' }}>
                        <div className="text-white/40 text-xs">{k}</div>
                        <div className="tabular-nums font-bold">{fmtSec(v)}</div>
                      </div>
                    ))}
                </div>
                <div className="flex justify-between mt-3 text-xs text-white/50">
                  <span>GAP {fmtGap(latestTelemetry.gap_ahead_ms)}</span>
                  <span style={{ color: compoundColor(latestTelemetry.compound) }}>
                    {latestTelemetry.compound || '—'} · {latestTelemetry.tire_age ?? '—'} laps
                  </span>
                  <span>{latestTelemetry.track_temp_c != null ? `${latestTelemetry.track_temp_c.toFixed(0)}°C track` : ''}</span>
                </div>
              </>
            ) : <div className="text-white/30 py-6 text-center">Waiting for telemetry…</div>}
          </div>

          {/* Wizard + commentary feed */}
          <div className="rounded-xl border border-white/10 p-4 flex flex-col gap-3"
               style={{ background: '#12121a' }}>
            <h2 className="text-xs tracking-widest text-white/50">COMMENTARY</h2>
            <WizardCard latest={latestFeed} />
            <div className="flex flex-col gap-2 overflow-y-auto" style={{ maxHeight: 280 }}>
              {feed.slice(1).map((c) => (
                <div key={c.id} className="rounded-lg px-3 py-2 text-sm"
                     style={{ background: '#1c1c28', borderLeft: `3px solid ${teamColor(c.driver)}` }}>
                  <span className="font-bold mr-2" style={{ color: teamColor(c.driver) }}>{c.driver}</span>
                  <span className="text-[10px] tracking-widest text-white/40 mr-2">
                    {TRIGGER_LABELS[c.trigger] || c.trigger}
                  </span>
                  <span className="text-white/80">{c.text}</span>
                </div>
              ))}
              {feed.length <= 1 && (
                <div className="text-white/30 text-center py-3 text-sm">No commentary yet.</div>
              )}
            </div>
          </div>
        </section>
      </main>

      {/* Driver history drawer */}
      {selected && (
        <div className="fixed inset-0 bg-black/60 flex items-center justify-center p-6 z-50"
             onClick={() => setSelected(null)}>
          <div className="rounded-xl border border-white/10 p-5 max-w-md w-full"
               style={{ background: '#15151f' }} onClick={(e) => e.stopPropagation()}>
            <div className="flex items-center justify-between mb-3">
              <h3 className="text-lg font-bold">
                <span style={{ color: teamColor(selected.driver) }}>{selected.driver}</span>
                <span className="text-white/40 text-sm ml-2">{selected.track}</span>
              </h3>
              <button className="text-white/40 hover:text-white" onClick={() => setSelected(null)}>✕</button>
            </div>
            <div className="grid grid-cols-2 gap-3 text-sm">
              <Info label="Historical laps" value={selected.laps?.length ?? 0} />
              <Info label="Pit stops" value={selected.pit_stops?.length ?? 0} />
            </div>
            {selected.pit_stops?.length > 0 && (
              <div className="mt-3">
                <div className="text-white/40 text-xs mb-1">RECENT PIT STOPS</div>
                {selected.pit_stops.slice(-4).map((p, i) => (
                  <div key={i} className="flex justify-between text-sm border-t border-white/5 py-1">
                    <span className="text-white/60">{p.year} · lap {p.lap_number}</span>
                    <span className="tabular-nums">{(p.duration_ms / 1000).toFixed(1)}s</span>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}

function Stat({ label, value }) {
  return (
    <div className="flex flex-col items-end leading-tight">
      <span className="text-white/40 text-[10px] tracking-widest">{label}</span>
      <span className="font-bold tabular-nums">{value}</span>
    </div>
  );
}

function Info({ label, value }) {
  return (
    <div className="rounded-lg p-3" style={{ background: '#1c1c28' }}>
      <div className="text-white/40 text-xs">{label}</div>
      <div className="text-2xl font-bold tabular-nums">{value}</div>
    </div>
  );
}
