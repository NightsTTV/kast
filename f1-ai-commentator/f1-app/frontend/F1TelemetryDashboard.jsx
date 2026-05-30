import React, { useState, useEffect, useRef, useCallback } from 'react';
import wizardsSprite from './wizards_sprite.png';

// ── SVG Icons ──────────────────────────────────────────────────────────────
const GripIcon = (props) => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" {...props}>
    <circle cx="9" cy="5" r="1" fill="currentColor" />
    <circle cx="9" cy="12" r="1" fill="currentColor" />
    <circle cx="9" cy="19" r="1" fill="currentColor" />
    <circle cx="15" cy="5" r="1" fill="currentColor" />
    <circle cx="15" cy="12" r="1" fill="currentColor" />
    <circle cx="15" cy="19" r="1" fill="currentColor" />
  </svg>
);

const MaximizeIcon = (props) => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" {...props}>
    <polyline points="15 3 21 3 21 9" />
    <polyline points="9 21 3 21 3 15" />
    <line x1="21" y1="3" x2="14" y2="10" />
    <line x1="3" y1="21" x2="10" y2="14" />
  </svg>
);

const MinimizeIcon = (props) => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" {...props}>
    <polyline points="4 14 10 14 10 20" />
    <polyline points="20 10 14 10 14 4" />
    <line x1="14" y1="10" x2="21" y2="3" />
    <line x1="10" y1="14" x2="3" y2="21" />
  </svg>
);

const CloseIcon = (props) => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" {...props}>
    <line x1="18" y1="6" x2="6" y2="18" />
    <line x1="6" y1="6" x2="18" y2="18" />
  </svg>
);

const FilterIcon = (props) => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" {...props}>
    <polygon points="22 3 2 3 10 12.46 10 19 14 21 14 12.46 22 3" />
  </svg>
);

const PlayIcon = (props) => (
  <svg viewBox="0 0 24 24" fill="currentColor" {...props}>
    <polygon points="5 3 19 12 5 21 5 3" />
  </svg>
);

const PauseIcon = (props) => (
  <svg viewBox="0 0 24 24" fill="currentColor" {...props}>
    <rect x="6" y="4" width="4" height="16" />
    <rect x="14" y="4" width="4" height="16" />
  </svg>
);

const ResetIcon = (props) => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" {...props}>
    <path d="M23 4v6h-6" />
    <path d="M20.49 15a9 9 0 1 1-2.12-9.36L23 10" />
  </svg>
);

const FlagIcon = (props) => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" {...props}>
    <path d="M4 15s1-1 4-1 5 2 8 2 4-1 4-1V3s-1 1-4 1-5-2-8-2-4 1-4 1z" />
    <line x1="4" y1="22" x2="4" y2="15" />
  </svg>
);

const InfoIcon = (props) => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" {...props}>
    <circle cx="12" cy="12" r="10" />
    <line x1="12" y1="16" x2="12" y2="12" />
    <line x1="12" y1="8" x2="12.01" y2="8" />
  </svg>
);

const ChevronDownIcon = (props) => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" {...props}>
    <polyline points="6 9 12 15 18 9" />
  </svg>
);

const TrophyIcon = (props) => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" {...props}>
    <path d="M6 9H4.5a2.5 2.5 0 0 1 0-5H6" />
    <path d="M18 9h1.5a2.5 2.5 0 0 0 0-5H18" />
    <path d="M4 22h16" />
    <path d="M10 14.66V17c0 .55-.45 1-1 1H4v2h16v-2h-5c-.55 0-1-.45-1-1v-2.34" />
    <path d="M12 2a6 6 0 0 1 6 6v5a6 6 0 0 1-6 6 6 6 0 0 1-6-6V8a6 6 0 0 1 6-6z" />
  </svg>
);

const GaugeIcon = (props) => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" {...props}>
    <path d="M12 2a10 10 0 0 0-10 10 10 10 0 0 0 9.31 9.96" />
    <path d="M22 12A10 10 0 0 0 12.04 2" />
    <path d="M12 12L16 8" />
    <circle cx="12" cy="12" r="1.5" fill="currentColor" />
    <path d="M16 16c1.5-1.5 2.5-3.5 2.5-6H14" />
  </svg>
);

const MessageSquareIcon = (props) => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" {...props}>
    <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z" />
  </svg>
);

const TargetIcon = (props) => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" {...props}>
    <circle cx="12" cy="12" r="10" />
    <circle cx="12" cy="12" r="6" />
    <circle cx="12" cy="12" r="2" />
  </svg>
);

// ---------------------------------------------------------------------------
// Backend (Phase 4) endpoints. Override with VITE_API_BASE if hosted elsewhere.
//   WS:   ws://localhost:8000/ws/race   — TELEMETRY_UPDATE / COMMENTARY_UPDATE / RACE_FINISHED
//   REST: /api/status, /api/standings, /api/history/{driver}
// ---------------------------------------------------------------------------
const API = import.meta.env?.VITE_API_BASE || 'http://localhost:8000';
const WS_URL = API.replace(/^http/, 'ws') + '/ws/race';

const FEED_LIMIT = 40;

// Team colours keyed by 3-letter code (covers 2023-2026 grids; default grey).
const TEAM_COLORS = {
  VER: '#3671C6', PER: '#3671C6',
  LEC: '#E8002D', SAI: '#E8002D', HAM: '#E8002D',
  RUS: '#27F4D2', ANT: '#27F4D2',
  NOR: '#FF8000', PIA: '#FF8000',
  ALO: '#229971', STR: '#229971',
  GAS: '#0093CC', OCO: '#0093CC', COL: '#0093CC',
  ALB: '#64C4FF', SAR: '#64C4FF',
  TSU: '#6692FF', RIC: '#6692FF', LAW: '#6692FF',
  HAD: '#6692FF', LIN: '#6692FF', DEV: '#6692FF',
  BOT: '#52E252', ZHO: '#52E252', BOR: '#52E252', HUL: '#52E252',
  MAG: '#B6BABD', BEA: '#B6BABD',
};
const teamColor = (code) => TEAM_COLORS[code] || '#8b8b8b';

const hexToRgba = (hex, alpha) => {
  if (!hex || !hex.startsWith('#')) return `rgba(100, 130, 180, ${alpha})`;
  const r = parseInt(hex.slice(1, 3), 16);
  const g = parseInt(hex.slice(3, 5), 16);
  const b = parseInt(hex.slice(5, 7), 16);
  return `rgba(${r}, ${g}, ${b}, ${alpha})`;
};

const getWizardCoords = (driver) => {
  const code = driver?.toUpperCase();
  switch (code) {
    // Red Bull (Blue)
    case 'VER':
    case 'PER':
      return { r: 0, c: 0 };
    // Ferrari (Red)
    case 'LEC':
    case 'SAI':
    case 'HAM':
      return { r: 0, c: 1 };
    // Aston Martin (Green)
    case 'ALO':
    case 'STR':
      return { r: 0, c: 2 };
    // VCARB / AlphaTauri (Indigo/Purple-Blue)
    case 'TSU':
    case 'RIC':
    case 'LAW':
    case 'HAD':
    case 'LIN':
    case 'DEV':
      return { r: 0, c: 4 };
    // McLaren (Orange)
    case 'NOR':
    case 'PIA':
      return { r: 0, c: 5 };
    // Sauber (Light Green)
    case 'BOT':
    case 'ZHO':
    case 'BOR':
    case 'HUL':
      return { r: 1, c: 2 };
    // Mercedes (Teal)
    case 'RUS':
    case 'ANT':
      return { r: 1, c: 3 };
    // Williams (Royal Blue)
    case 'ALB':
    case 'SAR':
      return { r: 1, c: 4 };
    // Alpine (Pink)
    case 'GAS':
    case 'OCO':
    case 'COL':
      return { r: 1, c: 5 };
    // Haas (Slate Grey)
    case 'MAG':
    case 'BEA':
      return { r: 2, c: 2 };
    // Default (White Wizard)
    default:
      return { r: 3, c: 5 };
  }
};

const DRIVER_NAMES = {
  VER: 'Max Verstappen', PER: 'Sergio Pérez', HAM: 'Lewis Hamilton', RUS: 'George Russell',
  LEC: 'Charles Leclerc', SAI: 'Carlos Sainz', ALO: 'Fernando Alonso', STR: 'Lance Stroll',
  NOR: 'Lando Norris', PIA: 'Oscar Piastri', GAS: 'Pierre Gasly', OCO: 'Esteban Ocon',
  ALB: 'Alexander Albon', SAR: 'Logan Sargeant', BOT: 'Valtteri Bottas', ZHO: 'Zhou Guanyu',
  MAG: 'Kevin Magnussen', HUL: 'Nico Hülkenberg', TSU: 'Yuki Tsunoda', RIC: 'Daniel Ricciardo',
  LAW: 'Liam Lawson', DEV: 'Nyck de Vries', ANT: 'Andrea Kimi Antonelli', BEA: 'Oliver Bearman',
  BOR: 'Gabriel Bortoleto', COL: 'Franco Colapinto', HAD: 'Isack Hadjar', LIN: 'Arvid Lindblad',
};
const driverName = (code) => DRIVER_NAMES[code] || code;

const COMPOUND_COLORS = {
  SOFT: '#ff3b3b', MEDIUM: '#ffd23b', HARD: '#e8e8e8',
  INTERMEDIATE: '#3bd16f', WET: '#3b8bff',
};
const compoundColor = (c) => COMPOUND_COLORS[c] || '#8b8b8b';

const TRIGGER_LABELS = {
  fast_lap: 'FAST LAP', slow_lap: 'SLOW LAP', sector_outlier: 'SECTOR',
  pit_anomaly: 'PIT', drs_battle: 'DRS',
};

// Plain-English definitions for the expandable "What do these mean?" footer.
const GLOSSARY = [
  ['Position (P)', 'The driver’s current rank in the race — P1 is leading.'],
  ['Lap time', 'How long a full lap took, shown as minutes:seconds (e.g. 1:16.800).'],
  ['Gap', 'How far behind the car ahead this driver is, in seconds (e.g. +2.3s). “LEADER” means they’re first.'],
  ['S1 / S2 / S3', 'Each lap is split into three timed sections (sectors): S1 is the first part of the track, S2 the middle, S3 the final part.'],
  ['Tyre / Compound', 'The type of tyre fitted — a trade-off between outright speed and how long it lasts.'],
  ['Soft · Medium · Hard', 'Dry tyres: Soft (red) is fastest but wears out quickly; Hard (white) is slowest but lasts longest; Medium (yellow) sits in between.'],
  ['Intermediate', 'Green-marked tyre for a damp or drying track (light rain).'],
  ['Wet', 'Blue-marked tyre for heavy rain and standing water.'],
  ['Tyre age', 'How many laps the current set of tyres has run — older tyres are slower.'],
  ['Pit stop', 'When a car enters the pit lane to change tyres; measured in seconds.'],
  ['DRS', 'Drag Reduction System — a rear-wing flap that opens on straights to add speed when within 1 second of the car ahead, helping overtakes.'],
];

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
// Panel header — shared between inline and expanded views
// ===========================================================================
function PanelHeader({ title, icon, expanded, onToggle, children, dragHandle }) {
  return (
    <div className="flex items-center justify-between"
         style={{
           padding: expanded ? undefined : '10px 16px',
           borderBottom: '1px solid rgba(255,255,255,0.06)',
         }}>
      <div className="flex items-center gap-2">
        {dragHandle && (
          <span className="drag-grip flex items-center justify-center" title="Drag to rearrange" {...dragHandle}>
            <GripIcon className="w-3.5 h-3.5 text-white/20 hover:text-white/50 transition-colors duration-150" />
          </span>
        )}
        {icon && <span className="text-white/40 flex items-center">{icon}</span>}
        <h2 className="text-xs font-semibold tracking-widest text-white/40">{title}</h2>
      </div>
      <div className="flex items-center gap-2">
        {children}
        <button className="panel-expand-btn flex items-center justify-center focus-visible:ring-1 focus-visible:ring-accent focus-visible:outline-none"
                onClick={onToggle}
                aria-label={expanded ? `Collapse ${title}` : `Expand ${title}`}
                title={expanded ? 'Collapse' : 'Expand'}>
          {expanded ? <MinimizeIcon className="w-3.5 h-3.5" /> : <MaximizeIcon className="w-3.5 h-3.5" />}
        </button>
      </div>
    </div>
  );
}

// ===========================================================================
// Glossary footer — collapsible "What do these mean?" help in expanded panels
// ===========================================================================
function GlossaryFooter() {
  const [open, setOpen] = useState(false);
  return (
    <div style={{ flexShrink: 0, borderTop: '1px solid rgba(255,255,255,0.06)' }}>
      <button className="btn-press focus-visible:ring-1 focus-visible:ring-accent focus-visible:outline-none"
              onClick={() => setOpen((o) => !o)}
              style={{
                width: '100%', display: 'flex', alignItems: 'center',
                justifyContent: 'space-between', padding: '12px 20px',
                background: 'transparent', border: 'none', cursor: 'pointer',
                color: 'rgba(255,255,255,0.4)', fontSize: 11, fontWeight: 600,
                letterSpacing: '0.12em', textTransform: 'uppercase',
              }}>
        <span className="flex items-center gap-1.5">
          <InfoIcon className="w-3.5 h-3.5" />
          <span>What do these mean?</span>
        </span>
        <ChevronDownIcon className="w-3.5 h-3.5 transition-transform duration-150"
                         style={{ transform: open ? 'rotate(180deg)' : 'none' }} />
      </button>
      {open && (
        <div className="feed-item-enter"
             style={{ padding: '0 20px 16px', maxHeight: 260, overflowY: 'auto' }}>
          {GLOSSARY.map(([term, def]) => (
            <div key={term}
                 style={{ padding: '8px 0', borderTop: '1px solid rgba(255,255,255,0.05)' }}>
              <div style={{ color: '#fff', fontWeight: 600, fontSize: 13 }}>{term}</div>
              <div style={{ color: 'rgba(255,255,255,0.55)', fontSize: 13,
                            lineHeight: 1.5, marginTop: 2 }}>{def}</div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

// ===========================================================================
// Expanded panel overlay — wraps any section in a foreground modal
// ===========================================================================
function ExpandedOverlay({ onClose, wide, children }) {
  // Close on Escape key
  useEffect(() => {
    const handler = (e) => { if (e.key === 'Escape') onClose(); };
    window.addEventListener('keydown', handler);
    return () => window.removeEventListener('keydown', handler);
  }, [onClose]);

  return (
    <div className="expanded-overlay" onClick={onClose}>
      <div className={`expanded-panel ${wide ? 'expanded-panel--wide' : ''}`}
           onClick={(e) => e.stopPropagation()}>
        {children}
        <GlossaryFooter />
      </div>
    </div>
  );
}

// ===========================================================================
// Wizard commentary card — shows the latest line with a typing effect.
// ===========================================================================
function WizardCard({ latest, expanded }) {
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

  const imgSize = expanded ? 120 : 96;
  const fontSize = expanded ? 24 : 20;

  const color = latest ? teamColor(latest.driver) : '#6482b4';
  const cardBg = latest ? hexToRgba(color, 0.08) : 'rgba(100, 130, 180, 0.12)';
  const cardBorder = latest ? hexToRgba(color, 0.18) : 'rgba(100, 130, 180, 0.15)';
  const avatarBg = latest ? hexToRgba(color, 0.15) : 'rgba(100, 130, 180, 0.2)';

  const coords = getWizardCoords(latest?.driver);
  const xPercent = (coords.c / 5) * 100;
  const yPercent = (coords.r / 3) * 100;

  return (
    <div className="flex items-stretch gap-4"
         style={{
           backgroundColor: cardBg,
           borderRadius: 'var(--radius-lg)',
           border: `1px solid ${cardBorder}`,
           minHeight: expanded ? 160 : 130,
           backdropFilter: 'blur(8px)',
           padding: expanded ? 20 : 12,
           transition: 'background-color 300ms var(--ease-out), border-color 300ms var(--ease-out)',
         }}>
      <div className="flex items-center justify-center overflow-hidden shrink-0"
           style={{
             width: imgSize, height: imgSize,
             borderRadius: 'var(--radius-md)',
             backgroundColor: avatarBg,
             backgroundImage: `url(${wizardsSprite})`,
             backgroundSize: '600% 400%',
             backgroundPosition: `${xPercent}% ${yPercent}%`,
             backgroundRepeat: 'no-repeat',
             transition: 'background-color 300ms var(--ease-out)',
           }}
           role="img"
           aria-label={latest ? `Wizard commentator color-coded for ${latest.driver}` : 'Awaiting commentary'}
      />
      <div className="flex-1 flex items-center">
        {latest ? (
          <p className="leading-relaxed"
             style={{ color: '#c8d6f0', fontFamily: "'VT323', monospace", fontSize }}>
            <span className="font-bold" style={{ color: teamColor(latest.driver) }}>
              {latest.driver}{' '}
            </span>
            {typed}<span className="cursor-blink">▮</span>
          </p>
        ) : (
          <p style={{ color: '#6b7db0', fontFamily: "'VT323', monospace", fontSize }}>
            Awaiting the first moment worth talking about…
          </p>
        )}
      </div>
    </div>
  );
}

// ===========================================================================
// Feed item — used in both inline and expanded commentary
// ===========================================================================
function FeedItem({ c, expanded }) {
  return (
    <div className={expanded ? 'feed-item-expanded' : 'feed-item-enter text-sm'}
         style={{
           background: '#181824',
           borderRadius: expanded ? 'var(--radius-md)' : 'var(--radius-sm)',
           borderLeft: `3px solid ${teamColor(c.driver)}`,
           padding: expanded ? '12px 16px' : '8px 12px',
           lineHeight: expanded ? 1.6 : undefined,
         }}>
      <span className={`feed-driver font-semibold mr-2 ${expanded ? 'text-base' : ''}`}
            style={{ color: teamColor(c.driver) }}>{c.driver}</span>
      <span className={`tracking-widest text-white/35 mr-2 font-medium ${expanded ? 'text-xs' : 'text-[10px]'}`}>
        {TRIGGER_LABELS[c.trigger] || c.trigger}
      </span>
      <span className={`feed-text ${expanded ? 'text-white/85' : 'text-white/75'}`}>{c.text}</span>
    </div>
  );
}

// ===========================================================================
// Standings table — used in both inline and expanded
// ===========================================================================
function StandingsTable({ standings, onDriverClick, expanded, focusDriver }) {
  return (
    <table className="w-full text-sm">
      <thead className="text-white/35 text-xs">
        <tr>
          <th className="text-left px-3 py-2 font-medium">P</th>
          <th className="text-left px-2 py-2 font-medium">DRIVER</th>
          {expanded && <th className="text-left px-2 py-2 font-medium">NAME</th>}
          <th className="text-right px-2 py-2 font-medium">LAST LAP</th>
          <th className="text-right px-2 py-2 font-medium">GAP</th>
          <th className="text-center px-3 py-2 font-medium">TYRE</th>
        </tr>
      </thead>
      <tbody>
        {standings.map((d) => {
          const isFocused = !focusDriver || d.driver === focusDriver;
          return (
            <tr key={d.driver}
                onClick={() => onDriverClick(d.driver)}
                className="row-hover cursor-pointer"
                style={{
                  borderTop: '1px solid rgba(255,255,255,0.04)',
                  opacity: isFocused ? 1 : 0.25,
                  transition: 'opacity 200ms var(--ease-out)',
                }}
                onMouseEnter={(e) => {
                  if (isFocused) e.currentTarget.style.backgroundColor = 'rgba(255,255,255,0.04)';
                }}
                onMouseLeave={(e) => {
                  e.currentTarget.style.backgroundColor = 'transparent';
                }}>
              <td className="px-3 py-2 text-white/40 tabular-nums">{d.position ?? '—'}</td>
              <td className="px-2 py-2 font-semibold">
                <span className="inline-block w-1 h-4 mr-2 align-middle"
                      style={{ background: teamColor(d.driver), borderRadius: 2 }} />
                {d.driver}
              </td>
              {expanded && (
                <td className="px-2 py-2 text-white/50">{driverName(d.driver)}</td>
              )}
              <td className="px-2 py-2 text-right tabular-nums">{fmtLap(d.lap_time_ms)}</td>
              <td className="px-2 py-2 text-right tabular-nums text-white/60">{fmtGap(d.gap_ahead_ms)}</td>
              <td className="px-3 py-2 text-center">
                {d.compound ? (
                  <span className="text-xs font-bold" style={{ color: compoundColor(d.compound) }}>
                    {d.compound[0]}{d.tire_age != null ? `·${d.tire_age}` : ''}
                  </span>
                ) : '—'}
              </td>
            </tr>
          );
        })}
        {standings.length === 0 && (
          <tr><td colSpan={expanded ? 6 : 5} className="px-3 py-8 text-center text-white/25 text-sm">
            Waiting for telemetry…
          </td></tr>
        )}
      </tbody>
    </table>
  );
}

// ===========================================================================
// Telemetry detail — used in both inline and expanded
// ===========================================================================
function TelemetryDetail({ t, expanded }) {
  if (!t) return <div className="text-white/25 py-8 text-center text-sm">Waiting for telemetry…</div>;

  return (
    <>
      <div className="flex items-center justify-between mb-4">
        <div className="flex items-center gap-3">
          <span className="inline-block"
                style={{
                  width: expanded ? 4 : 6, height: expanded ? 48 : 32,
                  background: teamColor(t.driver), borderRadius: 3,
                }} />
          <div>
            <span className={`font-bold tracking-tight ${expanded ? 'text-5xl' : 'text-3xl'}`}>{t.driver}</span>
            {expanded && <span className="text-white/40 text-lg ml-3 font-medium">{driverName(t.driver)}</span>}
            <span className="text-white/35 text-sm font-medium ml-2">L{t.lap_num}</span>
          </div>
        </div>
        <span className={`font-bold tabular-nums tracking-tight ${expanded ? 'text-5xl' : 'text-3xl'}`}>
          {fmtLap(t.lap_time_ms)}
        </span>
      </div>
      <div className={`grid grid-cols-3 ${expanded ? 'gap-3' : 'gap-2'} text-center`}>
        {[['S1', t.sector_1_ms], ['S2', t.sector_2_ms], ['S3', t.sector_3_ms]]
          .map(([k, v]) => (
            <div key={k}
                 style={{
                   background: '#181824',
                   borderRadius: 'var(--radius-md)',
                   border: '1px solid rgba(255,255,255,0.04)',
                   padding: expanded ? '16px 12px' : '10px 8px',
                 }}>
              <div className="text-white/35 text-xs font-medium tracking-wide">{k}</div>
              <div className={`tabular-nums font-bold ${expanded ? 'text-2xl mt-1' : 'text-lg'}`}>{fmtSec(v)}</div>
            </div>
          ))}
      </div>
      <div className={`flex justify-between ${expanded ? 'mt-4 text-sm' : 'mt-3 text-xs'} text-white/40 font-medium`}>
        <span>GAP {fmtGap(t.gap_ahead_ms)}</span>
        <span style={{ color: compoundColor(t.compound) }}>
          {t.compound || '—'} · {t.tire_age ?? '—'} laps
        </span>
        <span>{t.track_temp_c != null ? `${t.track_temp_c.toFixed(0)}°C track` : ''}</span>
      </div>
    </>
  );
}
// ===========================================================================
// Driver Focus Panel — detailed lap-by-lap history and summary
// ===========================================================================
function FocusPanel({ focusDriver, setFocusDriver, standings, raceHistory, expanded, onToggle, dragHandle }) {
  const [showSelector, setShowSelector] = useState(false);

  const titleText = focusDriver
    ? `DRIVER FOCUS: ${focusDriver} (${driverName(focusDriver)})`
    : 'DRIVER FOCUS';

  const filterBtn = (
    <button
      onClick={() => setShowSelector((prev) => !prev)}
      className="btn-press text-[10px] bg-white/5 hover:bg-white/10 px-2.5 py-1 font-bold tracking-wider rounded text-white/80 flex items-center gap-1 focus-visible:ring-1 focus-visible:ring-accent focus-visible:outline-none"
      style={{ borderRadius: 'var(--radius-sm)' }}
    >
      {showSelector ? (
        <>
          <CloseIcon className="w-3 h-3" />
          <span>CLOSE</span>
        </>
      ) : (
        <>
          <FilterIcon className="w-3 h-3" />
          <span>FILTER</span>
        </>
      )}
    </button>
  );

  let content;
  if (!focusDriver) {
    content = (
      <div className={expanded ? 'mt-4' : 'mt-3'}>
        {showSelector && (
          <div className="flex items-center gap-2 mb-4 bg-[#181824] p-2 rounded-lg border border-white/5">
            <select
              value=""
              onChange={(e) => {
                setFocusDriver(e.target.value || null);
                setShowSelector(false);
              }}
              className="bg-[#212130] text-white border border-white/10 rounded px-2 py-1 text-xs font-semibold focus:outline-none focus:border-white/20 cursor-pointer flex-1 focus-visible:ring-1 focus-visible:ring-accent"
              style={{ borderRadius: 'var(--radius-sm)' }}
            >
              <option value="">-- Choose Driver --</option>
              {standings.map((s) => (
                <option key={s.driver} value={s.driver}>
                  {s.driver} - {driverName(s.driver)}
                </option>
              ))}
            </select>
          </div>
        )}
        <div className="text-center py-12 text-white/30 flex flex-col items-center justify-center gap-2">
          <FlagIcon className="w-8 h-8 text-white/20" />
          <p className="text-xs font-semibold tracking-wider">NO DRIVER SELECTED</p>
          <p className="text-[11px] text-white/20 max-w-[200px] leading-normal">
            Click a standings row or click the filter button to choose a driver.
          </p>
          {!showSelector && (
            <button
              onClick={() => setShowSelector(true)}
              className="btn-press mt-2 text-[10px] bg-[#e10600] hover:bg-[#ff1e18] text-white font-bold tracking-wider px-3 py-1.5 rounded flex items-center gap-1 focus-visible:ring-1 focus-visible:ring-accent focus-visible:outline-none"
              style={{ borderRadius: 'var(--radius-sm)' }}
            >
              <FilterIcon className="w-3 h-3" />
              <span>CHOOSE DRIVER</span>
            </button>
          )}
        </div>
      </div>
    );
  } else {
    const laps = Object.values(raceHistory[focusDriver] || {}).sort((a, b) => a.lap_num - b.lap_num);
    const sortedLaps = [...laps].reverse();

    const validLaps = laps.filter((l) => l.lap_time_ms && l.lap_time_ms > 0);
    const bestLapMs = validLaps.length > 0 ? Math.min(...validLaps.map((l) => l.lap_time_ms)) : null;
    const avgLapMs = validLaps.length > 0 ? (validLaps.reduce((acc, l) => acc + l.lap_time_ms, 0) / validLaps.length) : null;

    const s1Laps = laps.filter((l) => l.sector_1_ms && l.sector_1_ms > 0);
    const bestS1 = s1Laps.length > 0 ? Math.min(...s1Laps.map((l) => l.sector_1_ms)) : null;

    const s2Laps = laps.filter((l) => l.sector_2_ms && l.sector_2_ms > 0);
    const bestS2 = s2Laps.length > 0 ? Math.min(...s2Laps.map((l) => l.sector_2_ms)) : null;

    const s3Laps = laps.filter((l) => l.sector_3_ms && l.sector_3_ms > 0);
    const bestS3 = s3Laps.length > 0 ? Math.min(...s3Laps.map((l) => l.sector_3_ms)) : null;

    const latestLap = laps[laps.length - 1];
    const currentTyre = latestLap?.compound || '—';
    const tyreAge = latestLap?.tire_age ?? '—';

    const driverStanding = standings.find((s) => s.driver === focusDriver);
    const position = driverStanding?.position ?? '—';

    content = (
      <div className={expanded ? 'mt-4' : 'mt-3'}>
        {showSelector && (
          <div className="flex items-center gap-2 mb-3 bg-[#181824] p-2 rounded-lg border border-white/5">
            <select
              value={focusDriver || ''}
              onChange={(e) => {
                setFocusDriver(e.target.value || null);
                setShowSelector(false);
              }}
              className="bg-[#212130] text-white border border-white/10 rounded px-2 py-1 text-xs font-semibold focus:outline-none focus:border-white/20 cursor-pointer flex-1 focus-visible:ring-1 focus-visible:ring-accent"
              style={{ borderRadius: 'var(--radius-sm)' }}
            >
              <option value="">-- Choose Driver --</option>
              {standings.map((s) => (
                <option key={s.driver} value={s.driver}>
                  {s.driver} - {driverName(s.driver)}
                </option>
              ))}
            </select>
            <button
              onClick={() => {
                setFocusDriver(null);
                setShowSelector(false);
              }}
              className="btn-press px-2.5 py-1 text-[10px] font-bold tracking-widest text-white/60 bg-white/5 hover:bg-white/10 flex items-center gap-1 focus-visible:ring-1 focus-visible:ring-accent focus-visible:outline-none"
              style={{ borderRadius: 'var(--radius-sm)' }}
            >
              <CloseIcon className="w-3 h-3" />
              <span>CLEAR</span>
            </button>
          </div>
        )}

        {/* Summary Row */}
        <div className="grid grid-cols-5 gap-2 mb-4 text-center">
          <MiniStat label="BEST LAP" value={fmtLap(bestLapMs)} />
          <MiniStat label="AVG LAP" value={fmtLap(avgLapMs)} />
          <MiniStat
            label="BEST SECTORS"
            value={
              <span className="text-[10px] font-bold text-white/80 tabular-nums">
                {bestS1 ? fmtSec(bestS1) : '—'} · {bestS2 ? fmtSec(bestS2) : '—'} · {bestS3 ? fmtSec(bestS3) : '—'}
              </span>
            }
          />
          <MiniStat
            label="TYRE"
            value={
              <span className="font-bold" style={{ color: compoundColor(currentTyre) }}>
                {currentTyre}{tyreAge !== '—' ? ` (${tyreAge}L)` : ''}
              </span>
            }
          />
          <MiniStat label="POS" value={position} />
        </div>

        {/* Lap by Lap Table */}
        <div className="overflow-y-auto" style={{ maxHeight: expanded ? 'calc(100vh - 350px)' : 260 }}>
          <table className="w-full text-xs">
            <thead className="text-white/35 text-[10px] tracking-wider">
              <tr>
                <th className="text-left px-2 py-1.5 font-medium">LAP</th>
                <th className="text-right px-2 py-1.5 font-medium">LAP TIME</th>
                <th className="text-right px-2 py-1.5 font-medium">S1</th>
                <th className="text-right px-2 py-1.5 font-medium">S2</th>
                <th className="text-right px-2 py-1.5 font-medium">S3</th>
                <th className="text-center px-2 py-1.5 font-medium">TYRE</th>
                <th className="text-right px-2 py-1.5 font-medium">GAP</th>
              </tr>
            </thead>
            <tbody>
              {sortedLaps.map((l) => (
                <tr
                  key={l.lap_num}
                  className="row-hover"
                  style={{ borderTop: '1px solid rgba(255,255,255,0.04)' }}
                >
                  <td className="px-2 py-1.5 text-white/40 tabular-nums">{l.lap_num}</td>
                  <td className="px-2 py-1.5 text-right font-semibold tabular-nums">
                    {fmtLap(l.lap_time_ms)}
                  </td>
                  <td className="px-2 py-1.5 text-right text-white/50 tabular-nums">{fmtSec(l.sector_1_ms)}</td>
                  <td className="px-2 py-1.5 text-right text-white/50 tabular-nums">{fmtSec(l.sector_2_ms)}</td>
                  <td className="px-2 py-1.5 text-right text-white/50 tabular-nums">{fmtSec(l.sector_3_ms)}</td>
                  <td className="px-2 py-1.5 text-center">
                    {l.compound ? (
                      <span className="font-bold" style={{ color: compoundColor(l.compound) }}>
                        {l.compound[0]}·{l.tire_age ?? '0'}
                      </span>
                    ) : '—'}
                  </td>
                  <td className="px-2 py-1.5 text-right text-white/60 tabular-nums">{fmtGap(l.gap_ahead_ms)}</td>
                </tr>
              ))}
              {sortedLaps.length === 0 && (
                <tr>
                  <td colSpan={7} className="px-3 py-8 text-center text-white/25 text-sm">
                    No laps recorded for this driver.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    );
  }

  if (expanded) {
    return (
      <>
        <div className="expanded-panel-header">
          <div className="flex items-center gap-2">
            <TargetIcon className="w-4 h-4 text-white/40" />
            <h2 className="text-sm font-semibold tracking-widest text-white/50">{titleText}</h2>
          </div>
          <div className="flex items-center gap-3">
            {filterBtn}
            <button
              className="btn-icon-press text-white/35 text-lg leading-none p-1 flex items-center justify-center focus-visible:ring-1 focus-visible:ring-accent focus-visible:outline-none"
              onClick={onToggle}
              aria-label="Collapse telemetry"
            >
              <CloseIcon className="w-4 h-4" />
            </button>
          </div>
        </div>
        <div className="expanded-panel-body">{content}</div>
      </>
    );
  }

  return (
    <>
      <PanelHeader title={titleText} icon={<TargetIcon className="w-4 h-4" />} expanded={false} dragHandle={dragHandle} onToggle={onToggle}>
        {filterBtn}
      </PanelHeader>
      {content}
    </>
  );
}

function MiniStat({ label, value }) {
  return (
    <div
      style={{
        background: '#181824',
        borderRadius: 'var(--radius-md)',
        border: '1px solid rgba(255,255,255,0.04)',
        padding: '6px 4px',
      }}
    >
      <div className="text-white/35 text-[9px] font-semibold tracking-widest">{label}</div>
      <div className="text-xs font-bold tabular-nums mt-0.5">{value || '—'}</div>
    </div>
  );
}

// ===========================================================================
// Main dashboard
// ===========================================================================
export default function F1Dashboard() {
  const [connected, setConnected] = useState(false);
  const [status, setStatus] = useState({ running: false, current_lap: 0, total_laps: 0, triggers_fired: 0 });
  const [standings, setStandings] = useState([]);
  const [latestTelemetry, setLatestTelemetry] = useState(null);
  const [feed, setFeed] = useState([]);
  const [raceFinished, setRaceFinished] = useState(false);
  const [selected, setSelected] = useState(null);
  const [expanded, setExpanded] = useState(null); // null | 'standings' | 'telemetry' | 'commentary' | 'focus'
  const [focusDriver, setFocusDriver] = useState(null);
  const [raceHistory, setRaceHistory] = useState({}); // { [driver]: { [lapNum]: snapshot } }
  const feedId = useRef(0);
  const headerRef = useRef(null);

  const toggleExpand = useCallback((panel) => {
    setExpanded((prev) => prev === panel ? null : panel);
  }, []);

  const closeExpand = useCallback(() => setExpanded(null), []);

  // ── Drag-and-drop panel ordering ─────────────────────────────────────────
  const [order, setOrder] = useState(['standings', 'telemetry', 'commentary', 'focus']);
  const [dragging, setDragging] = useState(null);   // key being dragged (for dimming)
  const [overKey, setOverKey] = useState(null);      // current drop target (for highlight)
  const dragKeyRef = useRef(null);                   // source of truth for the logic

  const handleDrop = useCallback((targetKey) => {
    const from = dragKeyRef.current;
    setDragging(null); setOverKey(null); dragKeyRef.current = null;
    if (!from || from === targetKey) return;
    setOrder((cur) => {
      const next = cur.filter((k) => k !== from);
      next.splice(next.indexOf(targetKey), 0, from);  // drop before the target
      return next;
    });
  }, []);

  // ── WebSocket: telemetry + commentary + race end ─────────────────────────
  useEffect(() => {
    let ws, retry, alive = true;
    const connect = () => {
      ws = new WebSocket(WS_URL);
      ws.onopen = () => setConnected(true);
      ws.onclose = () => {
        setConnected(false);
        if (alive) retry = setTimeout(connect, 1500);
      };
      ws.onerror = () => ws.close();
      ws.onmessage = (e) => {
        const msg = JSON.parse(e.data);
        if (msg.type === 'TELEMETRY_UPDATE') {
          setLatestTelemetry(msg.payload);
          const snap = msg.payload;
          if (snap && snap.driver && snap.lap_num != null) {
            setRaceHistory((prev) => {
              const dCode = snap.driver;
              const lNum = snap.lap_num;
              const driverHistory = { ...(prev[dCode] || {}) };
              driverHistory[lNum] = snap;
              return {
                ...prev,
                [dCode]: driverHistory,
              };
            });
          }
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

  // ── Poll status + standings ──────────────────────────────────────────────
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
      } catch { /* backend not up yet */ }
    };
    tick();
    const id = setInterval(tick, 1500);
    return () => { alive = false; clearInterval(id); };
  }, []);

  // ── Backfill race history when focusing a driver ──────────────────────────
  useEffect(() => {
    if (!focusDriver) return;

    const backfillHistory = async () => {
      try {
        const res = await fetch(`${API}/api/race-history/${focusDriver}`);
        if (!res.ok) return;
        const data = await res.json();
        const laps = Array.isArray(data) ? data : [];
        if (laps.length === 0) return;

        setRaceHistory((prev) => {
          const driverHistory = { ...(prev[focusDriver] || {}) };
          laps.forEach((lap) => {
            if (lap && lap.lap_num != null) {
              driverHistory[lap.lap_num] = lap;
            }
          });
          return {
            ...prev,
            [focusDriver]: driverHistory,
          };
        });
      } catch (err) {
        console.error('Error backfilling race history:', err);
      }
    };

    backfillHistory();
  }, [focusDriver]);

  // ── Interactive Header Accent Line (Emil Kowalski style lerp) ────────────
  useEffect(() => {
    const header = headerRef.current;
    if (!header) return;

    let targetCenter = 50;
    let targetPeak = 0.6;
    let targetShoulder = 0.4;

    let currentCenter = 50;
    let currentPeak = 0.6;
    let currentShoulder = 0.4;

    let animationActive = false;
    let frameId = null;

    const tick = () => {
      const dCenter = targetCenter - currentCenter;
      const dPeak = targetPeak - currentPeak;
      const dShoulder = targetShoulder - currentShoulder;

      if (Math.abs(dCenter) < 0.01 && Math.abs(dPeak) < 0.001 && Math.abs(dShoulder) < 0.001) {
        currentCenter = targetCenter;
        currentPeak = targetPeak;
        currentShoulder = targetShoulder;
        header.style.setProperty('--line-center', `${currentCenter}%`);
        header.style.setProperty('--line-peak-opacity', currentPeak);
        header.style.setProperty('--line-shoulder-opacity', currentShoulder);
        animationActive = false;
        return;
      }

      currentCenter += dCenter * 0.15;
      currentPeak += dPeak * 0.15;
      currentShoulder += dShoulder * 0.15;

      header.style.setProperty('--line-center', `${currentCenter}%`);
      header.style.setProperty('--line-peak-opacity', currentPeak);
      header.style.setProperty('--line-shoulder-opacity', currentShoulder);

      frameId = requestAnimationFrame(tick);
    };

    const activateAnimation = () => {
      if (!animationActive) {
        animationActive = true;
        frameId = requestAnimationFrame(tick);
      }
    };

    const handleMouseMove = (e) => {
      const rect = header.getBoundingClientRect();
      const clientX = e.clientX;
      const clientY = e.clientY;

      const lineY = rect.bottom;
      let closestX = clientX;
      if (clientX < rect.left) closestX = rect.left;
      else if (clientX > rect.right) closestX = rect.right;

      const dx = clientX - closestX;
      const dy = clientY - lineY;
      const dist = Math.sqrt(dx * dx + dy * dy);

      const threshold = 220;
      let influence = 0;
      if (dist < threshold) {
        const ratio = dist / threshold;
        influence = 1 - ratio * ratio;
      }

      const targetXPercent = ((closestX - rect.left) / rect.width) * 100;
      
      targetCenter = 50 + (targetXPercent - 50) * influence;
      targetPeak = 0.6 + 0.35 * influence;
      targetShoulder = 0.4 + 0.1 * influence;

      activateAnimation();
    };

    const handleMouseLeave = () => {
      targetCenter = 50;
      targetPeak = 0.6;
      targetShoulder = 0.4;
      activateAnimation();
    };

    window.addEventListener('mousemove', handleMouseMove);
    document.addEventListener('mouseleave', handleMouseLeave);

    return () => {
      window.removeEventListener('mousemove', handleMouseMove);
      document.removeEventListener('mouseleave', handleMouseLeave);
      if (frameId) cancelAnimationFrame(frameId);
    };
  }, []);

  const openHistory = useCallback(async (driver) => {
    try {
      const data = await fetch(`${API}/api/history/${driver}`).then((r) => r.json());
      setSelected(data);
    } catch { /* ignore */ }
  }, []);

  const [resetting, setResetting] = useState(false);
  const resetRace = useCallback(async () => {
    setResetting(true);
    setFeed([]); setRaceFinished(false); setLatestTelemetry(null); setStandings([]);
    setFocusDriver(null);
    setRaceHistory({});
    try { await fetch(`${API}/api/reset`, { method: 'POST' }); } catch { /* ignore */ }
    setTimeout(() => setResetting(false), 2500);
  }, []);

  const togglePause = useCallback(async () => {
    const next = !status.paused;
    setStatus((s) => ({ ...s, paused: next }));   // optimistic; the poll reconciles
    try { await fetch(`${API}/api/${next ? 'pause' : 'resume'}`, { method: 'POST' }); }
    catch { /* ignore */ }
  }, [status.paused]);

  const latestFeed = feed[0] || null;

  // ── Card style constant ──────────────────────────────────────────────────
  const cardStyle = {
    background: '#111119',
    borderRadius: 'var(--radius-lg)',
    border: '1px solid rgba(255,255,255,0.08)',
  };

  // Drag handle (grip) + drop-zone (card) props for reorderable panels.
  const handleProps = (key) => ({
    draggable: true,
    onDragStart: (e) => {
      dragKeyRef.current = key; setDragging(key);
      e.dataTransfer.effectAllowed = 'move';
      e.dataTransfer.setData('text/plain', key);   // required for Firefox
    },
    onDragEnd: () => { setDragging(null); setOverKey(null); dragKeyRef.current = null; },
  });
  const dropProps = (key) => ({
    onDragOver: (e) => { e.preventDefault(); e.dataTransfer.dropEffect = 'move';
                         if (overKey !== key) setOverKey(key); },
    onDragLeave: () => { if (overKey === key) setOverKey(null); },
    onDrop: (e) => { e.preventDefault(); handleDrop(key); },
  });
  const panelStyle = (key) => ({
    ...cardStyle,
    opacity: dragging === key ? 0.4 : 1,
    outline: overKey === key && dragging && dragging !== key
      ? '2px dashed rgba(225,6,0,0.7)' : 'none',
    outlineOffset: '2px',
    transition: 'opacity 160ms var(--ease-out)',
  });

  const handleDriverClick = useCallback((code) => {
    setFocusDriver((prev) => (prev === code ? null : code));
  }, []);

  const renderPanel = (key) => {
    if (key === 'standings') {
      return (
        <section key="standings" className="overflow-hidden"
                 style={panelStyle('standings')} {...dropProps('standings')}>
          <PanelHeader title="STANDINGS" icon={<TrophyIcon className="w-4 h-4" />} expanded={false} dragHandle={handleProps('standings')}
                       onToggle={() => toggleExpand('standings')}>
            {focusDriver && (
              <button onClick={() => setFocusDriver(null)}
                      className="btn-press text-[10px] bg-white/5 hover:bg-white/10 px-2.5 py-1 font-bold tracking-wider rounded text-white/80 focus-visible:ring-1 focus-visible:ring-accent focus-visible:outline-none flex items-center gap-1"
                      style={{ borderRadius: 'var(--radius-sm)' }}>
                <CloseIcon className="w-3 h-3" />
                <span>SHOW ALL</span>
              </button>
            )}
          </PanelHeader>
          <StandingsTable standings={standings} onDriverClick={handleDriverClick} expanded={false} focusDriver={focusDriver} />
        </section>
      );
    }
    if (key === 'telemetry') {
      return (
        <div key="telemetry" className="p-4"
             style={panelStyle('telemetry')} {...dropProps('telemetry')}>
          <PanelHeader title="LATEST LAP" icon={<GaugeIcon className="w-4 h-4" />} expanded={false} dragHandle={handleProps('telemetry')}
                       onToggle={() => toggleExpand('telemetry')} />
          <div className="mt-3"><TelemetryDetail t={latestTelemetry} expanded={false} /></div>
        </div>
      );
    }
    if (key === 'focus') {
      return (
        <div key="focus" className="p-4"
             style={panelStyle('focus')} {...dropProps('focus')}>
          <FocusPanel
            focusDriver={focusDriver}
            setFocusDriver={setFocusDriver}
            standings={standings}
            raceHistory={raceHistory}
            expanded={false}
            onToggle={() => toggleExpand('focus')}
            dragHandle={handleProps('focus')}
          />
        </div>
      );
    }
    if (key === 'commentary') {
      const commentaryLeft = order[0] === 'commentary' || order[1] === 'commentary';   // wide left column → taller feed
      return (
        <div key="commentary" className="p-4 flex flex-col gap-3"
             style={panelStyle('commentary')} {...dropProps('commentary')}>
          <PanelHeader title="COMMENTARY" icon={<MessageSquareIcon className="w-4 h-4" />} expanded={false} dragHandle={handleProps('commentary')}
                       onToggle={() => toggleExpand('commentary')}>
            <span className="text-[10px] text-white/25 tabular-nums font-medium">
              {feed.length} msg{feed.length !== 1 ? 's' : ''}
            </span>
          </PanelHeader>
          <WizardCard latest={latestFeed} expanded={false} />
          <div className="flex flex-col gap-2 overflow-y-auto"
               style={{ maxHeight: commentaryLeft ? 'calc(100vh - 330px)' : 280 }}>
            {feed.slice(1).map((c) => (<FeedItem key={c.id} c={c} expanded={false} />))}
            {feed.length <= 1 && (
              <div className="text-white/25 text-center py-4 text-sm">No commentary yet.</div>
            )}
          </div>
        </div>
      );
    }
    return null;
  };

  return (
    <div className="min-h-screen text-white" style={{ background: '#0a0a0f' }}>
      {/* Header */}
      <header ref={headerRef} className="header-accent flex items-center justify-between px-6 py-3"
              style={{ background: 'linear-gradient(90deg, #13131d, #0a0a0f)' }}>
        <div className="flex items-baseline gap-3">
          <span className="text-xl font-bold tracking-widest" style={{ color: '#e10600' }}>F1</span>
          <span className="text-xl font-semibold tracking-widest" style={{ letterSpacing: '0.15em' }}>LIVE TELEMETRY</span>
        </div>
        <div className="flex items-center gap-6 text-sm">
          <Stat label="LAP" value={`${status.current_lap} / ${status.total_laps || '—'}`} />
          <Stat label="TRIGGERS" value={status.triggers_fired} />
          <div className="flex items-center gap-2">
            <span className="status-dot w-2.5 h-2.5 rounded-full"
                  style={{ background: connected ? '#3bd16f' : '#e10600' }} />
            <span className="text-white/60">{connected ? 'LIVE' : 'OFFLINE'}</span>
          </div>
          <button onClick={togglePause}
                  className="btn-press px-3 py-1.5 text-xs font-bold tracking-widest flex items-center gap-1.5 focus-visible:ring-1 focus-visible:ring-accent focus-visible:outline-none"
                  aria-label={status.paused ? 'Resume race' : 'Pause race'}
                  style={{
                    borderRadius: 'var(--radius-sm)',
                    background: status.paused ? '#3bd16f' : '#26263a',
                    color: status.paused ? '#0a0a0f' : '#fff',
                    cursor: 'pointer',
                  }}>
            {status.paused ? (
              <>
                <PlayIcon className="w-3.5 h-3.5 fill-current" />
                <span>RESUME</span>
              </>
            ) : (
              <>
                <PauseIcon className="w-3.5 h-3.5 fill-current" />
                <span>PAUSE</span>
              </>
            )}
          </button>
          <button onClick={resetRace} disabled={resetting}
                  className="btn-press px-3 py-1.5 text-xs font-bold tracking-widest flex items-center gap-1.5 focus-visible:ring-1 focus-visible:ring-accent focus-visible:outline-none"
                  style={{
                    borderRadius: 'var(--radius-sm)',
                    background: resetting ? '#333' : '#e10600',
                    color: '#fff',
                    cursor: resetting ? 'not-allowed' : 'pointer',
                    opacity: resetting ? 0.5 : 1,
                  }}>
            <ResetIcon className={`w-3.5 h-3.5 ${resetting ? 'animate-spin' : ''}`} />
            <span>{resetting ? 'RESETTING…' : 'RESET RACE'}</span>
          </button>
        </div>
      </header>

      {raceFinished && (
        <div className="race-finished-banner text-center py-2 font-bold tracking-widest flex items-center justify-center gap-2"
             style={{ background: '#e10600' }}>
          <FlagIcon className="w-4 h-4 text-white" />
          <span>RACE FINISHED</span>
          <FlagIcon className="w-4 h-4 text-white" />
        </div>
      )}

      {/* Panels are reorderable: drag a panel by its ⠿ grip onto another.
          order[0]/order[1] stack on the left; order[2]/order[3] stack on the right. */}
      <main className="grid gap-4 p-4" style={{ gridTemplateColumns: '1.4fr 1fr' }}>
        <section className="flex flex-col gap-4">
          {renderPanel(order[0])}
          {renderPanel(order[1])}
        </section>
        <section className="flex flex-col gap-4">
          {renderPanel(order[2])}
          {renderPanel(order[3])}
        </section>
      </main>

      {/* ════════════════════════════════════════════════════════════════════
          EXPANDED PANEL OVERLAYS
          ════════════════════════════════════════════════════════════════════ */}

      {/* Expanded: Standings */}
      {expanded === 'standings' && (
        <ExpandedOverlay onClose={closeExpand} wide>
          <div className="expanded-panel-header">
            <div className="flex items-center gap-2">
              <TrophyIcon className="w-4 h-4 text-white/40" />
              <h2 className="text-sm font-semibold tracking-widest text-white/50">STANDINGS</h2>
            </div>
            <div className="flex items-center gap-3">
              {focusDriver && (
                <button onClick={() => setFocusDriver(null)}
                        className="btn-press text-[10px] bg-white/5 hover:bg-white/10 px-2.5 py-1 font-bold tracking-wider rounded text-white/80 focus-visible:ring-1 focus-visible:ring-accent focus-visible:outline-none flex items-center gap-1"
                        style={{ borderRadius: 'var(--radius-sm)' }}>
                  <CloseIcon className="w-3 h-3" />
                  <span>SHOW ALL</span>
                </button>
              )}
              <span className="text-xs text-white/30 tabular-nums">{standings.length} drivers</span>
              <button className="btn-icon-press text-white/35 text-lg leading-none p-1 flex items-center justify-center focus-visible:ring-1 focus-visible:ring-accent focus-visible:outline-none"
                      onClick={closeExpand} aria-label="Collapse standings">
                <CloseIcon className="w-4 h-4" />
              </button>
            </div>
          </div>
          <div className="expanded-panel-body">
            <StandingsTable standings={standings} onDriverClick={handleDriverClick} expanded focusDriver={focusDriver} />
          </div>
        </ExpandedOverlay>
      )}

      {/* Expanded: Telemetry */}
      {expanded === 'telemetry' && (
        <ExpandedOverlay onClose={closeExpand}>
          <div className="expanded-panel-header">
            <div className="flex items-center gap-2">
              <GaugeIcon className="w-4 h-4 text-white/40" />
              <h2 className="text-sm font-semibold tracking-widest text-white/50">LATEST LAP</h2>
            </div>
            <button className="btn-icon-press text-white/35 text-lg leading-none p-1 flex items-center justify-center focus-visible:ring-1 focus-visible:ring-accent focus-visible:outline-none"
                    onClick={closeExpand} aria-label="Collapse telemetry">
              <CloseIcon className="w-4 h-4" />
            </button>
          </div>
          <div className="expanded-panel-body">
            <TelemetryDetail t={latestTelemetry} expanded />
          </div>
        </ExpandedOverlay>
      )}

      {/* Expanded: Focus */}
      {expanded === 'focus' && (
        <ExpandedOverlay onClose={closeExpand} wide>
          <FocusPanel
            focusDriver={focusDriver}
            setFocusDriver={setFocusDriver}
            standings={standings}
            raceHistory={raceHistory}
            expanded
            onToggle={closeExpand}
          />
        </ExpandedOverlay>
      )}

      {/* Expanded: Commentary — foreground for readability */}
      {expanded === 'commentary' && (
        <ExpandedOverlay onClose={closeExpand} wide>
          <div className="expanded-panel-header">
            <div className="flex items-center gap-2">
              <MessageSquareIcon className="w-4 h-4 text-white/40" />
              <h2 className="text-sm font-semibold tracking-widest text-white/50">COMMENTARY</h2>
            </div>
            <div className="flex items-center gap-3">
              <span className="text-xs text-white/30 tabular-nums">{feed.length} messages</span>
              <button className="btn-icon-press text-white/35 text-lg leading-none p-1 flex items-center justify-center focus-visible:ring-1 focus-visible:ring-accent focus-visible:outline-none"
                      onClick={closeExpand} aria-label="Collapse commentary">
                <CloseIcon className="w-4 h-4" />
              </button>
            </div>
          </div>
          <div className="expanded-panel-body expanded-commentary">
            <WizardCard latest={latestFeed} expanded />
            <div className="flex flex-col gap-3 mt-4">
              {feed.slice(1).map((c) => (
                <FeedItem key={c.id} c={c} expanded />
              ))}
              {feed.length <= 1 && (
                <div className="text-white/25 text-center py-8 text-sm">No commentary yet.</div>
              )}
            </div>
          </div>
        </ExpandedOverlay>
      )}

      {/* ── Driver history drawer ─────────────────────────────────────────── */}
      {selected && (
        <div className="backdrop-enter fixed inset-0 flex items-center justify-center p-6 z-50"
             style={{ backgroundColor: 'rgba(0, 0, 0, 0.65)', cursor: 'pointer' }}
             onClick={() => setSelected(null)}>
          <div className="modal-enter max-w-md w-full p-5"
               style={{
                 background: '#14141e',
                 borderRadius: 'var(--radius-xl)',
                 border: '1px solid rgba(255,255,255,0.1)',
                 boxShadow: '0 24px 64px -12px rgba(0, 0, 0, 0.6)',
                 cursor: 'default',
               }}
               onClick={(e) => e.stopPropagation()}>
            <div className="flex items-center justify-between mb-3">
              <h3 className="text-lg font-bold">
                <span style={{ color: teamColor(selected.driver) }}>{driverName(selected.driver)}</span>
                <span className="text-white/35 text-sm ml-2 font-medium">{selected.track}</span>
              </h3>
              <button className="btn-icon-press text-white/35 text-lg leading-none p-1 flex items-center justify-center focus-visible:ring-1 focus-visible:ring-accent focus-visible:outline-none"
                      onClick={() => setSelected(null)}
                      aria-label="Close driver history">
                <CloseIcon className="w-4 h-4" />
              </button>
            </div>
            <div className="grid grid-cols-2 gap-3 text-sm">
              <Info label="Historical laps" value={selected.laps?.length ?? 0} />
              <Info label="Pit stops" value={selected.pit_stops?.length ?? 0} />
            </div>
            {selected.pit_stops?.length > 0 && (
              <div className="mt-3">
                <div className="text-white/35 text-xs font-medium mb-1 tracking-wide">RECENT PIT STOPS</div>
                {selected.pit_stops.slice(-4).map((p, i) => (
                  <div key={i} className="flex justify-between text-sm py-1.5"
                       style={{ borderTop: '1px solid rgba(255,255,255,0.05)' }}>
                     <span className="text-white/50">{p.year} · lap {p.lap_number}</span>
                     <span className="tabular-nums font-medium">{(p.duration_ms / 1000).toFixed(1)}s</span>
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
      <span className="text-white/35 text-[10px] tracking-widest font-medium">{label}</span>
      <span className="font-bold tabular-nums">{value}</span>
    </div>
  );
}

function Info({ label, value }) {
  return (
    <div className="p-3"
         style={{
           background: '#181824',
           borderRadius: 'var(--radius-md)',
           border: '1px solid rgba(255,255,255,0.04)',
         }}>
      <div className="text-white/35 text-xs font-medium">{label}</div>
      <div className="text-2xl font-bold tabular-nums">{value}</div>
    </div>
  );
}
