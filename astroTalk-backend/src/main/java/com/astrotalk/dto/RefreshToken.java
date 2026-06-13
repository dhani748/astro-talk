package com.astrotalk.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshToken {

    @NotBlank(message = "Refresh token is required")
    private String refreshToken;
}
