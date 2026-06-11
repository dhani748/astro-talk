import { Link } from 'react-router-dom'
import { FiStar, FiMail, FiPhone, FiMapPin, FiInstagram, FiTwitter, FiYoutube } from 'react-icons/fi'

const Footer = () => {
  return (
    <footer className="bg-gray-900 dark:bg-gray-950 text-gray-300">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div className="col-span-1 md:col-span-2">
            <div className="flex items-center gap-2 mb-4">
              <div className="w-8 h-8 bg-gradient-to-br from-primary to-gold rounded-lg flex items-center justify-center">
                <FiStar className="text-white" size={18} />
              </div>
              <span className="font-bold text-xl text-white">AstroTalk</span>
            </div>
            <p className="text-sm text-gray-400 max-w-md leading-relaxed">
              Connect with expert astrologers for personalized predictions, guidance, 
              and solutions to life's challenges. Available 24/7.
            </p>
          </div>

          <div>
            <h4 className="font-semibold text-white mb-4">Quick Links</h4>
            <div className="space-y-2">
              <Link to="/astrologers" className="block text-sm hover:text-primary transition-colors">Find Astrologers</Link>
              <Link to="/login" className="block text-sm hover:text-primary transition-colors">Login</Link>
              <Link to="/register" className="block text-sm hover:text-primary transition-colors">Register</Link>
            </div>
          </div>

          <div>
            <h4 className="font-semibold text-white mb-4">Contact</h4>
            <div className="space-y-3 text-sm">
              <div className="flex items-center gap-2"><FiMail size={14} /> support@astrotalk.com</div>
              <div className="flex items-center gap-2"><FiPhone size={14} /> +91-1800-123-456</div>
              <div className="flex items-center gap-2"><FiMapPin size={14} /> Mumbai, India</div>
            </div>
            <div className="flex gap-3 mt-4">
              <a href="#" className="p-2 bg-gray-800 rounded-lg hover:bg-primary transition-colors"><FiInstagram size={16} /></a>
              <a href="#" className="p-2 bg-gray-800 rounded-lg hover:bg-primary transition-colors"><FiTwitter size={16} /></a>
              <a href="#" className="p-2 bg-gray-800 rounded-lg hover:bg-primary transition-colors"><FiYoutube size={16} /></a>
            </div>
          </div>
        </div>

        <div className="border-t border-gray-800 mt-8 pt-8 text-center">
          <p className="text-sm text-gray-500">&copy; {new Date().getFullYear()} AstroTalk. All rights reserved.</p>
        </div>
      </div>
    </footer>
  )
}

export default Footer
