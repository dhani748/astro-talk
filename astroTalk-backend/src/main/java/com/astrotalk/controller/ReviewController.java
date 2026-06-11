package com.astrotalk.controller;

import com.astrotalk.dto.ReviewRequest;
import com.astrotalk.dto.ReviewResponse;
import com.astrotalk.entity.User;
import com.astrotalk.exception.ResourceNotFoundException;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "User reviews and ratings for astrologers")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserRepository userRepository;

    @PostMapping
    @Operation(summary = "Submit a review", description = "Submit a rating and comment for a completed consultation")
    public ResponseEntity<ReviewResponse> submitReview(
            Authentication authentication,
            @Valid @RequestBody ReviewRequest request) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(reviewService.submitReview(user.getId(), request));
    }

    @GetMapping("/astrologer/{id}")
    @Operation(summary = "Get astrologer reviews", description = "Returns paginated reviews for an astrologer")
    public ResponseEntity<Page<ReviewResponse>> getAstrologerReviews(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(reviewService.getAstrologerReviews(id, PageRequest.of(page, size)));
    }

    @DeleteMapping("/{id}")
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
