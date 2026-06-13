package com.astrotalk.controller;

import com.astrotalk.constant.WebResource;
import com.astrotalk.entity.User;
import com.astrotalk.entity.WalletTransaction;
import com.astrotalk.exception.ResourceNotFoundException;
import com.astrotalk.model.AddMoneyRequestModel;
import com.astrotalk.model.WalletResponseModel;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for wallet operations.
 * Provides endpoints for checking balance, viewing transaction history, and adding funds.
 */
@RestController
@RequestMapping(WebResource.WALLET)
@Validated
@RequiredArgsConstructor
@Tag(name = "Wallet", description = "Wallet balance and transaction management")
public class WalletController {

    private final WalletService walletService;
    private final UserRepository userRepository;

    /**
     * GET /wallet/balance - Returns the current wallet balance for the authenticated user.
     *
     * @param authentication the current authenticated user
     * @return {@link WalletResponseModel} with balance and currency info
     */
    @GetMapping(WebResource.BALANCE)
    @Operation(summary = "Get wallet balance", description = "Returns current wallet balance for authenticated user")
    public ResponseEntity<WalletResponseModel> getBalance(Authentication authentication) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(walletService.getBalance(user.getUserId()));
    }

    /**
     * GET /wallet/transactions - Returns paginated wallet transaction history.
     *
     * @param authentication the current authenticated user
     * @param page           zero-based page index (default 0)
     * @param size           page size (default 10)
     * @return paginated list of {@link WalletTransaction}
     */
    @GetMapping(WebResource.TRANSACTIONS)
    @Operation(summary = "Get transaction history", description = "Returns paginated wallet transaction history")
    public ResponseEntity<Page<WalletTransaction>> getTransactions(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(walletService.getTransactionHistory(user.getUserId(), PageRequest.of(page, size)));
    }

    /**
     * POST /wallet/add - Credits the authenticated user's wallet with the specified amount.
     *
     * @param authentication the current authenticated user
     * @param request        the amount to add
     * @return updated {@link WalletResponseModel}
     */
    @PostMapping(WebResource.ADD)
    @Operation(summary = "Add money to wallet", description = "Credits the wallet with specified amount")
    public ResponseEntity<WalletResponseModel> addMoney(
            Authentication authentication,
            @Valid @RequestBody AddMoneyRequestModel request) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(walletService.addBalance(user.getUserId(), request.getAmount(), "Wallet top-up"));
    }

    private User getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }
}
