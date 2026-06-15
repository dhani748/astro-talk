import { Link } from 'react-router-dom'
import { FiMessageCircle, FiPhone, FiStar } from 'react-icons/fi'
import Badge from '../common/Badge'

const AstrologerCard = ({ astrologer }) => {
  return (
    <div className="astro-card rounded-2xl p-6 transition-all duration-300">
      <div className="flex items-start gap-4">
        <div className="relative flex-shrink-0">
          <img
            src={astrologer.profilePhoto || `https://ui-avatars.com/api/?name=${astrologer.name}&background=2A241C&color=C9A84C&size=80`}
            alt={astrologer.name}
            className="w-20 h-20 rounded-xl object-cover"
            style={{ border: '1px solid rgba(201,168,76,0.15)' }}
          />
          <div className="absolute -top-1 -right-1">
            <Badge status={astrologer.online ? 'online' : 'offline'} />
          </div>
        </div>
        <div className="flex-1 min-w-0">
          <Link to={`/astrologers/${astrologer.id}`} className="transition-colors">
            <h3 className="font-semibold" style={{ fontFamily: 'var(--font-display)', fontSize: '1.1rem', color: 'var(--cream)' }}>{astrologer.name}</h3>
          </Link>
          <p style={{ fontSize: '.85rem', color: 'var(--muted)', marginTop: 2 }}>{astrologer.specialization}</p>
          <div className="flex items-center gap-1 mt-1">
            <FiStar style={{ color: 'var(--gold)', fill: 'var(--gold)' }} size={14} />
            <span style={{ fontSize: '.9rem', fontWeight: 500, color: 'var(--light)' }}>{astrologer.rating?.toFixed(1) || '5.0'}</span>
            <span style={{ fontSize: '.75rem', color: 'var(--muted)' }}>({astrologer.reviewCount || 0})</span>
          </div>
          <div className="flex items-center gap-2 mt-3">
            <span style={{ color: 'var(--gold)', fontWeight: 600, fontSize: '.95rem' }}>₹{astrologer.pricePerMin || 10}/min</span>
          </div>
          <div className="flex gap-2 mt-3">
            <Link
              to={`/astrologers/${astrologer.id}`}
              className="btn-ghost !text-[.55rem] !px-3 !py-1.5 !inline-flex !items-center !gap-1"
              style={{ fontSize: '.6rem', padding: '.4rem .8rem' }}
            >
              <FiMessageCircle size={12} /> Chat
            </Link>
            <Link
              to={`/astrologers/${astrologer.id}`}
              className="btn-gold !text-[.55rem] !px-3 !py-1.5 !inline-flex !items-center !gap-1"
              style={{ fontSize: '.6rem', padding: '.4rem .8rem' }}
            >
              <FiPhone size={12} /> Call
            </Link>
          </div>
        </div>
      </div>
    </div>
  )
}

export default AstrologerCard
