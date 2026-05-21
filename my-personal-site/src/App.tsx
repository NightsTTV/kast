import { useState } from 'react'

const navLinks = [
  { label: 'Home', href: '#' },
  { label: 'About', href: '#about' },
  { label: 'Projects', href: '#projects' },
  { label: 'Contact', href: '#contact' },
]

function App() {
  const [menuOpen, setMenuOpen] = useState(false)

  return (
    <div className="min-h-screen bg-slate-950 text-slate-100">
      {/* Navigation */}
      <header className="fixed inset-x-0 top-0 z-50 border-b border-white/10 bg-slate-950/80 backdrop-blur-md">
        <nav className="mx-auto flex max-w-6xl items-center justify-between px-4 py-4 sm:px-6 lg:px-8">
          <a
            href="#"
            className="text-lg font-semibold tracking-tight text-white transition hover:text-sky-400"
          >
            Your Name
          </a>

          {/* Desktop nav */}
          <ul className="hidden items-center gap-8 md:flex">
            {navLinks.map((link) => (
              <li key={link.label}>
                <a
                  href={link.href}
                  className="text-sm font-medium text-slate-300 transition hover:text-white"
                >
                  {link.label}
                </a>
              </li>
            ))}
          </ul>

          <a
            href="#contact"
            className="hidden rounded-full bg-sky-500 px-5 py-2 text-sm font-medium text-white transition hover:bg-sky-400 md:inline-block"
          >
            Get in touch
          </a>

          {/* Mobile menu button */}
          <button
            type="button"
            className="inline-flex items-center justify-center rounded-lg p-2 text-slate-300 transition hover:bg-white/10 hover:text-white md:hidden"
            aria-expanded={menuOpen}
            aria-label="Toggle navigation menu"
            onClick={() => setMenuOpen((open) => !open)}
          >
            {menuOpen ? (
              <svg className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12" />
              </svg>
            ) : (
              <svg className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                <path strokeLinecap="round" strokeLinejoin="round" d="M4 6h16M4 12h16M4 18h16" />
              </svg>
            )}
          </button>
        </nav>

        {/* Mobile nav panel */}
        {menuOpen && (
          <div className="border-t border-white/10 bg-slate-950 px-4 pb-4 md:hidden">
            <ul className="flex flex-col gap-1 pt-2">
              {navLinks.map((link) => (
                <li key={link.label}>
                  <a
                    href={link.href}
                    className="block rounded-lg px-3 py-2 text-sm font-medium text-slate-300 transition hover:bg-white/10 hover:text-white"
                    onClick={() => setMenuOpen(false)}
                  >
                    {link.label}
                  </a>
                </li>
              ))}
            </ul>
            <a
              href="#contact"
              className="mt-3 block rounded-full bg-sky-500 px-5 py-2.5 text-center text-sm font-medium text-white transition hover:bg-sky-400"
              onClick={() => setMenuOpen(false)}
            >
              Get in touch
            </a>
          </div>
        )}
      </header>

      {/* Hero */}
      <main className="relative overflow-hidden pt-24">
        <div
          className="pointer-events-none absolute inset-0 -z-10"
          aria-hidden="true"
        >
          <div className="absolute -top-40 left-1/2 h-[500px] w-[500px] -translate-x-1/2 rounded-full bg-sky-500/20 blur-3xl" />
          <div className="absolute top-1/3 right-0 h-[400px] w-[400px] rounded-full bg-violet-500/10 blur-3xl" />
        </div>

        <section className="mx-auto flex max-w-6xl flex-col items-center gap-12 px-4 py-16 sm:px-6 sm:py-24 lg:flex-row lg:items-center lg:justify-between lg:gap-16 lg:px-8 lg:py-32">
          <div className="flex-1 text-center lg:text-left">
            <p className="mb-4 inline-block rounded-full border border-sky-500/30 bg-sky-500/10 px-4 py-1 text-sm font-medium text-sky-400">
              Welcome to my site
            </p>
            <h1 className="text-4xl font-bold tracking-tight text-white sm:text-5xl lg:text-6xl">
              Hi, I&apos;m{' '}
              <span className="bg-gradient-to-r from-sky-400 to-violet-400 bg-clip-text text-transparent">
                Your Name
              </span>
            </h1>
            <p className="mt-6 max-w-xl text-lg leading-relaxed text-slate-400 sm:text-xl">
              Developer, designer, and builder of things on the web. I craft clean,
              thoughtful experiences from idea to launch.
            </p>
            <div className="mt-10 flex flex-col items-center gap-4 sm:flex-row sm:justify-center lg:justify-start">
              <a
                href="#projects"
                className="w-full rounded-full bg-sky-500 px-8 py-3 text-center text-sm font-semibold text-white transition hover:bg-sky-400 sm:w-auto"
              >
                View my work
              </a>
              <a
                href="#contact"
                className="w-full rounded-full border border-white/20 px-8 py-3 text-center text-sm font-semibold text-slate-200 transition hover:border-white/40 hover:bg-white/5 sm:w-auto"
              >
                Contact me
              </a>
            </div>
          </div>

          {/* Profile placeholder */}
          <div className="flex shrink-0 justify-center lg:justify-end">
            <div className="relative">
              <div className="absolute -inset-1 rounded-2xl bg-gradient-to-br from-sky-500 to-violet-500 opacity-75 blur" />
              <div className="relative flex h-64 w-64 items-center justify-center overflow-hidden rounded-2xl border border-white/10 bg-slate-800 sm:h-72 sm:w-72 lg:h-80 lg:w-80">
                <svg
                  className="h-24 w-24 text-slate-600"
                  fill="currentColor"
                  viewBox="0 0 24 24"
                  aria-hidden="true"
                >
                  <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z" />
                </svg>
                <span className="sr-only">Profile picture placeholder</span>
              </div>
            </div>
          </div>
        </section>
      </main>
    </div>
  )
}

export default App
