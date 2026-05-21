import { useState } from 'react'
import resumeUrl from './assets/Resume26.pdf'
import { ThemeToggle } from './components/ThemeToggle'
import { useTheme } from './hooks/useTheme'

const navLinks = [
  { label: 'Home', href: '#home' },
  { label: 'About', href: '#about' },
  { label: 'Projects', href: '#projects' },
  { label: 'Contact', href: '#contact' },
]

const projects = [
  {
    title: 'UNO Card Game',
    description:
      'A digital implementation of the classic UNO card game. Created as a full-stack project with multiplayer support.',
    status: 'Coming soon',
  },
  {
    title: 'Multi User Chat Application',
    description:
      'Synchronized messaging platform supporting multiple concurrent users in shared rooms.',
    status: 'Coming soon',
  },
  {
    title: 'BioBERT — Premier League Injury Severity',
    description:
      'NLP analysis using BioBERT to classify and predict injury severity from Premier League soccer injury reports.',
    status: 'Coming soon',
  },
  {
    title: 'CHIP',
    description:
      'An Eliza influenced conversational chatbot exploring the patterns of matching dialogue and personality.',
    status: 'Coming soon',
  },
]

function App() {
  const [menuOpen, setMenuOpen] = useState(false)
  const { theme, toggleTheme } = useTheme()

  return (
    <div className="min-h-screen bg-slate-50 text-slate-900 transition-colors duration-300 dark:bg-slate-950 dark:text-slate-100">
      {/* Navigation */}
      <header className="fixed inset-x-0 top-0 z-50 border-b border-slate-200 bg-white/80 backdrop-blur-md dark:border-white/10 dark:bg-slate-950/80">
        <nav className="mx-auto flex max-w-6xl items-center justify-between gap-4 px-4 py-4 sm:px-6 lg:px-8">
          <a
            href="#home"
            className="text-lg font-semibold tracking-tight text-slate-900 transition hover:text-sky-600 dark:text-white dark:hover:text-sky-400"
          >
            Kaspian Thomas
          </a>

          <div className="hidden items-center gap-6 md:flex">
            <ul className="flex items-center gap-8">
              {navLinks.map((link) => (
                <li key={link.label}>
                  <a
                    href={link.href}
                    className="text-sm font-medium text-slate-600 transition hover:text-slate-900 dark:text-slate-300 dark:hover:text-white"
                  >
                    {link.label}
                  </a>
                </li>
              ))}
            </ul>
            <ThemeToggle theme={theme} onToggle={toggleTheme} />
            <a
              href="#contact"
              className="rounded-full bg-sky-500 px-5 py-2 text-sm font-medium text-white transition hover:bg-sky-400"
            >
              Get in touch
            </a>
          </div>

          <div className="flex items-center gap-2 md:hidden">
            <ThemeToggle theme={theme} onToggle={toggleTheme} />
            <button
              type="button"
              className="inline-flex items-center justify-center rounded-lg p-2 text-slate-600 transition hover:bg-slate-200 hover:text-slate-900 dark:text-slate-300 dark:hover:bg-white/10 dark:hover:text-white"
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
          </div>
        </nav>

        {menuOpen && (
          <div className="border-t border-slate-200 bg-white px-4 pb-4 dark:border-white/10 dark:bg-slate-950 md:hidden">
            <ul className="flex flex-col gap-1 pt-2">
              {navLinks.map((link) => (
                <li key={link.label}>
                  <a
                    href={link.href}
                    className="block rounded-lg px-3 py-2 text-sm font-medium text-slate-600 transition hover:bg-slate-100 hover:text-slate-900 dark:text-slate-300 dark:hover:bg-white/10 dark:hover:text-white"
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

      <main className="relative overflow-hidden pt-24">
        <div className="pointer-events-none absolute inset-0 -z-10" aria-hidden="true">
          <div className="absolute -top-40 left-1/2 h-[500px] w-[500px] -translate-x-1/2 rounded-full bg-sky-400/15 blur-3xl dark:bg-sky-500/20" />
          <div className="absolute top-1/3 right-0 h-[400px] w-[400px] rounded-full bg-violet-400/10 blur-3xl dark:bg-violet-500/10" />
        </div>

        {/* Home / Hero */}
        <section
          id="home"
          className="mx-auto flex max-w-6xl flex-col items-center gap-12 px-4 py-16 sm:px-6 sm:py-24 lg:flex-row lg:items-center lg:justify-between lg:gap-16 lg:px-8 lg:py-32"
        >
          <div className="flex-1 text-center lg:text-left">
            <p className="mb-4 inline-block rounded-full border border-sky-500/30 bg-sky-500/10 px-4 py-1 text-sm font-medium text-sky-600 dark:text-sky-400">
              Computer Science · UNO
            </p>
            <h1 className="text-4xl font-bold tracking-tight text-slate-900 sm:text-5xl lg:text-6xl dark:text-white">
              Hi, I&apos;m{' '}
              <span className="bg-gradient-to-r from-sky-500 to-violet-500 bg-clip-text text-transparent dark:from-sky-400 dark:to-violet-400">
                Kaspian Thomas
              </span>
            </h1>
            <p className="mt-6 max-w-2xl text-lg leading-relaxed text-slate-600 dark:text-slate-400">
              I am currently an undergraduate at UNO studying computer science (AI/ML).
              I am fascinated by the crossroad of evolutionary computation and artificial
              intelligence. My goal is to build genetic AI systems that operate beyond
              human constraints of time.
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
                className="w-full rounded-full border border-slate-300 px-8 py-3 text-center text-sm font-semibold text-slate-700 transition hover:border-slate-400 hover:bg-slate-100 sm:w-auto dark:border-white/20 dark:text-slate-200 dark:hover:border-white/40 dark:hover:bg-white/5"
              >
                Contact me
              </a>
            </div>
          </div>

          <div className="flex shrink-0 justify-center lg:justify-end">
            <div className="relative">
              <div className="absolute -inset-1 rounded-2xl bg-gradient-to-br from-sky-500 to-violet-500 opacity-60 blur dark:opacity-75" />
              <img
                src="/me.png"
                alt="Kaspian Thomas"
                className="relative h-64 w-64 rounded-2xl border border-slate-200 object-cover object-top sm:h-72 sm:w-72 lg:h-80 lg:w-80 dark:border-white/10"
              />
            </div>
          </div>
        </section>

        {/* Projects */}
        <section
          id="projects"
          className="border-t border-slate-200 bg-slate-100 py-20 sm:py-28 dark:border-white/10 dark:bg-slate-900/50"
        >
          <div className="mx-auto max-w-6xl px-4 sm:px-6 lg:px-8">
            <div className="text-center lg:text-left">
              <h2 className="text-3xl font-bold tracking-tight text-slate-900 sm:text-4xl dark:text-white">
                Projects
              </h2>
              <p className="mt-4 max-w-2xl text-slate-600 lg:max-w-none dark:text-slate-400">
                A glimpse into projects I&apos;m building or have built and planning to integrate into the future. Stay tuned for updates!
              </p>
            </div>

            <div className="mt-12 grid gap-6 sm:grid-cols-2">
              {projects.map((project) => (
                <article
                  key={project.title}
                  className="group rounded-2xl border border-slate-200 bg-white p-6 shadow-sm transition hover:border-sky-500/40 hover:shadow-md dark:border-white/10 dark:bg-slate-950/60 dark:shadow-none dark:hover:border-sky-500/30 dark:hover:bg-slate-950"
                >
                  <div className="flex items-start justify-between gap-4">
                    <h3 className="text-lg font-semibold text-slate-900 group-hover:text-sky-600 dark:text-white dark:group-hover:text-sky-400">
                      {project.title}
                    </h3>
                    <span className="shrink-0 rounded-full border border-sky-500/30 bg-sky-500/10 px-3 py-0.5 text-xs font-medium text-sky-600 dark:text-sky-400">
                      {project.status}
                    </span>
                  </div>
                  <p className="mt-3 text-sm leading-relaxed text-slate-600 dark:text-slate-400">
                    {project.description}
                  </p>
                </article>
              ))}
            </div>
          </div>
        </section>

        {/* About */}
        <section id="about" className="py-20 sm:py-28">
          <div className="mx-auto max-w-6xl px-4 sm:px-6 lg:px-8">
            <h2 className="text-3xl font-bold tracking-tight text-slate-900 sm:text-4xl dark:text-white">
              About
            </h2>
            <p className="mt-4 max-w-3xl text-lg leading-relaxed text-slate-600 dark:text-slate-400">
              I&apos;m from Houma, Louisiana, rooted in the bayou, driven by curiosity, and
              always looking for the next problem worth solving.
            </p>

            {/* Soccer */}
            <div className="mt-12 overflow-hidden rounded-2xl border border-slate-200 bg-slate-100 shadow-sm dark:border-white/10 dark:bg-slate-900/50 dark:shadow-none">
              <img
                src="/soccer.png"
                alt="Kaspian Thomas with his youth soccer team"
                className="aspect-video w-full object-cover"
              />
              <div className="border-t border-sky-500/20 bg-gradient-to-br from-sky-500/10 via-slate-50 to-violet-500/5 p-6 sm:p-8 dark:via-slate-900/80">
                <p className="font-['Lora',serif] text-lg italic leading-relaxed text-slate-700 sm:text-xl dark:text-slate-200">
                  <span className="float-left mr-3 mt-1 font-serif text-5xl leading-none text-sky-600 dark:text-sky-400">
                    &ldquo;
                  </span>
                  In my free time I enjoy giving back to the community and coaching youth
                  sports as a{' '}
                  <span className="font-semibold not-italic text-sky-700 dark:text-sky-300">
                    competitive soccer coach
                  </span>
                  . This is a second passion of mine and I love to see the players grow and
                  succeed.
                </p>
              </div>
            </div>

            {/* Resume */}
            <div className="mt-8 flex flex-col items-start gap-4 rounded-2xl border border-slate-200 bg-slate-100 p-6 shadow-sm sm:flex-row sm:items-center sm:justify-between sm:p-8 dark:border-white/10 dark:bg-slate-900/50 dark:shadow-none">
              <div>
                <h3 className="text-lg font-semibold text-slate-900 dark:text-white">Resume</h3>
                <p className="mt-1 text-sm text-slate-600 dark:text-slate-400">
                  Download my FULL resume now for education, experience, and skills.
                </p>
              </div>
              <a
                href={resumeUrl}
                target="_blank"
                rel="noopener noreferrer"
                className="inline-flex items-center gap-2 rounded-full bg-sky-500 px-6 py-2.5 text-sm font-semibold text-white transition hover:bg-sky-400"
              >
                <svg className="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                  <path strokeLinecap="round" strokeLinejoin="round" d="M12 10v6m0 0l-3-3m3 3l3-3M3 17V7a2 2 0 012-2h6l2 2h6a2 2 0 012 2v8a2 2 0 01-2 2H5a2 2 0 01-2-2z" />
                </svg>
                Download resume
              </a>
            </div>

            {/* Casper */}
            <div className="mt-8 flex flex-col gap-6 overflow-hidden rounded-2xl border border-slate-200 bg-slate-100 shadow-sm sm:flex-row sm:items-center dark:border-white/10 dark:bg-slate-900/50 dark:shadow-none">
              <img
                src="/casper.png"
                alt="Casper, Kaspian's dog"
                className="h-56 w-full shrink-0 object-cover object-center sm:h-auto sm:w-56"
              />
              <div className="p-6 sm:p-8">
                <h3 className="text-outline-black text-2xl font-bold tracking-tight">
                  Meet Casper
                </h3>
                <p className="mt-2 leading-relaxed text-slate-600 dark:text-slate-300">
                  My son Casper is a huge part of my world. This rescued white pit mix is the best
                  companion a guy could ask for and is always ready for a nap.
                </p>
              </div>
            </div>
          </div>
        </section>

        {/* Contact */}
        <section
          id="contact"
          className="border-t border-slate-200 bg-slate-100 py-20 sm:py-28 dark:border-white/10 dark:bg-slate-900/50"
        >
          <div className="mx-auto max-w-6xl px-4 sm:px-6 lg:px-8">
            <div className="flex flex-col items-center gap-12 lg:flex-row lg:items-start lg:gap-16">
              <div className="shrink-0">
                <div className="relative">
                  <div className="absolute -inset-1 rounded-2xl bg-gradient-to-br from-sky-500 to-violet-500 opacity-60 blur dark:opacity-75" />
                  <img
                    src="/me.png"
                    alt="Kaspian Thomas"
                    className="relative h-48 w-48 rounded-2xl border border-slate-200 object-cover object-top sm:h-56 sm:w-56 dark:border-white/10"
                  />
                </div>
              </div>

              <div className="flex-1 text-center lg:text-left">
                <h2 className="text-3xl font-bold tracking-tight text-slate-900 sm:text-4xl dark:text-white">
                  Contact
                </h2>
                <p className="mt-4 text-slate-600 dark:text-slate-400">
                  Feel free to reach out with any questions or just to say hello. I&apos;d love to connect with you!
                </p>

                <ul className="mt-8 space-y-4">
                  <li>
                    <a
                      href="mailto:klthoma8@uno.edu"
                      className="inline-flex items-center gap-3 text-slate-600 transition hover:text-sky-600 dark:text-slate-300 dark:hover:text-sky-400"
                    >
                      <ContactIcon type="email" />
                      klthoma8@uno.edu
                    </a>
                  </li>
                  <li>
                    <a
                      href="mailto:kaspian2005@gmail.com"
                      className="inline-flex items-center gap-3 text-slate-600 transition hover:text-sky-600 dark:text-slate-300 dark:hover:text-sky-400"
                    >
                      <ContactIcon type="email" />
                      kaspian2005@gmail.com
                    </a>
                  </li>
                  <li>
                    <a
                      href="tel:+19857721721"
                      className="inline-flex items-center gap-3 text-slate-600 transition hover:text-sky-600 dark:text-slate-300 dark:hover:text-sky-400"
                    >
                      <ContactIcon type="phone" />
                      985-772-1721
                    </a>
                  </li>
                  <li>
                    <a
                      href="https://www.linkedin.com/in/kaspian-thomas-294300232/"
                      target="_blank"
                      rel="noopener noreferrer"
                      className="inline-flex items-center gap-3 text-slate-600 transition hover:text-sky-600 dark:text-slate-300 dark:hover:text-sky-400"
                    >
                      <ContactIcon type="linkedin" />
                      LinkedIn — Kaspian Thomas
                    </a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </section>
      </main>

      <footer className="border-t border-slate-200 py-8 text-center text-sm text-slate-500 dark:border-white/10">
        <p>&copy; {new Date().getFullYear()} Kaspian Thomas. All rights reserved.</p>
      </footer>
    </div>
  )
}

function ContactIcon({ type }: { type: 'email' | 'phone' | 'linkedin' }) {
  const paths = {
    email: 'M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z',
    phone: 'M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z',
    linkedin: 'M16 8a6 6 0 016 6v7h-4v-7a2 2 0 00-2-2 2 2 0 00-2 2v7h-4v-7a6 6 0 016-6zM2 9h4v12H2z M4 6a2 2 0 100-4 2 2 0 000 4z',
  }

  return (
    <span className="flex h-9 w-9 items-center justify-center rounded-lg bg-sky-500/10 text-sky-600 dark:text-sky-400">
      <svg className="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
        <path strokeLinecap="round" strokeLinejoin="round" d={paths[type]} />
      </svg>
    </span>
  )
}

export default App
