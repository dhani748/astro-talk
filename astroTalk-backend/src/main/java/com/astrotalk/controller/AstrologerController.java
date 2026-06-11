package com.astrotalk.controller;

import com.astrotalk.dto.AstrologerResponse;
import com.astrotalk.dto.UpdateAstrologerRequest;
import com.astrotalk.entity.Astrologer;
import com.astrotalk.exception.ResourceNotFoundException;
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
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/astrologers")
@RequiredArgsConstructor
@Tag(name = "Astrologers", description = "Browse and search astrologers")
public class AstrologerController {

    private final AstrologerService astrologerService;
    private final AstrologerRepository astrologerRepository;

    @GetMapping
    @Operation(summary = "Search astrologers", description = "Search/filter astrologers by specialization, language, price, rating, and online status")
    public ResponseEntity<Page<AstrologerResponse>> searchAstrologers(
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
        Page<AstrologerResponse> result = astrologerService.searchAstrologers(
                specialization, language, minPrice, maxPrice, minRating, isOnline,
                PageRequest.of(page, size, sort));

        return ResponseEntity.ok(result);
    }

    @GetMapping("/top")
    @Operation(summary = "Get top astrologers", description = "Returns top 6 astrologers by rating")
    public ResponseEntity<List<AstrologerResponse>> getTopAstrologers() {
        return ResponseEntity.ok(astrologerService.getTopAstrologers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get astrologer profile", description = "Returns full profile of an astrologer")
    public ResponseEntity<AstrologerResponse> getAstrologerProfile(@PathVariable Long id) {
        return ResponseEntity.ok(astrologerService.getAstrologerProfile(id));
    }

    @GetMapping("/{id}/availability")
    @Operation(summary = "Check astrologer availability", description = "Returns whether astrologer is currently online")
    public ResponseEntity<Boolean> getAvailability(@PathVariable Long id) {
        Astrologer astrologer = astrologerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Astrologer", id));
        return ResponseEntity.ok(astrologer.getStatus() != null
                && astrologer.getStatus().name().equals("ONLINE"));
    }

    @PutMapping("/profile")
    @Operation(summary = "Update astrologer profile", description = "Updates the authenticated astrologer's profile")
    public ResponseEntity<AstrologerResponse> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateAstrologerRequest request) {
        Astrologer astrologer = getCurrentAstrologer(authentication);
        return ResponseEntity.ok(astrologerService.updateAstrologer(astrologer.getId(), request));
    }

    private Astrologer getCurrentAstrologer(Authentication authentication) {
        String email = authentication.getName();
        return astrologerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Astrologer", "email", email));
    }
}
