import { Link } from 'react-router-dom'
import { FiInstagram, FiTwitter, FiYoutube } from 'react-icons/fi'

const Footer = () => {
  return (
    <footer className="bg-cosmic-2" style={{ borderTop: '1px solid rgba(201,168,76,0.1)', padding: '4rem 6% 2rem' }}>
      <div className="max-w-7xl mx-auto">
        <div style={{ display: 'grid', gridTemplateColumns: '2fr 1fr 1fr 1fr', gap: '3rem', marginBottom: '3rem', paddingBottom: '3rem', borderBottom: '1px solid rgba(201,168,76,0.08)' }}
          className="max-md:grid-cols-1">
          <div>
            <div className="footer-brand">AstroTalk</div>
            <p className="footer-brand-desc" style={{ maxWidth: 280 }}>
              Connect with expert astrologers for personalized predictions, guidance, and solutions to life's challenges.
            </p>
            <div style={{ display: 'flex', gap: '0.8rem', marginTop: '1.5rem' }}>
              {[FiInstagram, FiTwitter, FiYoutube].map((Icon, i) => (
                <a key={i} href="#" className="soc-btn" style={{
                  width: 36, height: 36, border: '1px solid rgba(201,168,76,0.2)',
                  display: 'flex', alignItems: 'center', justifyContent: 'center',
                  color: 'var(--muted)', transition: 'all 0.3s'
                }}>
                  <Icon size={16} />
                </a>
              ))}
            </div>
          </div>

          <div>
            <div className="footer-col-title" style={{ marginBottom: '1.2rem' }}>Explore</div>
            <ul className="footer-links list-none p-0 m-0 flex flex-col gap-3">
              <li><Link to="/astrologers">Find Astrologers</Link></li>
              <li><Link to="/">Home</Link></li>
              <li><Link to="/register">Register</Link></li>
            </ul>
          </div>

          <div>
            <div className="footer-col-title" style={{ marginBottom: '1.2rem' }}>Support</div>
            <ul className="footer-links list-none p-0 m-0 flex flex-col gap-3">
              <li><a href="#">help@astrotalk.in</a></li>
              <li><a href="#">+91-1800-123-456</a></li>
              <li><a href="#">Mumbai, India</a></li>
            </ul>
          </div>

          <div>
            <div className="footer-col-title" style={{ marginBottom: '1.2rem' }}>More</div>
            <ul className="footer-links list-none p-0 m-0 flex flex-col gap-3">
              <li><a href="#">Privacy Policy</a></li>
              <li><a href="#">Terms of Service</a></li>
              <li><a href="#">Careers</a></li>
            </ul>
          </div>
        </div>

        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '1rem' }}>
          <span style={{ fontSize: '.7rem', letterSpacing: '.15em', color: 'var(--muted)' }}>
            &copy; {new Date().getFullYear()} AstroTalk. All rights reserved.
          </span>
          <span style={{ fontSize: '.7rem', letterSpacing: '.15em', color: 'var(--muted)' }}>
            Crafted with care
          </span>
        </div>
      </div>
    </footer>
  )
}

export default Footer
