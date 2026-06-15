import dayjs from 'dayjs'
import StarRating from './StarRating'

const ReviewCard = ({ review }) => {
  return (
    <div className="bg-cosmic-2 rounded-xl p-4 border border-white/5">
      <div className="flex items-start gap-3">
        <img
          src={review.userPhoto || `https://ui-avatars.com/api/?name=${review.userName}&background=F59E0B&color=fff&size=40`}
          alt={review.userName}
          className="w-10 h-10 rounded-full object-cover"
        />
        <div className="flex-1">
          <div className="flex items-center justify-between">
            <h4 className="font-medium text-light text-sm">{review.userName}</h4>
            <span className="text-xs text-muted">{dayjs(review.createdAt).format('MMM dd, yyyy')}</span>
          </div>
          <StarRating rating={review.rating} readonly size="sm" />
          <p className="text-sm text-muted mt-2">{review.comment}</p>
        </div>
      </div>
    </div>
  )
}

export default ReviewCard
