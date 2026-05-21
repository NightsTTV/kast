import type { AnchorHTMLAttributes, ReactNode } from 'react'
import { externalLinkProps } from '../constants/links'

type ExternalLinkProps = AnchorHTMLAttributes<HTMLAnchorElement> & {
  href: string
  children: ReactNode
}

/** Opens https external URLs in a new tab with safe rel attributes */
export function ExternalLink({ href, children, ...rest }: ExternalLinkProps) {
  const safeHref = href.startsWith('http') ? href : `https://${href.replace(/^\/+/, '')}`

  return (
    <a href={safeHref} {...externalLinkProps} {...rest}>
      {children}
    </a>
  )
}
