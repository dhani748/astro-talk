import { Link } from 'react-router-dom'
import { FiMessageCircle, FiPhone, FiStar } from 'react-icons/fi'
import Badge from '../common/Badge'

const AstrologerCard = ({ astrologer }) => {
  return (
    <div className="bg-white dark:bg-gray-900 rounded-2xl p-6 shadow-sm border border-gray-100 dark:border-gray-800 hover:shadow-md transition-all duration-300">
      <div className="flex items-start gap-4">
        <div className="relative flex-shrink-0">
          <img
            src={astrologer.profilePhoto || `https://ui-avatars.com/api/?name=${astrologer.name}&background=6B21A8&color=fff&size=80`}
            alt={astrologer.name}
            className="w-20 h-20 rounded-xl object-cover"
          />
          <div className="absolute -top-1 -right-1">
            <Badge status={astrologer.online ? 'online' : 'offline'} />
          </div>
        </div>
        <div className="flex-1 min-w-0">
          <Link to={`/astrologers/${astrologer.id}`} className="hover:text-primary transition-colors">
            <h3 className="font-semibold text-gray-900 dark:text-white text-lg truncate">{astrologer.name}</h3>
          </Link>
          <p className="text-sm text-gray-500 dark:text-gray-400 mt-0.5">{astrologer.specialization}</p>
          <div className="flex items-center gap-1 mt-1">
            <FiStar className="text-gold fill-gold" />
            <span className="text-sm font-medium text-gray-700 dark:text-gray-300">{astrologer.rating?.toFixed(1) || '5.0'}</span>
            <span className="text-xs text-gray-400">({astrologer.reviewCount || 0})</span>
          </div>
          <div className="flex items-center gap-2 mt-3">
            <span className="text-primary font-semibold">₹{astrologer.pricePerMin || 10}/min</span>
          </div>
          <div className="flex gap-2 mt-3">
            <Link
              to={`/consultation/${astrologer.id}?type=chat`}
              className="flex items-center gap-1.5 px-3 py-1.5 bg-primary/10 text-primary rounded-lg text-sm font-medium hover:bg-primary/20 transition-colors"
            >
              <FiMessageCircle size={14} /> Chat
            </Link>
            <Link
              to={`/consultation/${astrologer.id}?type=call`}
              className="flex items-center gap-1.5 px-3 py-1.5 bg-gold/10 text-gold-dark rounded-lg text-sm font-medium hover:bg-gold/20 transition-colors"
            >
              <FiPhone size={14} /> Call
            </Link>
          </div>
        </div>
      </div>
    </div>
  )
}

export default AstrologerCard
