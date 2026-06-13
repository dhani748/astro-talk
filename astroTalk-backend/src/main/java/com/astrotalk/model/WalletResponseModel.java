package com.astrotalk.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletResponseModel {
    private Long userId;
    private BigDecimal balance;
}
