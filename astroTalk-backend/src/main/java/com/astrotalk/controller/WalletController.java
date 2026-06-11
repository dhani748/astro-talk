package com.astrotalk.controller;

import com.astrotalk.dto.AddMoneyRequest;
import com.astrotalk.dto.WalletResponse;
import com.astrotalk.entity.User;
import com.astrotalk.entity.WalletTransaction;
import com.astrotalk.exception.ResourceNotFoundException;
import com.astrotalk.repository.UserRepository;
import com.astrotalk.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
@Tag(name = "Wallet", description = "Wallet balance and transaction management")
public class WalletController {

    private final WalletService walletService;
    private final UserRepository userRepository;

    @GetMapping("/balance")
    @Operation(summary = "Get wallet balance", description = "Returns current wallet balance for authenticated user")
    public ResponseEntity<WalletResponse> getBalance(Authentication authentication) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(walletService.getBalance(user.getId()));
    }

    @GetMapping("/transactions")
    @Operation(summary = "Get transaction history", description = "Returns paginated wallet transaction history")
    public ResponseEntity<Page<WalletTransaction>> getTransactions(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(walletService.getTransactionHistory(user.getId(), PageRequest.of(page, size)));
    }

    @PostMapping("/add")
    @Operation(summary = "Add money to wallet", description = "Credits the wallet with specified amount")
    public ResponseEntity<WalletResponse> addMoney(
            Authentication authentication,
            @Valid @RequestBody AddMoneyRequest request) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(walletService.addBalance(user.getId(), request.getAmount(), "Wallet top-up"));
    }

    private User getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }
}
