package com.astrotalk.controller;

import com.astrotalk.constant.WebResource;
import com.astrotalk.entity.Astrologer;
import com.astrotalk.entity.AstrologerStatus;
import com.astrotalk.exception.ResourceNotFoundException;
import com.astrotalk.repository.AstrologerRepository;
import com.astrotalk.service.AstrologerStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST controller for toggling astrologer online/offline status.
 */
@RestController
@RequestMapping(WebResource.ASTROLOGERS)
@Validated
@RequiredArgsConstructor
@Tag(name = "Astrologers", description = "Browse and search astrologers")
public class AstrologerStatusController {

    private final AstrologerStatusService astrologerStatusService;
    private final AstrologerRepository astrologerRepository;

    /**
     * PUT /astrologers/status/toggle - Toggles the authenticated astrologer's online/offline status.
     *
     * @param authentication the current authenticated astrologer
     * @return map containing {@code astrologerId} and the new {@code status}
     */
    @PutMapping(WebResource.STATUS_TOGGLE)
    @Operation(summary = "Toggle online status", description = "Toggles the astrologer's online/offline status")
    public ResponseEntity<Map<String, Object>> toggleStatus(Authentication authentication) {
        Astrologer astrologer = getCurrentAstrologer(authentication);
        AstrologerStatus newStatus = astrologerStatusService.toggleOnlineStatus(astrologer.getId());
        return ResponseEntity.ok(Map.of(
                "astrologerId", astrologer.getId(),
                "status", newStatus.name()
        ));
    }

    private Astrologer getCurrentAstrologer(Authentication authentication) {
        String email = authentication.getName();
        return astrologerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Astrologer", "email", email));
    }
}
