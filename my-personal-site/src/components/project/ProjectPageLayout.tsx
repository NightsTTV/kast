import { Link } from 'react-router-dom'
import type { ReactNode } from 'react'

type ProjectPageLayoutProps = {
  title: string
  subtitle: string
  tags: string[]
  heroImage?: { src: string; alt: string }
  children: ReactNode
}

export function ProjectPageLayout({
  title,
  subtitle,
  tags,
  heroImage,
  children,
}: ProjectPageLayoutProps) {
  return (
    <article className="scroll-mt-20 pb-14 pt-4 sm:pb-20">
      <div className="mx-auto max-w-4xl px-4 sm:px-6 lg:px-8">
        <Link
          to="/#projects"
          className="inline-flex items-center gap-2 text-sm font-medium text-sky-600 transition hover:text-sky-500 dark:text-sky-400 dark:hover:text-sky-300"
        >
          <span aria-hidden="true">←</span> Back to projects
        </Link>

        <header className="mt-8">
          <div className="flex flex-wrap gap-2">
            {tags.map((tag) => (
              <span
                key={tag}
                className="rounded-full border border-sky-500/30 bg-sky-500/10 px-3 py-0.5 text-xs font-medium text-sky-700 dark:text-sky-400"
              >
                {tag}
              </span>
            ))}
          </div>
          <h1 className="mt-4 text-3xl font-bold tracking-tight text-slate-900 sm:text-4xl lg:text-5xl dark:text-white">
            {title}
          </h1>
          <p className="mt-4 text-lg leading-relaxed text-slate-600 dark:text-slate-400">
            {subtitle}
          </p>
        </header>

        {heroImage && (
          <div className="mt-10 overflow-hidden rounded-2xl border border-slate-200 shadow-lg dark:border-white/10">
            <img
              src={heroImage.src}
              alt={heroImage.alt}
              className="w-full object-cover"
            />
          </div>
        )}

        <div className="mt-12 space-y-14">{children}</div>
      </div>
    </article>
  )
}

export function ProjectSection({
  title,
  children,
}: {
  title: string
  children: ReactNode
}) {
  return (
    <section>
      <h2 className="text-2xl font-bold tracking-tight text-slate-900 dark:text-white">
        {title}
      </h2>
      <div className="mt-4 space-y-4 text-slate-600 leading-relaxed dark:text-slate-300">
        {children}
      </div>
    </section>
  )
}

export function ImageGallery({
  images,
}: {
  images: { src: string; alt: string; caption?: string }[]
}) {
  return (
    <div className="mt-6 grid gap-6 sm:grid-cols-2">
      {images.map((img) => (
        <figure
          key={img.src}
          className="overflow-hidden rounded-xl border border-slate-200 bg-slate-50 dark:border-white/10 dark:bg-slate-900/50"
        >
          <img
            src={img.src}
            alt={img.alt}
            loading="lazy"
            decoding="async"
            className="w-full object-cover"
          />
          {img.caption && (
            <figcaption className="px-4 py-3 text-sm text-slate-500 dark:text-slate-400">
              {img.caption}
            </figcaption>
          )}
        </figure>
      ))}
    </div>
  )
}

export function FeatureList({ items }: { items: string[] }) {
  return (
    <ul className="mt-4 grid gap-3 sm:grid-cols-2">
      {items.map((item) => (
        <li
          key={item}
          className="flex gap-2 rounded-lg border border-slate-200 bg-white px-4 py-3 text-sm dark:border-white/10 dark:bg-slate-900/40"
        >
          <span className="text-sky-500" aria-hidden="true">
            ✓
          </span>
          <span>{item}</span>
        </li>
      ))}
    </ul>
  )
}

export function TechTable({
  rows,
}: {
  rows: { label: string; value: string }[]
}) {
  return (
    <div className="mt-4 overflow-hidden rounded-xl border border-slate-200 dark:border-white/10">
      <table className="w-full text-left text-sm">
        <tbody>
          {rows.map((row) => (
            <tr
              key={row.label}
              className="border-b border-slate-200 last:border-0 dark:border-white/10"
            >
              <th className="bg-slate-100 px-4 py-3 font-semibold text-slate-900 dark:bg-slate-900/60 dark:text-white">
                {row.label}
              </th>
              <td className="px-4 py-3 text-slate-600 dark:text-slate-300">{row.value}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

export function DocLinks({
  links,
}: {
  links: { label: string; href: string; external?: boolean }[]
}) {
  return (
    <div className="mt-4 flex flex-wrap gap-3">
      {links.map((link) => (
        <a
          key={link.href}
          href={link.href}
          target={link.external ? '_blank' : undefined}
          rel={link.external ? 'noopener noreferrer' : undefined}
          className="inline-flex items-center gap-2 rounded-full border border-sky-500/30 bg-sky-500/10 px-5 py-2 text-sm font-semibold text-sky-700 transition hover:bg-sky-500/20 dark:text-sky-400"
        >
          {link.label}
        </a>
      ))}
    </div>
  )
}

export function SeverityScale() {
  const tiers = [
    { level: 1, name: 'Minor', duration: '1 – 7 days' },
    { level: 2, name: 'Mild', duration: '1 – 3 weeks' },
    { level: 3, name: 'Moderate', duration: '3 – 8 weeks' },
    { level: 4, name: 'Severe', duration: '2 – 6 months' },
    { level: 5, name: 'Season-Ending', duration: '6+ months' },
  ]

  return (
    <div className="mt-4 grid gap-3 sm:grid-cols-2 lg:grid-cols-3">
      {tiers.map((tier) => (
        <div
          key={tier.level}
          className="rounded-xl border border-slate-200 bg-white p-4 dark:border-white/10 dark:bg-slate-900/40"
        >
          <p className="text-xs font-semibold uppercase tracking-wide text-sky-600 dark:text-sky-400">
            Severity {tier.level}
          </p>
          <p className="mt-1 font-semibold text-slate-900 dark:text-white">{tier.name}</p>
          <p className="text-sm text-slate-500 dark:text-slate-400">{tier.duration}</p>
        </div>
      ))}
    </div>
  )
}
