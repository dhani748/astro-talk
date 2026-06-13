package com.astrotalk.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletAdjustRequestModel {
    @NotNull(message = "User ID is required")
    private Long userId;
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;
    @NotBlank(message = "Reason is required")
    private String reason;
    private boolean credit;
}
