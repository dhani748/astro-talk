package com.astrotalk.service.impl;

import com.astrotalk.dto.WalletResponse;
import com.astrotalk.entity.User;
import com.astrotalk.entity.WalletTransaction;
import com.astrotalk.entity.WalletTransactionType;
import com.astrotalk.exception.InsufficientBalanceException;
import com.astrotalk.exception.ResourceNotFoundException;
import com.astrotalk.repository.UserRepository;
import com.astrotalk.repository.WalletTransactionRepository;
import com.astrotalk.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final UserRepository userRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    @Override
    @Transactional
    public WalletResponse addBalance(Long userId, BigDecimal amount, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        user.setWalletBalance(user.getWalletBalance().add(amount));
        user = userRepository.save(user);

        walletTransactionRepository.save(WalletTransaction.builder()
                .user(user)
                .type(WalletTransactionType.CREDIT)
                .amount(amount)
                .description(description)
                .build());

        return WalletResponse.builder()
                .userId(user.getId())
                .balance(user.getWalletBalance())
                .build();
    }

    @Override
    @Transactional
    public WalletResponse deductBalance(Long userId, BigDecimal amount, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        if (user.getWalletBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException(
                    "Insufficient balance. Available: " + user.getWalletBalance() + ", Required: " + amount);
        }

        user.setWalletBalance(user.getWalletBalance().subtract(amount));
        user = userRepository.save(user);

        walletTransactionRepository.save(WalletTransaction.builder()
                .user(user)
                .type(WalletTransactionType.DEBIT)
                .amount(amount)
                .description(description)
                .build());

        return WalletResponse.builder()
                .userId(user.getId())
                .balance(user.getWalletBalance())
                .build();
    }

    @Override
    public WalletResponse getBalance(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        return WalletResponse.builder()
                .userId(user.getId())
                .balance(user.getWalletBalance())
                .build();
    }

    @Override
    public Page<WalletTransaction> getTransactionHistory(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        return walletTransactionRepository.findByUserOrderByCreatedAtDesc(user, pageable);
    }

    @Override
    @Transactional
    public void giveSignupBonus(Long userId) {
        addBalance(userId, new BigDecimal("50.00"), "Signup bonus");
    }
}
