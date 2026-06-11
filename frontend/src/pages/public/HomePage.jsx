import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { FiStar, FiMessageCircle, FiPhone, FiShield, FiClock, FiUsers, FiArrowRight, FiChevronLeft, FiChevronRight } from 'react-icons/fi'
import { getTopAstrologers } from '../../api/astrologerAPI'
import AstrologerCard from '../../components/astrologer/AstrologerCard'
import LoadingSpinner from '../../components/common/LoadingSpinner'

const testimonials = [
  { name: 'Priya S.', text: 'AstroTalk changed my life! The astrologer was incredibly accurate about my career path.', rating: 5 },
  { name: 'Rahul M.', text: 'Very professional platform. Got instant consultation during a tough time. Highly recommended!', rating: 5 },
  { name: 'Anita K.', text: 'The Vedic astrology session was eye-opening. Detailed analysis and practical remedies.', rating: 5 },
]

const HomePage = () => {
  const [topAstrologers, setTopAstrologers] = useState([])
  const [loading, setLoading] = useState(true)
  const [testimonialIdx, setTestimonialIdx] = useState(0)

  useEffect(() => {
    getTopAstrologers()
      .then(({ data }) => setTopAstrologers(data.content || data || []))
      .catch(() => {})
      .finally(() => setLoading(false))
  }, [])

  return (
    <div className="page-transition">
      <section className="relative overflow-hidden bg-gradient-to-br from-purple-50 via-white to-amber-50 dark:from-gray-900 dark:via-gray-900 dark:to-purple-950 py-20 lg:py-28">
        <div className="absolute top-0 left-0 w-72 h-72 bg-primary/10 rounded-full -translate-x-1/2 -translate-y-1/2 blur-3xl" />
        <div className="absolute bottom-0 right-0 w-96 h-96 bg-gold/10 rounded-full translate-x-1/2 translate-y-1/2 blur-3xl" />
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative">
          <div className="max-w-3xl mx-auto text-center">
            <div className="inline-flex items-center gap-2 px-4 py-1.5 bg-primary/10 text-primary rounded-full text-sm font-medium mb-6">
              <FiStar className="text-gold fill-gold" size={14} /> Trusted by 10,000+ Users
            </div>
            <h1 className="text-4xl sm:text-5xl lg:text-6xl font-extrabold text-gray-900 dark:text-white leading-tight">
              Talk to Expert <br />
              <span className="bg-gradient-to-r from-primary to-gold bg-clip-text text-transparent">Astrologers</span> Online
            </h1>
            <p className="mt-6 text-lg text-gray-600 dark:text-gray-400 max-w-xl mx-auto leading-relaxed">
              Get personalized astrological guidance from verified experts. Chat or call anytime, anywhere.
            </p>
            <div className="flex flex-wrap items-center justify-center gap-4 mt-8">
              <Link to="/astrologers" className="inline-flex items-center gap-2 px-8 py-3 bg-primary text-white rounded-xl font-semibold hover:bg-primary-dark transition-colors shadow-lg shadow-primary/25">
                Find Astrologers <FiArrowRight />
              </Link>
              <Link to="/register" className="inline-flex items-center gap-2 px-8 py-3 border-2 border-gray-200 dark:border-gray-700 text-gray-700 dark:text-gray-300 rounded-xl font-semibold hover:border-primary hover:text-primary transition-colors">
                Get Started
              </Link>
            </div>
          </div>
        </div>
      </section>

      <section className="py-16 bg-white dark:bg-gray-900">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl font-bold text-gray-900 dark:text-white">How It Works</h2>
            <p className="text-gray-500 dark:text-gray-400 mt-2">Get guidance in three simple steps</p>
          </div>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            {[
              { icon: FiUsers, title: 'Choose an Astrologer', desc: 'Browse verified experts by specialization, language, and rating.' },
              { icon: FiMessageCircle, title: 'Start Consultation', desc: 'Chat or call instantly. No appointment needed.' },
              { icon: FiShield, title: 'Get Guidance', desc: 'Receive personalized predictions and remedies.' },
            ].map((step, i) => (
              <div key={i} className="text-center p-6">
                <div className="w-16 h-16 bg-primary/10 rounded-2xl flex items-center justify-center mx-auto mb-4">
                  <step.icon className="text-primary" size={28} />
                </div>
                <h3 className="text-lg font-semibold text-gray-900 dark:text-white mb-2">{step.title}</h3>
                <p className="text-sm text-gray-500 dark:text-gray-400">{step.desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      <section className="py-16 bg-gray-50 dark:bg-gray-950">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between mb-8">
            <div>
              <h2 className="text-3xl font-bold text-gray-900 dark:text-white">Top Astrologers</h2>
              <p className="text-gray-500 dark:text-gray-400 mt-1">Most trusted experts on our platform</p>
            </div>
            <Link to="/astrologers" className="hidden sm:flex items-center gap-1 text-primary font-medium hover:underline text-sm">
              View All <FiArrowRight />
            </Link>
          </div>
          {loading ? (
            <LoadingSpinner className="py-12" />
          ) : topAstrologers.length === 0 ? (
            <p className="text-center py-12 text-gray-400">No astrologers available yet.</p>
          ) : (
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
              {topAstrologers.slice(0, 6).map((astro) => (
                <AstrologerCard key={astro.id} astrologer={astro} />
              ))}
            </div>
          )}
        </div>
      </section>

      <section className="py-16 bg-white dark:bg-gray-900">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl font-bold text-gray-900 dark:text-white">What Users Say</h2>
          </div>
          <div className="max-w-2xl mx-auto relative">
            <div className="bg-gray-50 dark:bg-gray-800 rounded-2xl p-8 text-center">
              <div className="flex justify-center mb-4">
                {[...Array(testimonials[testimonialIdx].rating)].map((_, i) => (
                  <FiStar key={i} className="text-gold fill-gold" size={20} />
                ))}
              </div>
              <p className="text-gray-600 dark:text-gray-300 italic mb-6">"{testimonials[testimonialIdx].text}"</p>
              <p className="font-semibold text-gray-900 dark:text-white">- {testimonials[testimonialIdx].name}</p>
            </div>
            <div className="flex justify-center gap-2 mt-4">
              {testimonials.map((_, i) => (
                <button key={i} onClick={() => setTestimonialIdx(i)} className={`w-2.5 h-2.5 rounded-full transition-colors ${i === testimonialIdx ? 'bg-primary' : 'bg-gray-300 dark:bg-gray-700'}`} />
              ))}
            </div>
          </div>
        </div>
      </section>
    </div>
  )
}

export default HomePage
