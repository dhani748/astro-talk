import { motion } from 'framer-motion'
import { FiInstagram, FiTwitter, FiYoutube, FiLinkedin, FiMail, FiPhone, FiMapPin } from 'react-icons/fi'

const containerVariants = {
  hidden: { opacity: 0 },
  visible: { opacity: 1, transition: { staggerChildren: 0.1, delayChildren: 0.1 } },
}

const itemVariants = {
  hidden: { y: 20, opacity: 0 },
  visible: { y: 0, opacity: 1, transition: { duration: 0.5, ease: 'easeOut' } },
}

const quickLinks = ['Home', 'Find Astrologers', 'Services', 'Tarot Reading', 'Numerology', 'Blog']
const supportLinks = ['Contact Us', 'FAQ', 'Privacy Policy', 'Terms of Service', 'Refund Policy']

export default function Footer() {
  return (
    <footer id="contact" className="relative border-t border-white/5 bg-[#07070d] pt-20 pb-8">
      <div className="pointer-events-none absolute top-0 left-1/2 h-px w-1/3 -translate-x-1/2 bg-gradient-to-r from-transparent via-gold/30 to-transparent" />

      <motion.div
        variants={containerVariants}
        initial="hidden"
        whileInView="visible"
        viewport={{ once: true }}
        className="mx-auto max-w-7xl px-6 lg:px-10"
      >
        <div className="grid gap-12 sm:grid-cols-2 lg:grid-cols-4">
          {/* Brand */}
          <motion.div variants={itemVariants} className="sm:col-span-2 lg:col-span-1">
            <a href="#" className="font-display text-2xl font-bold tracking-wide text-gold">
              AstroTalk
            </a>
            <p className="mt-4 max-w-xs text-sm leading-relaxed text-white/40">
              Your gateway to cosmic guidance. Connect with verified astrologers for personalized predictions and life solutions.
            </p>
            <div className="mt-6 flex gap-3">
              {[FiInstagram, FiTwitter, FiYoutube, FiLinkedin].map((Icon, i) => (
                <a
                  key={i}
                  href="#"
                  className="flex h-9 w-9 items-center justify-center rounded-full border border-white/10 text-white/40 transition-all duration-300 hover:border-gold/40 hover:text-gold hover:bg-gold/10"
                >
                  <Icon size={15} />
                </a>
              ))}
            </div>
            <div className="mt-6 flex gap-2">
              <input
                type="email"
                placeholder="Your email"
                className="flex-1 rounded-full border border-white/10 bg-white/5 px-4 py-2 text-xs text-white placeholder-white/30 outline-none transition-colors focus:border-gold/40"
              />
              <button className="rounded-full bg-gold px-4 py-2 text-xs font-semibold text-cosmic transition-all duration-300 hover:bg-gold-light">
                Subscribe
              </button>
            </div>
          </motion.div>

          {/* Quick Links */}
          <motion.div variants={itemVariants}>
            <h4 className="mb-5 text-xs font-semibold uppercase tracking-[0.2em] text-gold">Quick Links</h4>
            <ul className="flex flex-col gap-3">
              {quickLinks.map((link) => (
                <li key={link}>
                  <a href="#" className="text-sm text-white/40 transition-colors duration-300 hover:text-white">{link}</a>
                </li>
              ))}
            </ul>
          </motion.div>

          {/* Support */}
          <motion.div variants={itemVariants}>
            <h4 className="mb-5 text-xs font-semibold uppercase tracking-[0.2em] text-gold">Support</h4>
            <ul className="flex flex-col gap-3">
              {supportLinks.map((link) => (
                <li key={link}>
                  <a href="#" className="text-sm text-white/40 transition-colors duration-300 hover:text-white">{link}</a>
                </li>
              ))}
            </ul>
          </motion.div>

          {/* Contact */}
          <motion.div variants={itemVariants}>
            <h4 className="mb-5 text-xs font-semibold uppercase tracking-[0.2em] text-gold">Contact</h4>
            <ul className="flex flex-col gap-4">
              <li className="flex items-start gap-3 text-sm text-white/40">
                <FiMail className="mt-0.5 text-gold" size={14} />
                <span>support@astrotalk.in</span>
              </li>
              <li className="flex items-start gap-3 text-sm text-white/40">
                <FiPhone className="mt-0.5 text-gold" size={14} />
                <span>+91-1800-123-456</span>
              </li>
              <li className="flex items-start gap-3 text-sm text-white/40">
                <FiMapPin className="mt-0.5 text-gold" size={14} />
                <span>Mumbai, Maharashtra, India</span>
              </li>
              <li>
                <span className="inline-block rounded-full border border-gold/20 bg-gold/5 px-3 py-1 text-[10px] font-semibold uppercase tracking-wider text-gold">
                  Available 24/7
                </span>
              </li>
            </ul>
          </motion.div>
        </div>

        {/* Bottom bar */}
        <div className="mt-16 flex flex-col items-center justify-between gap-4 border-t border-white/5 pt-8 sm:flex-row">
          <span className="text-xs text-white/30">
            &copy; {new Date().getFullYear()} AstroTalk. All rights reserved.
          </span>
          <span className="text-xs text-white/30">
            Made with ✨ for the cosmos
          </span>
          <div className="flex gap-3">
            {['Visa', 'MC', 'UPI', 'Net'].map((p) => (
              <span key={p} className="rounded-md border border-white/10 px-2.5 py-1 text-[10px] font-medium text-white/30">
                {p}
              </span>
            ))}
          </div>
        </div>
      </motion.div>
    </footer>
  )
}
