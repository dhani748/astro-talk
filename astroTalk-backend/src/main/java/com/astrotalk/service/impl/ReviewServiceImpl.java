package com.astrotalk.service.impl;

import com.astrotalk.dto.ReviewRequest;
import com.astrotalk.dto.ReviewResponse;
import com.astrotalk.entity.*;
import com.astrotalk.exception.ResourceNotFoundException;
import com.astrotalk.repository.*;
import com.astrotalk.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AstrologerRepository astrologerRepository;
    private final ConsultationRepository consultationRepository;

    @Override
    @Transactional
    public ReviewResponse submitReview(Long userId, ReviewRequest request) {
        Consultation consultation = consultationRepository.findById(request.getConsultationId())
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", request.getConsultationId()));

        if (consultation.getStatus() != ConsultationStatus.COMPLETED) {
            throw new RuntimeException("Can only review completed consultations");
        }

        if (reviewRepository.existsByUserIdAndConsultationId(userId, request.getConsultationId())) {
            throw new RuntimeException("You have already reviewed this consultation");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        Astrologer astrologer = consultation.getAstrologer();

        Review review = Review.builder()
                .user(user)
                .astrologer(astrologer)
                .consultation(consultation)
                .rating(request.getRating())
                .comment(request.getComment())
                .isVisible(true)
                .build();

        review = reviewRepository.save(review);

        Double avgRating = reviewRepository.findAverageRatingByAstrologerId(astrologer.getId());
        astrologer.setRating(avgRating);
        astrologerRepository.save(astrologer);

        return toReviewResponse(review);
    }

    @Override
    public Page<ReviewResponse> getAstrologerReviews(Long astrologerId, Pageable pageable) {
        return reviewRepository.findByAstrologerIdOrderByCreatedAtDesc(astrologerId, pageable)
                .map(this::toReviewResponse);
    }

    @Override
    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new ResourceNotFoundException("Review", reviewId);
        }
        reviewRepository.deleteById(reviewId);
    }

    private ReviewResponse toReviewResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .userName(review.getUser().getName())
                .astrologerId(review.getAstrologer().getId())
                .astrologerName(review.getAstrologer().getName())
                .consultationId(review.getConsultation().getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .isVisible(review.isVisible())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
