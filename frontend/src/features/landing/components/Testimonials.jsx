import { motion, useInView } from 'framer-motion'
import { useRef } from 'react'

const testimonials = [
  { id: 1, name: 'Priya Sharma', avatar: 'https://i.pravatar.cc/100?img=1', rating: 5, text: 'The astrologer was incredibly accurate about my career path. The detailed Vedic analysis gave me clarity I had been seeking for months.', type: 'Vedic Astrology', location: 'Mumbai, India' },
  { id: 2, name: 'Rahul Mehta', avatar: 'https://i.pravatar.cc/100?img=3', rating: 5, text: 'Very professional platform. Got an instant tarot consultation during a tough time. Highly recommended for anyone seeking guidance.', type: 'Tarot Reading', location: 'Delhi, India' },
  { id: 3, name: 'Anika Kapoor', avatar: 'https://i.pravatar.cc/100?img=5', rating: 5, text: 'The numerology session was eye-opening. The detailed analysis and practical remedies made a real difference in my life decisions.', type: 'Numerology', location: 'Pune, India' },
  { id: 4, name: 'Vikram Singh', avatar: 'https://i.pravatar.cc/100?img=8', rating: 5, text: 'I was skeptical at first, but the palm reading session was surprisingly accurate. The astrologer picked up on things I never mentioned.', type: 'Palm Reading', location: 'Jaipur, India' },
  { id: 5, name: 'Neha Gupta', avatar: 'https://i.pravatar.cc/100?img=9', rating: 5, text: 'The love & relationship consultation gave me such clarity. The astrologer was compassionate and provided practical guidance.', type: 'Love & Relationship', location: 'Bangalore, India' },
  { id: 6, name: 'Arun Kumar', avatar: 'https://i.pravatar.cc/100?img=11', rating: 5, text: 'Career consultation was a game-changer. The astrological timing advice helped me make the right move at the right moment.', type: 'Career Consultation', location: 'Hyderabad, India' },
]

const duplicated = [...testimonials, ...testimonials]

export default function Testimonials() {
  const ref = useRef(null)
  const isInView = useInView(ref, { once: true, margin: '-100px' })

  return (
    <section ref={ref} className="relative overflow-hidden bg-cosmic py-24">
      <style>{`
        @keyframes marquee {
          from { transform: translateX(0); }
          to { transform: translateX(-50%); }
        }
        .marquee-track {
          animation: marquee 40s linear infinite;
        }
        .marquee-track:hover {
          animation-play-state: paused;
        }
      `}</style>

      <motion.div
        initial={{ opacity: 0, y: 40 }}
        animate={isInView ? { opacity: 1, y: 0 } : {}}
        transition={{ duration: 0.7, ease: 'easeOut' }}
        className="mx-auto mb-14 max-w-7xl px-6 text-center"
      >
        <div className="mb-4 inline-flex flex-col items-center gap-2">
          <div className="inline-flex items-center gap-2 rounded-full border border-gold/20 bg-white/5 px-4 py-1.5 text-xs font-semibold tracking-widest uppercase text-gold-light backdrop-blur-md">
            <span className="h-1.5 w-1.5 rounded-full bg-gold shadow-[0_0_6px_#D4AF37]" />
            Testimonials
          </div>
          <span className="h-0.5 w-8 rounded-full bg-gold" />
        </div>
        <h2 className="font-display text-4xl font-bold text-white md:text-5xl">
          What Our <span className="text-gold">Users Say</span>
        </h2>
        <p className="mx-auto mt-4 max-w-xl text-base text-muted">
          Real stories from real people — discover how our astrological insights have transformed lives.
        </p>
      </motion.div>

      <motion.div
        initial={{ opacity: 0 }}
        animate={isInView ? { opacity: 1 } : {}}
        transition={{ duration: 0.8, delay: 0.3 }}
        className="relative mx-auto w-full overflow-hidden"
      >
        <div className="marquee-track flex w-max gap-6 px-3">
          {duplicated.map((t, i) => (
            <div
              key={`${t.id}-${i}`}
              className="flex w-[85vw] shrink-0 flex-col gap-4 rounded-2xl border border-white/10 bg-white/5 p-6 backdrop-blur-xl md:w-[30rem]"
            >
              <div className="flex items-center gap-4">
                <img
                  src={t.avatar}
                  alt={t.name}
                  className="h-12 w-12 shrink-0 rounded-full border-2 border-white/10 object-cover"
                />
                <div className="min-w-0 flex-1">
                  <div className="flex items-center gap-1.5">
                    <h3 className="truncate text-base font-semibold text-white">{t.name}</h3>
                    <svg className="h-4 w-4 shrink-0 text-green-400" viewBox="0 0 24 24" fill="currentColor">
                      <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z" />
                    </svg>
                  </div>
                  <p className="truncate text-sm text-white/50">{t.location}</p>
                </div>
              </div>

              <div className="flex gap-0.5">
                {Array.from({ length: 5 }, (_, j) => (
                  <svg key={j} className="h-4 w-4 text-gold" viewBox="0 0 24 24" fill="currentColor">
                    <path d="M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z" />
                  </svg>
                ))}
              </div>

              <p className="font-medium leading-relaxed text-white/70 italic">
                &ldquo;{t.text}&rdquo;
              </p>

              <span className="self-start rounded-full border border-gold/30 bg-gold/10 px-3 py-1 text-xs font-semibold tracking-wide text-gold-light">
                {t.type}
              </span>
            </div>
          ))}
        </div>
      </motion.div>
    </section>
  )
}
