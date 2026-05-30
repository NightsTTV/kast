module.exports = {
  content: [
    "./index.html",
    "./*.{js,jsx}",
    "./src/**/*.{js,jsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: '#0F172A',
        secondary: '#1E293B',
        cta: '#22C55E',
        background: '#020617',
        text: '#F8FAFC',
        accent: '#e10600', // F1 Red
        'card-bg': '#111119',
        'nested-bg': '#181824',
      },
      fontFamily: {
        sans: ['"Fira Sans"', '-apple-system', 'BlinkMacSystemFont', '"Segoe UI"', 'sans-serif'],
        mono: ['"Fira Code"', '"Courier New"', 'monospace'],
      },
    },
  },
  plugins: [],
}
