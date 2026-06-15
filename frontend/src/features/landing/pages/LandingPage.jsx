import { useEffect, lazy, Suspense } from 'react'
import { motion } from 'framer-motion'
import Lenis from 'lenis'

const Navbar = lazy(() => import('../components/Navbar'))
const HeroSection = lazy(() => import('../components/HeroSection'))
const FeaturesSection = lazy(() => import('../components/FeaturesSection'))
const HowItWorks = lazy(() => import('../components/HowItWorks'))
const Testimonials = lazy(() => import('../components/Testimonials'))
const TrustStats = lazy(() => import('../components/TrustStats'))
const MobileAppSection = lazy(() => import('../components/MobileAppSection'))
const Footer = lazy(() => import('../components/Footer'))

const SectionFallback = () => (
  <div className="flex h-64 items-center justify-center">
    <div className="h-8 w-8 animate-spin rounded-full border-2 border-gold/30 border-t-gold" />
  </div>
)

export default function LandingPage() {
  useEffect(() => {
    const lenis = new Lenis({ duration: 1.2, easing: (t) => Math.min(1, 1.001 - Math.pow(2, -10 * t)), orientation: 'vertical', smoothWheel: true })

    function raf(time) {
      lenis.raf(time)
      requestAnimationFrame(raf)
    }

    requestAnimationFrame(raf)
    return () => lenis.destroy()
  }, [])

  return (
    <motion.div
      className="min-h-screen bg-cosmic"
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      transition={{ duration: 0.5 }}
    >
      <Suspense fallback={<SectionFallback />}>
        <Navbar />
      </Suspense>
      <Suspense fallback={<SectionFallback />}>
        <HeroSection />
      </Suspense>
      <Suspense fallback={<SectionFallback />}>
        <FeaturesSection />
      </Suspense>
      <Suspense fallback={<SectionFallback />}>
        <HowItWorks />
      </Suspense>
      <Suspense fallback={<SectionFallback />}>
        <Testimonials />
      </Suspense>
      <Suspense fallback={<SectionFallback />}>
        <TrustStats />
      </Suspense>
      <Suspense fallback={<SectionFallback />}>
        <MobileAppSection />
      </Suspense>
      <Suspense fallback={<SectionFallback />}>
        <Footer />
      </Suspense>
    </motion.div>
  )
}
