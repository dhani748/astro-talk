package com.astrotalk.controller;

import com.astrotalk.constant.WebResource;
import com.astrotalk.entity.User;
import com.astrotalk.exception.ResourceNotFoundException;
import com.astrotalk.model.ReviewRequestModel;
import com.astrotalk.model.ReviewResponseModel;
import com.astrotalk.repository.UserRepository;
import com.astrotalk.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for astrologer reviews and ratings.
 * Handles submitting, retrieving, and deleting reviews.
 */
@RestController
@RequestMapping(WebResource.REVIEWS)
@Validated
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "User reviews and ratings for astrologers")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserRepository userRepository;

    /**
     * POST /reviews - Submits a rating and comment for a completed consultation.
     *
     * @param authentication the current authenticated user
     * @param request        the review details (consultation ID, rating, comment)
     * @return the created {@link ReviewResponseModel}
     */
    @PostMapping
    @Operation(summary = "Submit a review", description = "Submit a rating and comment for a completed consultation")
    public ResponseEntity<ReviewResponseModel> submitReview(
            Authentication authentication,
            @Valid @RequestBody ReviewRequestModel request) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(reviewService.submitReview(user.getUserId(), request));
    }

    /**
     * GET /reviews/astrologer/{id} - Returns paginated reviews for a specific astrologer.
     *
     * @param id   the astrologer's ID
     * @param page zero-based page index (default 0)
     * @param size page size (default 10)
     * @return paginated list of {@link ReviewResponseModel}
     */
    @GetMapping(WebResource.ASTRologer_REVIEWS)
    @Operation(summary = "Get astrologer reviews", description = "Returns paginated reviews for an astrologer")
    public ResponseEntity<Page<ReviewResponseModel>> getAstrologerReviews(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(reviewService.getAstrologerReviews(id, PageRequest.of(page, size)));
    }

    /**
     * DELETE /reviews/{id} - Deletes a review by ID (admin only).
     *
     * @param id the review ID to delete
     * @return 204 No Content
     */
    @DeleteMapping(WebResource.ID_PATH)
    @Operation(summary = "Delete a review", description = "Admin only - deletes a review")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    private User getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }
}
