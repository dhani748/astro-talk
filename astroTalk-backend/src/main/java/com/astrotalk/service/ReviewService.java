package com.astrotalk.service;

import com.astrotalk.dto.ReviewRequest;
import com.astrotalk.dto.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    ReviewResponse submitReview(Long userId, ReviewRequest request);

    Page<ReviewResponse> getAstrologerReviews(Long astrologerId, Pageable pageable);

    void deleteReview(Long reviewId);
}
