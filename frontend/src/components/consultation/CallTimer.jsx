import { useState, useEffect } from 'react'

const CallTimer = ({ startTime }) => {
  const [elapsed, setElapsed] = useState(0)

  useEffect(() => {
    const start = startTime ? new Date(startTime).getTime() : Date.now()
    const interval = setInterval(() => {
      setElapsed(Math.floor((Date.now() - start) / 1000))
    }, 1000)
    return () => clearInterval(interval)
  }, [startTime])

  const formatTime = (seconds) => {
    const mins = Math.floor(seconds / 60)
    const secs = seconds % 60
    return `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`
  }

  return (
    <div className="flex items-center gap-2 px-4 py-2 bg-gold/10 rounded-full">
      <span className="w-2 h-2 bg-green-500 rounded-full animate-pulse" />
      <span className="text-sm font-mono font-semibold text-gold">{formatTime(elapsed)}</span>
    </div>
  )
}

export default CallTimer
