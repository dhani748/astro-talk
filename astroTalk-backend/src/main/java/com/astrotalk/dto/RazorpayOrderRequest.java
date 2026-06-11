package com.astrotalk.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RazorpayOrderRequest {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1", message = "Amount must be at least 1")
    private BigDecimal amount;
}
