import { useState, useEffect } from 'react'
import { motion, AnimatePresence } from 'framer-motion'
import { Link } from 'react-router-dom'
import { useSelector } from 'react-redux'

const navLinks = [
  { label: 'Services', href: '#services' },
  { label: 'Astrologers', href: '#astrologers' },
  { label: 'How It Works', href: '#how-it-works' },
  { label: 'Testimonials', href: '#testimonials' },
  { label: 'Contact', href: '#contact' },
]

const containerVariants = {
  hidden: { y: -80, opacity: 0 },
  visible: {
    y: 0,
    opacity: 1,
    transition: {
      duration: 0.6,
      ease: 'easeOut',
      staggerChildren: 0.08,
    },
  },
}

const itemVariants = {
  hidden: { y: -20, opacity: 0 },
  visible: {
    y: 0,
    opacity: 1,
    transition: { duration: 0.4, ease: 'easeOut' },
  },
}

const overlayVariants = {
  hidden: { opacity: 0 },
  visible: { opacity: 1, transition: { duration: 0.3 } },
  exit: { opacity: 0, transition: { duration: 0.3 } },
}

const mobileLinkVariants = {
  hidden: { x: -40, opacity: 0 },
  visible: (i) => ({
    x: 0,
    opacity: 1,
    transition: { delay: i * 0.07, duration: 0.35, ease: 'easeOut' },
  }),
}

export default function Navbar() {
  const [scrolledDown, setScrolledDown] = useState(false)
  const [menuOpen, setMenuOpen] = useState(false)
  const { isAuthenticated, user, role } = useSelector((state) => state.auth)
  const dashboardLink = role === 'ASTROLOGER' ? '/astrologer/dashboard'
    : role === 'ADMIN' ? '/admin/dashboard'
    : '/dashboard'
  useEffect(() => {
    let prev = window.scrollY

    const handleScroll = () => {
      const curr = window.scrollY
      if (curr > prev && curr > 80) {
        setScrolledDown(true)
      } else {
        setScrolledDown(false)
      }
      prev = curr
    }

    window.addEventListener('scroll', handleScroll, { passive: true })
    return () => window.removeEventListener('scroll', handleScroll)
  }, [])

  useEffect(() => {
    if (menuOpen) {
      document.body.style.overflow = 'hidden'
    } else {
      document.body.style.overflow = ''
    }
    return () => {
      document.body.style.overflow = ''
    }
  }, [menuOpen])

  const handleLinkClick = () => {
    setMenuOpen(false)
  }

  return (
    <AnimatePresence>
      {!scrolledDown && (
        <motion.header
          key="navbar"
          variants={containerVariants}
          initial="hidden"
          animate="visible"
          exit={{ y: -80, opacity: 0, transition: { duration: 0.3 } }}
          className="fixed top-0 left-0 right-0 z-50 bg-white/5 backdrop-blur-xl border-b border-white/10 font-['Jost']"
        >
          <nav className="mx-auto flex max-w-7xl items-center justify-between px-6 py-4 lg:px-10">
            {/* Logo */}
            <motion.a
              variants={itemVariants}
              href="#"
              className="font-['Playfair_Display'] text-2xl font-bold tracking-wide text-gold"
            >
              AstroTalk
            </motion.a>

            {/* Center Links - Desktop */}
            <motion.ul
              variants={containerVariants}
              className="hidden items-center gap-9 md:flex"
            >
              {navLinks.map((link) => (
                <motion.li key={link.label} variants={itemVariants}>
                  <a
                    href={link.href}
                    className="group relative text-sm font-medium tracking-wide text-white/70 transition-colors duration-300 hover:text-white"
                  >
                    {link.label}
                    <span className="absolute -bottom-1 left-0 h-[2px] w-0 bg-gold transition-all duration-300 group-hover:w-full" />
                  </a>
                </motion.li>
              ))}
            </motion.ul>

            {/* Right - Desktop */}
            <motion.div variants={itemVariants} className="hidden md:flex items-center gap-4">
              {isAuthenticated && user ? (
                <div className="flex items-center gap-3">
                  <span className="text-sm text-white/70">{user?.name}</span>
                  <Link
                    to={dashboardLink}
                    className="rounded-full bg-white/10 px-4 py-2 text-sm font-medium text-white transition-all hover:bg-white/20"
                  >
                    Dashboard
                  </Link>
                </div>
              ) : (
                <Link
                  to="/login"
                  className="rounded-full bg-white/10 px-4 py-2 text-sm font-medium text-white transition-all hover:bg-white/20"
                >
                  Log In
                </Link>
              )}
              <Link
                to="/astrologers"
                className="inline-block rounded-full bg-gradient-to-r from-amber-400 to-yellow-600 px-6 py-2.5 text-sm font-semibold text-white shadow-lg shadow-amber-500/30 transition-all duration-300 hover:shadow-xl hover:shadow-amber-500/40 hover:scale-105"
              >
                Start Free Consultation
              </Link>
            </motion.div>

            {/* Hamburger - Mobile */}
            <motion.button
              variants={itemVariants}
              onClick={() => setMenuOpen(true)}
              className="relative z-50 flex flex-col items-end gap-1.5 md:hidden"
              aria-label="Open menu"
            >
              <span className="block h-[2px] w-7 rounded bg-white transition-all duration-300" />
              <span className="block h-[2px] w-5 rounded bg-white transition-all duration-300" />
              <span className="block h-[2px] w-7 rounded bg-white transition-all duration-300" />
            </motion.button>
          </nav>
        </motion.header>
      )}

      {/* Mobile Full-Screen Overlay */}
      <AnimatePresence>
        {menuOpen && (
          <motion.div
            key="mobile-overlay"
            variants={overlayVariants}
            initial="hidden"
            animate="visible"
            exit="exit"
            className="fixed inset-0 z-40 flex flex-col items-center justify-center bg-black/60 backdrop-blur-2xl md:hidden"
          >
            {/* Close Button */}
            <button
              onClick={() => setMenuOpen(false)}
              className="absolute top-6 right-6 z-50 flex flex-col items-end gap-1.5"
              aria-label="Close menu"
            >
              <span className="block h-[2px] w-7 origin-center translate-y-2 rotate-45 rounded bg-white transition-all duration-300" />
              <span className="block h-[2px] w-7 origin-center -rotate-45 rounded bg-white transition-all duration-300" />
            </button>

            {/* Mobile Nav Links */}
            <ul className="flex flex-col items-center gap-8">
              {navLinks.map((link, i) => (
                <motion.li
                  key={link.label}
                  custom={i}
                  variants={mobileLinkVariants}
                  initial="hidden"
                  animate="visible"
                >
                  <a
                    href={link.href}
                    onClick={handleLinkClick}
                    className="font-['Playfair_Display'] text-3xl font-semibold tracking-wide text-white/80 transition-colors duration-300 hover:text-gold"
                  >
                    {link.label}
                  </a>
                </motion.li>
              ))}
            </ul>

            {/* Mobile Auth */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{
                opacity: 1,
                y: 0,
                transition: { delay: 0.4, duration: 0.4 },
              }}
              className="mt-8 flex items-center gap-4"
            >
              {isAuthenticated && user ? (
                <Link
                  to={dashboardLink}
                  onClick={handleLinkClick}
                  className="rounded-full bg-white/10 px-6 py-3 text-base font-medium text-white transition-all hover:bg-white/20"
                >
                  Dashboard
                </Link>
              ) : (
                <Link
                  to="/login"
                  onClick={handleLinkClick}
                  className="rounded-full bg-white/10 px-6 py-3 text-base font-medium text-white transition-all hover:bg-white/20"
                >
                  Log In
                </Link>
              )}
            </motion.div>

            {/* Mobile CTA */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{
                opacity: 1,
                y: 0,
                transition: { delay: 0.5, duration: 0.4 },
              }}
              className="mt-6"
            >
              <Link
                to="/astrologers"
                onClick={handleLinkClick}
                className="inline-block rounded-full bg-gradient-to-r from-amber-400 to-yellow-600 px-8 py-3.5 text-base font-semibold text-white shadow-lg shadow-amber-500/30 transition-all duration-300 hover:shadow-xl hover:shadow-amber-500/40 hover:scale-105"
              >
                Start Free Consultation
              </Link>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
    </AnimatePresence>
  )
}
