package com.astrotalk.service;

import com.astrotalk.model.WalletResponseModel;
import com.astrotalk.entity.WalletTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface WalletService {

    WalletResponseModel addBalance(Long userId, BigDecimal amount, String description);

    WalletResponseModel deductBalance(Long userId, BigDecimal amount, String description);

    WalletResponseModel getBalance(Long userId);

    Page<WalletTransaction> getTransactionHistory(Long userId, Pageable pageable);

    void giveSignupBonus(Long userId);
}
