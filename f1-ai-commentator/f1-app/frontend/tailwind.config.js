module.exports = {
  content: [
    "./index.html",
    "./*.{js,jsx}",
    "./src/**/*.{js,jsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        sans: ['"Outfit"', '-apple-system', 'BlinkMacSystemFont', '"Segoe UI"', 'sans-serif'],
        mono: ['"Courier New"', '"Courier"', 'monospace'],
      },
    },
  },
  plugins: [],
}
