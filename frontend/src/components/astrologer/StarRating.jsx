import { useState } from 'react'
import { FiStar } from 'react-icons/fi'

const StarRating = ({ rating = 0, onChange, readonly = false, size = 'md' }) => {
  const [hovered, setHovered] = useState(0)
  const sizes = { sm: 'text-sm', md: 'text-lg', lg: 'text-2xl' }

  return (
    <div className="flex items-center gap-0.5">
      {[1, 2, 3, 4, 5].map((star) => (
        <button
          key={star}
          type="button"
          disabled={readonly}
          onClick={() => onChange?.(star)}
          onMouseEnter={() => !readonly && setHovered(star)}
          onMouseLeave={() => !readonly && setHovered(0)}
          className={`${readonly ? 'cursor-default' : 'cursor-pointer'} transition-colors ${sizes[size]}`}
        >
          <FiStar
            className={`${
              star <= (hovered || rating)
                ? 'fill-gold text-gold'
                : 'text-gray-300 dark:text-gray-600'
            } transition-colors`}
          />
        </button>
      ))}
    </div>
  )
}

export default StarRating
