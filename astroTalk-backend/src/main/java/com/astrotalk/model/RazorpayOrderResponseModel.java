package com.astrotalk.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RazorpayOrderResponseModel {
    private String orderId;
    private BigDecimal amount;
    private String currency;
    private String keyId;
}
