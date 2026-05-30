# F1 Telemetry Dashboard - Complete Setup Guide

## Project Overview

This is a professional F1 live telemetry dashboard built with:
- **React 18** for component-based UI
- **Vite** for fast development and optimized builds
- **Tailwind CSS** for utility-first styling
- **Custom animations** for the sliding text box

## Key Features Implemented

### ✅ Animated Text Box
- **Location**: Bottom of screen with 1/8th viewport height
- **Animation**: Slides up from bottom, holds for 4 seconds, slides back down
- **Style**: Black background with white border, speech bubble pointer (from your reference image)
- **Font**: Monospace (Courier New) with hacker-style typing effect
- **Content**: Cycles through F1 telemetry messages automatically
- **Cursor**: Blinking cursor animation while typing

### ✅ Dashboard Layout
- **Header**: "F1 TELEMETRY" title with race info and lap counter
- **Three-Column Grid**:
  - **Left**: Driver standings (clickable, selectable)
  - **Center**: Large speed display with secondary metrics grid
  - **Right**: Track status and radio communication
- **Background**: Dark theme with animated gradient overlays and grid pattern
- **Colors**: Cyan accents, red/blue team colors, neon-style highlights

### ✅ Real-time Telemetry Data
- Speed (KPH)
- RPM
- Throttle percentage
- Brake pressure
- G-Force
- Fuel levels
- Track conditions
- Tire compounds

## Setup Instructions

### Step 1: Create Project Directory
```bash
mkdir f1-telemetry-dashboard
cd f1-telemetry-dashboard
```

### Step 2: Create File Structure
```
f1-telemetry-dashboard/
├── index.html
├── main.jsx
├── F1TelemetryDashboard.jsx
├── index.css
├── vite.config.js
├── tailwind.config.js
├── postcss.config.js
├── package.json
└── README.md
```

### Step 3: Copy All Files
Copy the following files to your project directory:
- `index.html` - Main HTML file
- `main.jsx` - React entry point
- `F1TelemetryDashboard.jsx` - Main component
- `index.css` - Global styles
- `vite.config.js` - Vite config
- `tailwind.config.js` - Tailwind config
- `postcss.config.js` - PostCSS config
- `package.json` - Dependencies

### Step 4: Install Dependencies
```bash
npm install
```

This will install:
- react@18.2.0
- react-dom@18.2.0
- vite@5.0.8
- tailwindcss@3.3.6
- autoprefixer@10.4.16
- postcss@8.4.31
- @vitejs/plugin-react@4.2.0

### Step 5: Run Development Server
```bash
npm run dev
```

The dashboard will automatically open at `http://localhost:3000`

### Step 6: Build for Production
```bash
npm run build
```

This creates an optimized build in the `dist/` directory.

## Customization Guide

### Change Telemetry Messages
Edit the `telemetryMessages` array in `F1TelemetryDashboard.jsx`:

```javascript
const telemetryMessages = [
  'LECLERC P1 • 340 KPH • DRS ACTIVE • TIRE TEMP +2°C • FUEL: 65L • ENGINE: OPTIMAL',
  'VERSTAPPEN P2 • 338 KPH • DRS READY • FOLLOWING AT 1.2S • BRAKE TEMP: 650°C',
  // Add your messages here
];
```

### Adjust Text Box Timing

**Typing Speed** (in main TelemetryTextBox component):
```javascript
await new Promise(r => setTimeout(r, 30)); // Lower = faster typing
```

**Display Duration** (how long text stays visible):
```javascript
await new Promise(r => setTimeout(r, 4000)); // 4000ms = 4 seconds
```

**Slide Animation Speed**:
```javascript
<div className={`... duration-600 ...`}> {/* 600ms = animation duration */}
```

### Modify Colors

**Driver Team Colors**:
```javascript
const drivers = [
  { name: 'Charles Leclerc', team: 'Ferrari', position: 1, color: 'from-red-600 to-red-800' },
  // Edit color property for each driver
];
```

**Accent Colors** (Edit in className):
- `text-cyan-400` - Cyan text
- `border-cyan-400` - Cyan borders
- `bg-red-600`, `bg-blue-600` - Background gradient overlays

### Adjust Text Box Height
Current height is ~1/8 of viewport. To change:

```javascript
<p className="font-mono text-sm md:text-base ..."> 
  {/* Adjust text-sm, text-base to text-xs, text-lg for different heights */}
  {displayText}<span className="animate-pulse">▮</span>
</p>
```

Also adjust padding:
```javascript
<div className="bg-black border-2 border-white rounded-3xl px-8 py-6 ...">
  {/* Adjust px-8 (padding-x) and py-6 (padding-y) */}
```

## Advanced Customization

### Connect Real Telemetry Data
Replace the static `telemetryMessages` array with real data from an API:

```javascript
useEffect(() => {
  const fetchTelemetry = async () => {
    const response = await fetch('/api/telemetry');
    const data = await response.json();
    setDisplayText(formatTelemetry(data));
  };
  
  const interval = setInterval(fetchTelemetry, 1000);
  return () => clearInterval(interval);
}, []);
```

### Update Driver Data Dynamically
Connect to a race API to update driver standings in real-time:

```javascript
useEffect(() => {
  const fetchDrivers = async () => {
    const response = await fetch('/api/drivers/standings');
    const drivers = await response.json();
    // Update state with real drivers
  };
}, []);
```

### Customize Speech Bubble Pointer
Adjust the pointer position and size:

```javascript
<div className="absolute -bottom-6 left-12 ..."> {/* Adjust left-12 for position */}
  {/* Adjust border sizes for pointer shape */}
</div>
```

## Browser Compatibility

✅ Chrome/Chromium 90+
✅ Firefox 88+
✅ Safari 14+
✅ Edge 90+

## Performance Optimization

The dashboard is optimized for:
- **60fps animations** using CSS transforms
- **Smooth typing effect** with character-by-character rendering
- **Efficient re-renders** with React hooks
- **Light DOM** with minimal JavaScript

## Troubleshooting

**Text box not appearing?**
- Check that `setIsVisible` is being called
- Verify Tailwind classes are loading (check browser console)

**Typing effect too fast/slow?**
- Adjust the `setTimeout(r, 30)` value in the typing loop

**Animations not smooth?**
- Ensure hardware acceleration is enabled in your browser
- Check for CSS conflicts with browser extensions

**Build errors?**
- Delete `node_modules/` and `package-lock.json`
- Run `npm install` again
- Clear Vite cache: `rm -rf .vite`

## Project Statistics

- **Lines of Code**: ~400 (component)
- **CSS Classes Used**: 150+
- **Animation States**: 3 (hidden, visible, typing)
- **Component Count**: 2 (TelemetryTextBox, F1Dashboard)
- **Dependencies**: 2 (React, React-DOM)

## Next Steps

1. Connect to real F1 telemetry API (Formula1.com, ESPN API, etc.)
2. Add WebSocket for live updates
3. Implement driver camera view integration
4. Add pit stop strategy visualization
5. Create lap time comparison charts
6. Add weather radar overlay
7. Implement DRS/ERS boost indicators
8. Add multi-driver comparison view

---

**Built with precision for the ultimate F1 racing experience. 🏁**

Questions? Check the README.md or customize as needed!
