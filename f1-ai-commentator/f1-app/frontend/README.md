# F1 Live Telemetry Dashboard

A high-octane React + Vite + Tailwind CSS dashboard for live F1 race telemetry monitoring with an animated bottom text box featuring hacker-style monospace text.

## Features

✨ **Live Telemetry Display**
- Real-time speed, RPM, throttle, brake, and G-force metrics
- Dynamic driver standings with team colors
- Track status and environmental conditions
- Radio communication monitoring

🎬 **Animated Text Box**
- Slides up from the bottom of the screen
- Monospace hacker-style typing effect
- Auto-cycles through telemetry messages every 4 seconds
- Smooth slide-out animation after display duration
- Takes up 1/8th of screen height
- Features the custom speech bubble style from your reference

🎨 **Premium UI Design**
- Dark racing-inspired color scheme
- Grid background pattern
- Animated gradient overlays
- Glowing accent colors (cyan, red, blue)
- Responsive 3-column layout
- Custom scrollbars and micro-interactions

## Installation

1. **Install dependencies:**
   ```bash
   npm install
   ```

2. **Start development server:**
   ```bash
   npm run dev
   ```
   The dashboard will open at `http://localhost:3000`

3. **Build for production:**
   ```bash
   npm run build
   ```

## File Structure

```
├── index.html                    # HTML entry point
├── main.jsx                      # React entry point
├── F1TelemetryDashboard.jsx     # Main dashboard component
├── index.css                     # Tailwind imports & global styles
├── vite.config.js               # Vite configuration
├── tailwind.config.js           # Tailwind CSS configuration
├── postcss.config.js            # PostCSS configuration
└── package.json                 # Dependencies & scripts
```

## Customization

### Modify Telemetry Messages
Edit the `telemetryMessages` array in `F1TelemetryDashboard.jsx`:
```javascript
const telemetryMessages = [
  'YOUR_CUSTOM_MESSAGE • DATA • MORE_DATA',
  // Add more messages...
];
```

### Adjust Text Box Timing
- **Typing speed**: Change the `setTimeout(r, 30)` value (lower = faster)
- **Display duration**: Change the `setTimeout(r, 4000)` value (in milliseconds)
- **Animation speed**: Modify the `duration-600` class (in the transform div)

### Customize Colors
- Update the gradient colors in the driver cards
- Modify the cyan/blue/red accent colors throughout
- Edit the `tailwind.config.js` for theme-wide changes

### Adjust Text Box Height
The text box is set to approximately 1/8th of viewport height. Adjust padding/font size in the TelemetryTextBox component:
```javascript
<p className="font-mono text-sm md:text-base...">
```

## Technology Stack

- **React 18.2** - UI framework
- **Vite 5.0** - Lightning-fast build tool
- **Tailwind CSS 3.3** - Utility-first CSS
- **Courier New** - Monospace font for hacker aesthetic

## Key Components

### TelemetryTextBox
The animated text box component that:
- Types out messages character by character
- Holds text on screen for 4 seconds
- Slides out with smooth animation
- Automatically cycles through messages
- Features cursor blinking animation

### F1Dashboard
The main dashboard featuring:
- Header with race info
- 3-column layout for standings, metrics, and status
- Real-time telemetry data visualization
- Track conditions and radio communication

## Browser Support

Works on all modern browsers:
- Chrome/Edge 90+
- Firefox 88+
- Safari 14+

## Performance Notes

- CSS Grid and Flexbox for efficient layouts
- CSS-only animations for smooth 60fps
- Optimized re-renders with React hooks
- Minimal external dependencies

---

Built with precision for the ultimate F1 racing experience. 🏁
