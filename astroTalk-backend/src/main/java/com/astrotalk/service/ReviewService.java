package com.astrotalk.service;

import com.astrotalk.model.ReviewRequestModel;
import com.astrotalk.model.ReviewResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    ReviewResponseModel submitReview(Long userId, ReviewRequestModel request);

    Page<ReviewResponseModel> getAstrologerReviews(Long astrologerId, Pageable pageable);

    void deleteReview(Long reviewId);
}
