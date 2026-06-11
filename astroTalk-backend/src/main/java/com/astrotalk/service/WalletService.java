package com.astrotalk.service;

import com.astrotalk.dto.WalletResponse;
import com.astrotalk.entity.WalletTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface WalletService {

    WalletResponse addBalance(Long userId, BigDecimal amount, String description);

    WalletResponse deductBalance(Long userId, BigDecimal amount, String description);

    WalletResponse getBalance(Long userId);

    Page<WalletTransaction> getTransactionHistory(Long userId, Pageable pageable);

    void giveSignupBonus(Long userId);
}
