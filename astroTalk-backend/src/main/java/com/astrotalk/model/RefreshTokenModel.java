package com.astrotalk.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenModel {
    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}
