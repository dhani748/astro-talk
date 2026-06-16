package com.astrotalk.controller;

import com.astrotalk.constant.WebResource;
import com.astrotalk.entity.Astrologer;
import com.astrotalk.exception.ResourceNotFoundException;
import com.astrotalk.model.AstrologerResponseModel;
import com.astrotalk.model.UpdateAstrologerRequestModel;
import com.astrotalk.repository.AstrologerRepository;
import com.astrotalk.service.AstrologerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST controller for browsing and searching astrologer profiles.
 * Provides endpoints for listing, filtering, and updating astrologer information.
 */
@RestController
@RequestMapping(WebResource.ASTROLOGERS)
@Validated
@RequiredArgsConstructor
@Tag(name = "Astrologers", description = "Browse and search astrologers")
public class AstrologerController {

    private final AstrologerService astrologerService;
    private final AstrologerRepository astrologerRepository;

    /**
     * GET /astrologers - Search and filter astrologers by various criteria.
     *
     * @param specialization optional specialization filter
     * @param language       optional language filter
     * @param minPrice       optional minimum price filter
     * @param maxPrice       optional maximum price filter
     * @param minRating      optional minimum rating filter
     * @param isOnline       optional online status filter
     * @param sortBy         sort field (default "rating")
     * @param sortDir        sort direction "asc" or "desc" (default "desc")
     * @param page           zero-based page index (default 0)
     * @param size           page size (default 20)
     * @return paginated {@link AstrologerResponse} list
     */
    @GetMapping
    @Operation(summary = "Search astrologers", description = "Search/filter astrologers by specialization, language, price, rating, and online status")
    public ResponseEntity<Page<AstrologerResponseModel>> searchAstrologers(
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Boolean isOnline,
            @RequestParam(defaultValue = "rating") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Page<AstrologerResponseModel> result = astrologerService.searchAstrologers(
                specialization, language, minPrice, maxPrice, minRating, isOnline,
                PageRequest.of(page, size, sort));

        return ResponseEntity.ok(result);
    }

    /**
     * GET /astrologers/top - Returns the top 6 astrologers sorted by rating.
     *
     * @return list of top-rated {@link AstrologerResponse}
     */
    @GetMapping(WebResource.TOP)
    @Operation(summary = "Get top astrologers", description = "Returns top 6 astrologers by rating")
    public ResponseEntity<List<AstrologerResponseModel>> getTopAstrologers() {
        return ResponseEntity.ok(astrologerService.getTopAstrologers());
    }

    /**
     * GET /astrologers/{id} - Returns the full profile of a specific astrologer.
     *
     * @param id the astrologer's ID
     * @return the {@link AstrologerResponse} with all profile details
     */
    @GetMapping(WebResource.ASTROLOGER_DETAIL)
    @Operation(summary = "Get astrologer profile", description = "Returns full profile of an astrologer")
    public ResponseEntity<AstrologerResponseModel> getAstrologerProfile(@PathVariable Long id) {
        return ResponseEntity.ok(astrologerService.getAstrologerProfile(id));
    }

    /**
     * GET /astrologers/{id}/availability - Checks whether an astrologer is currently online.
     *
     * @param id the astrologer's ID
     * @return {@code true} if the astrologer's status is ONLINE, {@code false} otherwise
     */
    @GetMapping(WebResource.AVAILABILITY)
    @Operation(summary = "Check astrologer availability", description = "Returns whether astrologer is currently online")
    public ResponseEntity<Boolean> getAvailability(@PathVariable Long id) {
        Astrologer astrologer = astrologerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Astrologer", "id", String.valueOf(id)));
        return ResponseEntity.ok(astrologer.getStatus() != null
                && astrologer.getStatus().name().equals("ONLINE"));
    }

    /**
     * PUT /astrologers/profile - Updates the authenticated astrologer's own profile.
     *
     * @param authentication the current authenticated principal
     * @param request        the profile fields to update
     * @return the updated {@link AstrologerResponse}
     */
    @PutMapping(WebResource.PROFILE)
    @Operation(summary = "Update astrologer profile", description = "Updates the authenticated astrologer's profile")
    public ResponseEntity<AstrologerResponseModel> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateAstrologerRequestModel request) {
        Astrologer astrologer = getCurrentAstrologer(authentication);
        return ResponseEntity.ok(astrologerService.updateAstrologer(astrologer.getId(), request));
    }

    private Astrologer getCurrentAstrologer(Authentication authentication) {
        String email = authentication.getName();
        return astrologerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Astrologer", "email", email));
    }
}
