import { format } from 'date-fns'
import StarRating from './StarRating'

const ReviewCard = ({ review }) => {
  return (
    <div className="bg-white dark:bg-gray-900 rounded-xl p-4 border border-gray-100 dark:border-gray-800">
      <div className="flex items-start gap-3">
        <img
          src={review.userPhoto || `https://ui-avatars.com/api/?name=${review.userName}&background=F59E0B&color=fff&size=40`}
          alt={review.userName}
          className="w-10 h-10 rounded-full object-cover"
        />
        <div className="flex-1">
          <div className="flex items-center justify-between">
            <h4 className="font-medium text-gray-900 dark:text-white text-sm">{review.userName}</h4>
            <span className="text-xs text-gray-400">{format(new Date(review.createdAt), 'MMM dd, yyyy')}</span>
          </div>
          <StarRating rating={review.rating} readonly size="sm" />
          <p className="text-sm text-gray-600 dark:text-gray-400 mt-2">{review.comment}</p>
        </div>
      </div>
    </div>
  )
}

export default ReviewCard
