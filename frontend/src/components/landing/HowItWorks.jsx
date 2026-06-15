import { useRef } from 'react'
import { motion, useInView } from 'framer-motion'
import { FiUserPlus, FiSearch, FiVideo, FiStar } from 'react-icons/fi'

const steps = [
  {
    number: '01',
    icon: FiUserPlus,
    title: 'Register',
    desc: 'Create your account in seconds.',
  },
  {
    number: '02',
    icon: FiSearch,
    title: 'Select Expert',
    desc: 'Browse verified astrologers by expertise, rating, and language.',
  },
  {
    number: '03',
    icon: FiVideo,
    title: 'Connect Instantly',
    desc: 'Start voice, video, or chat consultation immediately.',
  },
  {
    number: '04',
    icon: FiStar,
    title: 'Get Guidance',
    desc: 'Receive personalized insights and practical remedies.',
  },
]

const containerVariants = {
  hidden: {},
  visible: {
    transition: { staggerChildren: 0.2 },
  },
}

const stepVariants = {
  hidden: { opacity: 0, y: 40 },
  visible: {
    opacity: 1,
    y: 0,
    transition: { duration: 0.7, ease: 'easeOut' },
  },
}

export default function HowItWorks() {
  const ref = useRef(null)
  const isInView = useInView(ref, { once: true, margin: '-100px' })

  return (
    <section
      ref={ref}
      id="how-it-works"
      className="relative overflow-hidden bg-[#0a0a0f] px-6 py-20 md:py-28 lg:px-10"
    >
      {/* Header */}
      <div className="mx-auto max-w-7xl text-center">
        <motion.span
          initial={{ opacity: 0, y: 20 }}
          animate={isInView ? { opacity: 1, y: 0 } : {}}
          transition={{ duration: 0.5, ease: 'easeOut' }}
          className="inline-block text-xs font-semibold tracking-[0.2em] uppercase text-gold/80"
        >
          How It Works
        </motion.span>
        <motion.h2
          initial={{ opacity: 0, y: 20 }}
          animate={isInView ? { opacity: 1, y: 0 } : {}}
          transition={{ duration: 0.5, delay: 0.1, ease: 'easeOut' }}
          className="mt-3 font-display text-4xl font-bold text-white md:text-5xl"
        >
          Your Journey to the <span className="text-gold">Stars</span>
        </motion.h2>
      </div>

      {/* Timeline */}
      <div className="relative mx-auto mt-16 max-w-4xl">
        {/* Gradient animated line */}
        <motion.div
          initial={{ scaleY: 0 }}
          animate={isInView ? { scaleY: 1 } : {}}
          transition={{ duration: 1.2, delay: 0.4, ease: 'easeOut' }}
          style={{ transformOrigin: 'top' }}
          className="absolute bottom-0 left-7 top-0 w-0.5 bg-gradient-to-b from-gold via-purple-500 to-purple-700 shadow-[0_0_12px_rgba(212,175,55,0.3)] md:left-1/2 md:-translate-x-px"
        />

        {/* Steps */}
        <motion.div
          variants={containerVariants}
          initial="hidden"
          animate={isInView ? 'visible' : 'hidden'}
          className="relative space-y-10 md:space-y-16"
        >
          {steps.map((step, index) => {
            const Icon = step.icon
            const isLeft = index % 2 === 0

            return (
              <motion.div
                key={step.number}
                variants={stepVariants}
                className="relative pl-16 md:pl-0"
              >
                {/* Glowing node */}
                <div className="absolute left-7 top-0 z-10 -translate-x-1/2 md:left-1/2">
                  <div className="relative flex h-14 w-14 items-center justify-center rounded-full bg-gradient-to-br from-gold to-purple-600 shadow-lg shadow-purple-600/30">
                    <span className="text-sm font-bold text-white">
                      {step.number}
                    </span>
                  </div>
                  <div className="absolute inset-0 -z-10 animate-pulse rounded-full bg-gold/20 blur-xl" />
                </div>

                {/* Card */}
                <div
                  className={
                    isLeft
                      ? 'md:ml-[calc(50%+2rem)] md:w-[calc(50%-2rem)]'
                      : 'md:mr-[calc(50%+2rem)] md:w-[calc(50%-2rem)]'
                  }
                >
                  <div className="rounded-2xl border border-white/10 bg-white/5 p-6 backdrop-blur-xl transition-all duration-300 hover:border-gold/30 hover:shadow-lg hover:shadow-gold/5">
                    <div className="mb-4 flex h-12 w-12 items-center justify-center rounded-lg bg-gradient-to-br from-gold/20 to-purple-600/20">
                      <Icon className="h-6 w-6 text-gold" />
                    </div>
                    <h3 className="font-display text-xl font-semibold text-white">
                      {step.title}
                    </h3>
                    <p className="mt-2 text-sm leading-relaxed text-white/60">
                      {step.desc}
                    </p>
                  </div>
                </div>
              </motion.div>
            )
          })}
        </motion.div>
      </div>
    </section>
  )
}
