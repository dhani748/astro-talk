package com.astrotalk.controller;

import com.astrotalk.constant.WebResource;
import com.astrotalk.model.AuthResponseModel;
import com.astrotalk.model.GoogleLoginRequestModel;
import com.astrotalk.model.LoginRequestModel;
import com.astrotalk.model.RefreshTokenModel;
import com.astrotalk.model.RegisterAstrologerRequestModel;
import com.astrotalk.model.RegisterRequestModel;
import com.astrotalk.service.AuthService;
import com.astrotalk.service.GoogleAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(WebResource.AUTH)
@Validated
@RequiredArgsConstructor
@Tags(value = {@Tag(name = "Auth Provider", description = "Auth Provider")})
public class AuthController {

    private final AuthService authService;
    private final GoogleAuthService googleAuthService;

    @PostMapping(WebResource.REGISTER)
    @Operation(summary = "Register a new user", description = "Creates a user account and returns JWT token with signup bonus")
    public ResponseEntity<AuthResponseModel> registerUser(@Valid @RequestBody RegisterRequestModel request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping(WebResource.REGISTER_ASTROLOGER)
    @Operation(summary = "Register a new astrologer", description = "Creates an astrologer account and returns JWT token")
    public ResponseEntity<AuthResponseModel> registerAstrologer(@Valid @RequestBody RegisterAstrologerRequestModel request) {
        return ResponseEntity.ok(authService.registerAstrologer(request));
    }

    @PostMapping(WebResource.LOGIN)
    @Operation(summary = "Login", description = "Authenticates user/astrologer and returns JWT token")
    public ResponseEntity<AuthResponseModel> login(@Valid @RequestBody LoginRequestModel request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(WebResource.REFRESH)
    @Operation(summary = "Refresh Token", description = "Generates a new access token using a valid refresh token")
    public ResponseEntity<AuthResponseModel> refreshToken(@Valid @RequestBody RefreshTokenModel request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PostMapping(WebResource.LOGOUT)
    @Operation(summary = "Logout", description = "Logs out the current user by invalidating the provided token")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        authService.logout(token);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(WebResource.FORGET)
    @Operation(summary = "Forget Password Request", description = "Initiates password reset process")
    public ResponseEntity<Void> forgetPassword(@RequestParam String email) {
        authService.forgetPassword(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping(WebResource.RESET_PASSWORD)
    @Operation(summary = "Reset Password", description = "Resets password using a valid token")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token,
            @RequestParam String password,
            @RequestParam String confirmPassword) {
        authService.resetPassword(token, password, confirmPassword);
        return ResponseEntity.ok("Password has been reset successfully.");
    }

    @PostMapping(WebResource.GOOGLE)
    @Operation(summary = "Google Sign-In", description = "Authenticates via Google ID token")
    public ResponseEntity<AuthResponseModel> googleLogin(@Valid @RequestBody GoogleLoginRequestModel request) {
        return ResponseEntity.ok(googleAuthService.authenticate(request));
    }
}
