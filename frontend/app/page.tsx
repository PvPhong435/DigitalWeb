import { SiteHeader } from "@/components/site-header"
import { HeroBanner } from "@/components/hero-banner"

export default function Home() {
  return (
    <div className="flex min-h-screen flex-col">
      <SiteHeader />
      <main className="flex-1">
        <HeroBanner />
      </main>
    </div>
  )
}

