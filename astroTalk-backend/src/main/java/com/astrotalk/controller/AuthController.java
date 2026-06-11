package com.astrotalk.controller;

import com.astrotalk.dto.*;
import com.astrotalk.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User and Astrologer registration & login")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a user account and returns JWT token with signup bonus")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/register/astrologer")
    @Operation(summary = "Register a new astrologer", description = "Creates an astrologer account and returns JWT token")
    public ResponseEntity<AuthResponse> registerAstrologer(@Valid @RequestBody RegisterAstrologerRequest request) {
        return ResponseEntity.ok(authService.registerAstrologer(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticates user/astrologer and returns JWT token")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
