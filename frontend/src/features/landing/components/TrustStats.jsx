import { motion, useInView } from 'framer-motion'
import { useEffect, useRef, useState } from 'react'
import { FiMessageCircle, FiUsers, FiGlobe, FiStar } from 'react-icons/fi'

const iconMap = {
  FiMessageCircle,
  FiUsers,
  FiGlobe,
  FiStar,
}

const stats = [
  { value: 120, suffix: 'M+', label: 'Consultations', icon: 'FiMessageCircle' },
  { value: 48, suffix: 'K+', label: 'Verified Astrologers', icon: 'FiUsers' },
  { value: 13, suffix: '', label: 'Languages Supported', icon: 'FiGlobe' },
  { value: 49, suffix: '', label: 'Customer Rating', icon: 'FiStar', isRating: true },
]

function AnimatedNumber({ value, suffix = '', isRating = false }) {
  const [count, setCount] = useState(0)
  const ref = useRef(null)
  const isInView = useInView(ref, { once: true })
  const started = useRef(false)

  useEffect(() => {
    if (!isInView || started.current) return
    started.current = true

    const duration = 2000
    const startTime = performance.now()

    function animate(currentTime) {
      const elapsed = currentTime - startTime
      const progress = Math.min(elapsed / duration, 1)
      const eased = 1 - Math.pow(1 - progress, 3)
      setCount(eased * value)

      if (progress < 1) {
        requestAnimationFrame(animate)
      }
    }

    requestAnimationFrame(animate)
  }, [isInView, value])

  const display = isRating ? count.toFixed(1) : Math.floor(count)

  return (
    <span ref={ref} className="text-5xl font-bold text-white">
      {display}{suffix}
    </span>
  )
}

export default function TrustStats() {
  return (
    <section className="bg-[#0d0d1a] px-6 py-24 lg:px-10">
      <div className="mx-auto max-w-7xl">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true }}
          transition={{ duration: 0.6 }}
          className="mb-16 text-center"
        >
          <span className="mb-4 inline-block rounded-full border border-white/10 bg-white/5 px-4 py-1.5 text-sm font-medium tracking-wide text-amber-300/80 backdrop-blur-md">
            Trusted Worldwide
          </span>
          <h2 className="font-display text-4xl font-bold text-white sm:text-5xl">
            Numbers That <span className="text-amber-400">Speak</span>
          </h2>
        </motion.div>

        <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-4">
          {stats.map((stat, i) => {
            const IconComponent = iconMap[stat.icon]
            return (
              <motion.div
                key={stat.label}
                initial={{ opacity: 0, y: 40 }}
                whileInView={{ opacity: 1, y: 0 }}
                viewport={{ once: true }}
                transition={{ duration: 0.6, delay: i * 0.15 }}
                className="flex flex-col items-center rounded-2xl border border-white/10 bg-white/5 p-8 text-center backdrop-blur-xl"
              >
                <div className="mb-5 rounded-full bg-gradient-to-br from-amber-400 to-purple-600 p-4 shadow-lg">
                  <IconComponent className="h-7 w-7 text-white" />
                </div>
                <AnimatedNumber value={stat.value} suffix={stat.suffix} isRating={stat.isRating} />
                {stat.isRating && (
                  <FiStar className="mx-auto mt-1 h-5 w-5 text-amber-400" />
                )}
                <span className="mt-2 text-sm tracking-wide text-white/50">
                  {stat.label}
                </span>
              </motion.div>
            )
          })}
        </div>
      </div>
    </section>
  )
}
