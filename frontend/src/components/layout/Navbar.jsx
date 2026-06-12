import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useSelector, useDispatch } from 'react-redux'
import { logout } from '../../store/authSlice'
import { FiMenu, FiX, FiChevronDown } from 'react-icons/fi'
import { useLanguage } from '../../context/LanguageContext'
import LoginModal from './LoginModal'

const languages = [
  { code: 'en', label: 'English' },
  { code: 'hi', label: 'हिन्दी' },
]

const navLinks = [
  { label: 'Consultations', href: '/astrologers' },
  { label: 'Horoscope', href: '#' },
  { label: 'Free Services', href: '#' },
  { label: 'Calculators', href: '#' },
  { label: 'Panchang', href: '#' },
  { label: 'Shop', href: '#' },
  { label: 'Blog', href: '#' },
]

export default function Navbar() {
  const [mobileOpen, setMobileOpen] = useState(false)
  const [langOpen, setLangOpen] = useState(false)
  const [loginOpen, setLoginOpen] = useState(false)
  const { isAuthenticated, user, role } = useSelector((state) => state.auth)
  const { lang, t, switchLang } = useLanguage()
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const dashboardLink = role === 'ASTROLOGER' ? '/astrologer/dashboard'
    : role === 'ADMIN' ? '/admin/dashboard'
    : '/dashboard'

  const currentLang = languages.find((l) => l.code === lang) || languages[0]

  return (
    <>
      <nav className="site-nav sticky top-0 z-40">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 flex items-center justify-between h-[70px]">
          <Link to="/" className="nav-brand" style={{ fontFamily: 'var(--font-display)', fontSize: '1.4rem', color: 'var(--gold)', letterSpacing: '0.12em' }}>
            AstroTalk
          </Link>

          <ul className="hidden lg:flex items-center gap-10 list-none m-0 p-0">
            {navLinks.map((link) => (
              <li key={link.label}>
                <Link
                  to={link.href}
                  style={{ fontSize: '.7rem', letterSpacing: '.25em', textTransform: 'uppercase', color: 'var(--muted)' }}
                  className="hover:text-[var(--gold)] transition-colors"
                >
                  {t.nav?.[link.label.toLowerCase().replace(/\s+/g, '')] || link.label}
                </Link>
              </li>
            ))}
          </ul>

          <div className="flex items-center gap-4">
            <div className="relative hidden sm:block">
              <button
                onClick={() => setLangOpen(!langOpen)}
                className="flex items-center gap-1.5 px-3 py-1.5 text-xs font-medium tracking-[.2em] uppercase text-[var(--muted)] border border-[rgba(201,168,76,0.2)] rounded hover:border-[rgba(201,168,76,0.4)] transition-colors"
              >
                {currentLang.label} <FiChevronDown size={12} />
              </button>
              {langOpen && (
                <>
                  <div className="fixed inset-0 z-10" onClick={() => setLangOpen(false)} />
                  <div className="absolute right-0 top-full mt-1 bg-[var(--dark2)] border border-[rgba(201,168,76,0.15)] rounded py-1 z-20 min-w-[130px] shadow-xl">
                    {languages.map((l) => (
                      <button
                        key={l.code}
                        onClick={() => { switchLang(l.code); setLangOpen(false) }}
                        className={`block w-full text-left px-4 py-2 text-sm transition-colors ${
                          lang === l.code ? 'text-[var(--gold)] bg-[rgba(201,168,76,0.1)]' : 'text-[var(--muted)] hover:bg-[rgba(255,255,255,0.03)]'
                        }`}
                      >
                        {l.label}
                      </button>
                    ))}
                  </div>
                </>
              )}
            </div>

            {isAuthenticated ? (
              <Link to={dashboardLink} className="btn-gold !px-5 !py-2.5 !text-[.65rem]">
                {t.nav?.dashboard || 'Dashboard'}
              </Link>
            ) : (
              <button onClick={() => setLoginOpen(true)} className="btn-gold !px-5 !py-2.5 !text-[.65rem]">
                {t.nav?.signIn || 'Sign In'}
              </button>
            )}

            <button onClick={() => setMobileOpen(!mobileOpen)} className="lg:hidden p-2 text-[var(--muted)] hover:text-[var(--gold)] transition-colors">
              {mobileOpen ? <FiX size={20} /> : <FiMenu size={20} />}
            </button>
          </div>
        </div>

        {mobileOpen && (
          <div className="lg:hidden border-t border-[rgba(201,168,76,0.1)] bg-[var(--dark)] px-4 py-4 space-y-1">
            {navLinks.map((link) => (
              <Link
                key={link.label}
                to={link.href}
                onClick={() => setMobileOpen(false)}
                style={{ fontSize: '.7rem', letterSpacing: '.25em', textTransform: 'uppercase', color: 'var(--muted)' }}
                className="block px-3 py-2.5 hover:text-[var(--gold)] transition-colors"
              >
                {t.nav?.[link.label.toLowerCase().replace(/\s+/g, '')] || link.label}
              </Link>
            ))}
            <div className="border-t border-[rgba(201,168,76,0.1)] pt-3 mt-3 flex gap-2 px-3">
              {languages.map((l) => (
                <button
                  key={l.code}
                  onClick={() => { switchLang(l.code); setMobileOpen(false) }}
                  className={`px-3 py-1.5 text-xs font-medium tracking-[.2em] uppercase rounded transition-colors ${
                    lang === l.code ? 'text-[var(--gold)] bg-[rgba(201,168,76,0.1)]' : 'text-[var(--muted)]'
                  }`}
                >
                  {l.label}
                </button>
              ))}
            </div>
          </div>
        )}
      </nav>

      <LoginModal open={loginOpen} onClose={() => setLoginOpen(false)} />
    </>
  )
}
