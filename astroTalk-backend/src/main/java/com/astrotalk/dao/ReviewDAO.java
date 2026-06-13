package com.astrotalk.dao;

import com.astrotalk.entity.Review;
import com.astrotalk.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Data access layer wrapping {@link ReviewRepository} for {@link Review} entity operations.
 * Provides CRUD methods and queries for astrologer reviews and ratings.
 */
@Component
@RequiredArgsConstructor
public class ReviewDAO {

    private static final Logger log = LoggerFactory.getLogger(ReviewDAO.class);
    private final ReviewRepository reviewRepository;

    /**
     * Persists a new or existing review.
     *
     * @param review the review entity to save
     * @return the saved review entity
     */
    public Review save(Review review) {
        log.debug("Saving review for astrologer: {}", review.getAstrologer().getId());
        try {
            return reviewRepository.save(review);
        } catch (Exception e) {
            log.error("Error saving review for astrologer: {}", review.getAstrologer().getId(), e);
            throw e;
        }
    }

    /**
     * Finds reviews for an astrologer ordered by creation date descending.
     *
     * @param astrologerId the astrologer ID
     * @param pageable     the pagination information
     * @return a page of reviews
     */
    public Page<Review> findByAstrologerIdOrderByCreatedAtDesc(Long astrologerId, Pageable pageable) {
        log.debug("Finding reviews by astrologer id: {}", astrologerId);
        try {
            return reviewRepository.findByAstrologerIdOrderByCreatedAtDesc(astrologerId, pageable);
        } catch (Exception e) {
            log.error("Error finding reviews by astrologer id: {}", astrologerId, e);
            throw e;
        }
    }

    /**
     * Computes the average rating for an astrologer.
     *
     * @param astrologerId the astrologer ID
     * @return the average rating, or 0 if no reviews exist
     */
    public Double findAverageRatingByAstrologerId(Long astrologerId) {
        log.debug("Finding average rating by astrologer id: {}", astrologerId);
        try {
            return reviewRepository.findAverageRatingByAstrologerId(astrologerId);
        } catch (Exception e) {
            log.error("Error finding average rating by astrologer id: {}", astrologerId, e);
            throw e;
        }
    }

    /**
     * Checks whether a review already exists for a given user and consultation.
     *
     * @param userId         the user ID
     * @param consultationId the consultation ID
     * @return true if a review exists
     */
    public boolean existsByUserIdAndConsultationId(String userId, Long consultationId) {
        log.debug("Checking if review exists by userId: {} and consultationId: {}", userId, consultationId);
        try {
            return reviewRepository.existsByUserUserIdAndConsultationId(userId, consultationId);
        } catch (Exception e) {
            log.error("Error checking review existence by user id: {} and consultation id: {}", userId, consultationId, e);
            throw e;
        }
    }

    /**
     * Checks if a review exists by its ID.
     *
     * @param id the review ID
     * @return true if the review exists
     */
    public boolean existsById(Long id) {
        log.debug("Checking if review exists by id: {}", id);
        try {
            return reviewRepository.existsById(id);
        } catch (Exception e) {
            log.error("Error checking review existence by id: {}", id, e);
            throw e;
        }
    }

    /**
     * Deletes a review by its ID.
     *
     * @param id the review ID to delete
     */
    public void deleteById(Long id) {
        log.debug("Deleting review by id: {}", id);
        try {
            reviewRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting review by id: {}", id, e);
            throw e;
        }
    }

    /**
     * Deletes the given review entity.
     *
     * @param review the review entity to delete
     */
    public void delete(Review review) {
        log.debug("Deleting review: {}", review.getId());
        try {
            reviewRepository.delete(review);
        } catch (Exception e) {
            log.error("Error deleting review: {}", review.getId(), e);
            throw e;
        }
    }
}
