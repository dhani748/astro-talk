package com.astrotalk.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletResponse {

    private Long userId;
    private BigDecimal balance;
}
