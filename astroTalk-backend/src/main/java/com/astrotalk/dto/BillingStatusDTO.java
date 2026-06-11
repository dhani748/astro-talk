package com.astrotalk.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BillingStatusDTO {

    private Long consultationId;
    private String userEmail;
    private String astrologerName;
    private int durationMinutes;
    private BigDecimal amountDeducted;
    private BigDecimal currentBalance;
    private boolean active;
}
