package com.astrotalk.controller;

import com.astrotalk.dto.ConsultationResponse;
import com.astrotalk.dto.StartConsultationRequest;
import com.astrotalk.entity.User;
import com.astrotalk.exception.ResourceNotFoundException;
import com.astrotalk.repository.UserRepository;
import com.astrotalk.service.ConsultationService;
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
@RequestMapping("/api/consultations")
@RequiredArgsConstructor
@Tag(name = "Consultations", description = "Start, end, and manage consultations")
public class ConsultationController {

    private final ConsultationService consultationService;
    private final UserRepository userRepository;

    @PostMapping("/start")
    @Operation(summary = "Start a consultation", description = "Initiates a consultation with an astrologer after wallet check")
    public ResponseEntity<ConsultationResponse> startConsultation(
            Authentication authentication,
            @Valid @RequestBody StartConsultationRequest request) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(consultationService.startConsultation(user.getId(), request));
    }

    @PostMapping("/end/{id}")
    @Operation(summary = "End a consultation", description = "Completes an active consultation and processes final billing")
    public ResponseEntity<ConsultationResponse> endConsultation(@PathVariable Long id) {
        return ResponseEntity.ok(consultationService.endConsultation(id));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active consultation", description = "Returns the currently active consultation for the user")
    public ResponseEntity<ConsultationResponse> getActiveConsultation(Authentication authentication) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(consultationService.getActiveConsultation(user.getId()));
    }

    @GetMapping("/history")
    @Operation(summary = "Get consultation history", description = "Returns paginated consultation history for the user")
    public ResponseEntity<Page<ConsultationResponse>> getHistory(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(consultationService.getConsultationHistory(user.getId(), PageRequest.of(page, size)));
    }

    private User getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }
}
