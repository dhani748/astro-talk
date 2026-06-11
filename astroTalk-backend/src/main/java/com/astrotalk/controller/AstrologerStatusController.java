package com.astrotalk.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/astrologers/status")
@RequiredArgsConstructor
@Tag(name = "Astrologers", description = "Browse and search astrologers")
public class AstrologerStatusController {

    private final AstrologerStatusService astrologerStatusService;
    private final AstrologerRepository astrologerRepository;

    @PutMapping("/toggle")
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
