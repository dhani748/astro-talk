import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useSelector, useDispatch } from 'react-redux'
import { logout } from '../../store/authSlice'
import { FiBell, FiUser, FiLogOut, FiMenu, FiX, FiMoon, FiSun, FiCreditCard, FiStar } from 'react-icons/fi'
import { useTheme } from '../../hooks/useTheme'

const Navbar = () => {
  const [mobileOpen, setMobileOpen] = useState(false)
  const [profileOpen, setProfileOpen] = useState(false)
  const { isAuthenticated, user, role } = useSelector((state) => state.auth)
  const { unreadCount } = useSelector((state) => state.notification)
  const { balance } = useSelector((state) => state.wallet)
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const { darkMode, toggleDarkMode } = useTheme()

  const handleLogout = () => {
    dispatch(logout())
    navigate('/login')
  }

  const navLinks = [
    { to: '/', label: 'Home' },
    { to: '/astrologers', label: 'Astrologers' },
  ]

  const userLinks = isAuthenticated
    ? [
        { to: '/dashboard', label: 'Dashboard' },
        { to: '/history', label: 'History' },
      ]
    : []

  const dashboardLink = role === 'astro' ? '/astrologer/dashboard'
    : role === 'admin' ? '/admin/dashboard'
    : '/dashboard'

  return (
    <nav className="sticky top-0 z-40 bg-white/80 dark:bg-gray-900/80 backdrop-blur-lg border-b border-gray-100 dark:border-gray-800">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          <Link to="/" className="flex items-center gap-2">
            <div className="w-8 h-8 bg-gradient-to-br from-primary to-gold rounded-lg flex items-center justify-center">
              <FiStar className="text-white" size={18} />
            </div>
            <span className="font-bold text-xl text-primary">AstroTalk</span>
          </Link>

          <div className="hidden md:flex items-center gap-6">
            {[...navLinks, ...userLinks].map((link) => (
              <Link
                key={link.to}
                to={link.to}
                className="text-sm font-medium text-gray-600 dark:text-gray-300 hover:text-primary dark:hover:text-white transition-colors"
              >
                {link.label}
              </Link>
            ))}
          </div>

          <div className="flex items-center gap-3">
            <button onClick={toggleDarkMode} className="p-2 hover:bg-gray-100 dark:hover:bg-gray-800 rounded-lg transition-colors">
              {darkMode ? <FiSun className="text-gray-400" size={18} /> : <FiMoon className="text-gray-400" size={18} />}
            </button>

            {isAuthenticated ? (
              <>
                {balance !== undefined && (
                  <Link to="/wallet" className="hidden md:flex items-center gap-1.5 px-3 py-1.5 bg-primary/10 text-primary rounded-lg text-sm font-medium hover:bg-primary/20 transition-colors">
                    <FiCreditCard size={14} /> ₹{balance?.toLocaleString() || '0'}
                  </Link>
                )}

                <Link to="/notifications" className="relative p-2 hover:bg-gray-100 dark:hover:bg-gray-800 rounded-lg transition-colors">
                  <FiBell className="text-gray-500" size={18} />
                  {unreadCount > 0 && (
                    <span className="absolute -top-0.5 -right-0.5 w-4 h-4 bg-red-500 text-white text-[10px] font-bold rounded-full flex items-center justify-center">
                      {unreadCount > 9 ? '9+' : unreadCount}
                    </span>
                  )}
                </Link>

                <div className="relative">
                  <button
                    onClick={() => setProfileOpen(!profileOpen)}
                    className="flex items-center gap-2 p-1.5 hover:bg-gray-100 dark:hover:bg-gray-800 rounded-xl transition-colors"
                  >
                    <img
                      src={user?.profilePhoto || `https://ui-avatars.com/api/?name=${user?.name || 'U'}&background=6B21A8&color=fff&size=32`}
                      alt="Profile"
                      className="w-8 h-8 rounded-lg object-cover"
                    />
                  </button>
                  {profileOpen && (
                    <>
                      <div className="fixed inset-0 z-10" onClick={() => setProfileOpen(false)} />
                      <div className="absolute right-0 top-full mt-2 w-56 bg-white dark:bg-gray-900 rounded-xl shadow-lg border border-gray-100 dark:border-gray-800 py-2 z-20">
                        <div className="px-4 py-2 border-b border-gray-100 dark:border-gray-800">
                          <p className="text-sm font-medium text-gray-900 dark:text-white truncate">{user?.name}</p>
                          <p className="text-xs text-gray-400">{user?.email}</p>
                        </div>
                        <Link to="/profile" onClick={() => setProfileOpen(false)} className="flex items-center gap-2 px-4 py-2 text-sm text-gray-600 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors">
                          <FiUser size={14} /> Profile
                        </Link>
                        <Link to={dashboardLink} onClick={() => setProfileOpen(false)} className="flex items-center gap-2 px-4 py-2 text-sm text-gray-600 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors">
                          <FiStar size={14} /> Dashboard
                        </Link>
                        <button onClick={handleLogout} className="flex items-center gap-2 px-4 py-2 text-sm text-red-500 hover:bg-gray-50 dark:hover:bg-gray-800 w-full transition-colors">
                          <FiLogOut size={14} /> Logout
                        </button>
                      </div>
                    </>
                  )}
                </div>
              </>
            ) : (
              <div className="flex items-center gap-2">
                <Link to="/login" className="px-4 py-2 text-sm font-medium text-gray-600 dark:text-gray-300 hover:text-primary transition-colors">
                  Login
                </Link>
                <Link to="/register" className="px-4 py-2 text-sm font-medium bg-primary text-white rounded-xl hover:bg-primary-dark transition-colors">
                  Register
                </Link>
              </div>
            )}

            <button onClick={() => setMobileOpen(!mobileOpen)} className="md:hidden p-2 hover:bg-gray-100 dark:hover:bg-gray-800 rounded-lg">
              {mobileOpen ? <FiX className="text-gray-500" size={20} /> : <FiMenu className="text-gray-500" size={20} />}
            </button>
          </div>
        </div>

        {mobileOpen && (
          <div className="md:hidden py-4 border-t border-gray-100 dark:border-gray-800 space-y-2">
            {[...navLinks, ...userLinks].map((link) => (
              <Link
                key={link.to}
                to={link.to}
                onClick={() => setMobileOpen(false)}
                className="block py-2 text-sm font-medium text-gray-600 dark:text-gray-300 hover:text-primary"
              >
                {link.label}
              </Link>
            ))}
            {isAuthenticated && (
              <button onClick={() => { handleLogout(); setMobileOpen(false) }} className="block w-full text-left py-2 text-sm font-medium text-red-500">
                Logout
              </button>
            )}
          </div>
        )}
      </div>
    </nav>
  )
}

export default Navbar
