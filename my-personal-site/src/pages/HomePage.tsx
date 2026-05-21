import { Link } from 'react-router-dom'
import githubImg from '../assets/github.png'
import resumeUrl from '../assets/Resume26.pdf'
import { ContactIcon } from '../components/ContactIcon'
import { ExternalLink } from '../components/ExternalLink'
import { GITHUB_URL, LINKEDIN_URL } from '../constants/links'
import { projectSummaries } from '../data/projects'

export function HomePage() {
  return (
    <>
      <section
        id="home"
        className="scroll-mt-20 mx-auto flex max-w-6xl flex-col items-center gap-12 px-4 py-10 sm:px-6 sm:py-14 lg:flex-row lg:items-center lg:justify-between lg:gap-16 lg:px-8 lg:py-20"
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
              Contact
            </a>
          </div>
        </div>

        <div className="flex shrink-0 justify-center lg:justify-end">
          <div className="relative">
            <div className="absolute -inset-1 rounded-2xl bg-gradient-to-br from-sky-500 to-violet-500 opacity-60 blur dark:opacity-75" />
            <img
              src="/me.jpg"
              alt="Kaspian Thomas"
              width={560}
              height={747}
              className="relative h-64 w-64 rounded-2xl border border-slate-200 object-cover object-top sm:h-72 sm:w-72 lg:h-80 lg:w-80 dark:border-white/10"
            />
          </div>
        </div>
      </section>

      <section
        id="projects"
        className="scroll-mt-20 border-t border-slate-200 bg-slate-100 py-14 sm:py-20 dark:border-white/10 dark:bg-slate-900/50"
      >
        <div className="mx-auto max-w-6xl px-4 sm:px-6 lg:px-8">
          <div className="text-center lg:text-left">
            <h2 className="text-3xl font-bold tracking-tight text-slate-900 sm:text-4xl dark:text-white">
              Projects
            </h2>
            <p className="mt-4 max-w-2xl text-slate-600 lg:max-w-none dark:text-slate-400">
              A portfolio of coursework and research. Click on any project for the full
              architecture details, screenshots, and documentation. See Contact for more
              information, including links to repositories and other relevant information.
            </p>
          </div>

          <div className="mt-12 grid gap-6 sm:grid-cols-2">
            {projectSummaries.map((project) => {
              const cardClass =
                'group block rounded-2xl border border-slate-200 bg-white p-6 shadow-sm transition hover:border-sky-500/40 hover:shadow-md dark:border-white/10 dark:bg-slate-950/60 dark:shadow-none dark:hover:border-sky-500/30 dark:hover:bg-slate-950'
              const content = (
                <>
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
                  <div className="mt-4 flex flex-wrap gap-2">
                    {project.tags.map((tag) => (
                      <span
                        key={tag}
                        className="rounded-md bg-slate-100 px-2 py-0.5 text-xs text-slate-600 dark:bg-slate-800 dark:text-slate-400"
                      >
                        {tag}
                      </span>
                    ))}
                  </div>
                  {project.path.startsWith('/projects') && (
                    <p className="mt-4 text-sm font-medium text-sky-600 group-hover:underline dark:text-sky-400">
                      View project details →
                    </p>
                  )}
                </>
              )

              return project.path.startsWith('/projects') ? (
                <Link key={project.slug} to={project.path} className={cardClass}>
                  {content}
                </Link>
              ) : (
                <article key={project.slug} className={`${cardClass} opacity-90`}>
                  {content}
                </article>
              )
            })}
          </div>
        </div>
      </section>

      <section id="about" className="scroll-mt-20 py-14 sm:py-20">
        <div className="mx-auto max-w-6xl px-4 sm:px-6 lg:px-8">
          <h2 className="text-3xl font-bold tracking-tight text-slate-900 sm:text-4xl dark:text-white">
            About
          </h2>
          <p className="mt-4 max-w-3xl text-lg leading-relaxed text-slate-600 dark:text-slate-400">
            Originally from Houma, Louisiana, I am rooted in the bayou, driven by curiosity,
            and always looking for the next problem worth solving.
          </p>

          <h3 className="mt-10 text-xl font-semibold tracking-tight text-slate-900 dark:text-white">
            Community &amp; Passions
          </h3>
          <p className="mt-2 max-w-3xl text-slate-600 dark:text-slate-400">
            Outside of code and research, I invest time in the people and communities around me.
          </p>

          <div className="mt-6 overflow-hidden rounded-2xl border border-slate-200 bg-slate-100 shadow-sm dark:border-white/10 dark:bg-slate-900/50 dark:shadow-none">
              <img
                src="/soccer.jpg"
                alt="Kaspian Thomas with his youth soccer team"
                width={1280}
                height={590}
                loading="lazy"
                decoding="async"
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

          <div className="mt-8 flex flex-col overflow-hidden rounded-2xl border border-slate-200 bg-slate-100 shadow-sm lg:flex-row lg:items-stretch dark:border-white/10 dark:bg-slate-900/50 dark:shadow-none">
            <div className="flex items-center justify-center bg-gradient-to-br from-slate-50 to-slate-100/80 p-6 sm:p-8 lg:w-80 lg:shrink-0 dark:from-slate-900/60 dark:to-slate-950/40">
              <div className="casper-photo-wrap group w-full max-w-[17rem] overflow-hidden rounded-2xl border border-slate-200/90 shadow-lg shadow-slate-900/10 ring-1 ring-slate-900/5 transition-shadow duration-300 hover:shadow-xl dark:border-white/15 dark:shadow-black/40 dark:ring-white/10">
                <img
                  src="/casper-face.jpg"
                  alt="Casper, a white pit mix, resting with his head on his paw"
                  width={480}
                  height={503}
                  loading="lazy"
                  decoding="async"
                  className="casper-photo aspect-[6/7] w-full object-cover object-center"
                />
              </div>
            </div>
            <div className="flex flex-col justify-center px-6 py-8 font-sans sm:px-10 sm:py-10">
              <h3 className="text-3xl font-bold tracking-tight text-sky-600 dark:text-sky-400">
                Meet Casper
              </h3>
              <p className="mt-4 text-base leading-[1.6] text-slate-600 dark:text-slate-300">
                My son Casper is a huge part of my world. This rescued white pit mix is the best
                companion a guy could ask for and is always ready for a nap.
              </p>
            </div>
          </div>
        </div>
      </section>

      <section
        id="contact"
        className="scroll-mt-20 border-t border-slate-200 bg-slate-100 py-14 sm:py-20 dark:border-white/10 dark:bg-slate-900/50"
      >
        <div className="mx-auto max-w-6xl px-4 sm:px-6 lg:px-8">
          <div className="grid items-center gap-10 lg:grid-cols-[minmax(0,16rem)_1fr] lg:gap-16">
            <div className="flex flex-col items-center gap-6 lg:items-stretch">
              <div className="relative">
                <div className="absolute -inset-1 rounded-2xl bg-gradient-to-br from-sky-500 to-violet-500 opacity-60 blur dark:opacity-75" />
                <img
                  src="/me.jpg"
                  alt="Kaspian Thomas"
                  width={560}
                  height={747}
                  loading="lazy"
                  decoding="async"
                  className="relative h-48 w-48 rounded-2xl border border-slate-200 object-cover object-top sm:h-56 sm:w-56 dark:border-white/10"
                />
              </div>

              <ExternalLink
                href={GITHUB_URL}
                aria-label="GitHub profile — NightsTTV"
                className="group flex w-full max-w-[14rem] flex-col items-center rounded-2xl border border-slate-200 bg-white px-6 py-5 shadow-sm transition hover:border-sky-500/40 hover:shadow-md hover:ring-2 hover:ring-sky-500/20 dark:border-white/10 dark:bg-slate-950/60 dark:hover:border-sky-500/30 dark:hover:ring-sky-500/30 sm:max-w-none sm:w-56"
              >
                <img
                  src={githubImg}
                  alt=""
                  className="h-14 w-14 object-contain transition group-hover:scale-105"
                />
                <span className="mt-3 text-sm font-semibold text-slate-900 transition group-hover:text-sky-600 dark:text-white dark:group-hover:text-sky-400">
                  @NightsTTV
                </span>
                <span className="mt-1 text-xs text-slate-500 dark:text-slate-400">
                  View on GitHub
                </span>
              </ExternalLink>
            </div>

            <div className="flex flex-col justify-center text-center lg:text-left">
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
                  <ExternalLink
                    href={LINKEDIN_URL}
                    className="inline-flex items-center gap-3 text-slate-600 transition hover:text-sky-600 dark:text-slate-300 dark:hover:text-sky-400"
                  >
                    <ContactIcon type="linkedin" />
                    LinkedIn — Kaspian Thomas
                  </ExternalLink>
                </li>
                <li>
                  <ExternalLink
                    href={GITHUB_URL}
                    className="inline-flex items-center gap-3 text-slate-600 transition hover:text-sky-600 dark:text-slate-300 dark:hover:text-sky-400"
                  >
                    <ContactIcon type="github" />
                    GitHub — NightsTTV
                  </ExternalLink>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </section>
    </>
  )
}
