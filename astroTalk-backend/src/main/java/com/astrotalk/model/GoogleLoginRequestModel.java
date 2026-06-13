package com.astrotalk.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GoogleLoginRequestModel {
    @NotBlank(message = "Google ID token is required")
    private String idToken;
}
