package com.astrotalk.controller;

import com.astrotalk.constant.WebResource;
import com.astrotalk.model.PaymentVerifyRequestModel;
import com.astrotalk.model.RazorpayOrderRequestModel;
import com.astrotalk.model.RazorpayOrderResponseModel;
import com.astrotalk.model.WalletResponseModel;
import com.astrotalk.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for Razorpay payment integration.
 * Handles creating payment orders and verifying payment signatures.
 */
@RestController
@RequestMapping(WebResource.PAYMENTS)
@Validated
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Razorpay payment integration")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * POST /payments/create-order - Creates a Razorpay payment order for wallet top-up.
     *
     * @param authentication the current authenticated user
     * @param request        the order details (amount, currency)
     * @return {@link RazorpayOrderResponseModel} with order ID and payment options
     */
    @PostMapping(WebResource.CREATE_ORDER)
    @Operation(summary = "Create Razorpay order", description = "Creates a payment order via Razorpay")
    public ResponseEntity<RazorpayOrderResponseModel> createOrder(
            Authentication authentication,
            @Valid @RequestBody RazorpayOrderRequestModel request) {
        return ResponseEntity.ok(paymentService.createOrder(request, authentication.getName()));
    }

    /**
     * POST /payments/verify - Verifies a Razorpay payment signature and credits the user's wallet.
     *
     * @param authentication the current authenticated user
     * @param request        the payment verification details (Razorpay order ID, payment ID, signature)
     * @return updated {@link WalletResponseModel}
     */
    @PostMapping(WebResource.VERIFY)
    @Operation(summary = "Verify payment", description = "Verifies Razorpay payment signature and credits wallet")
    public ResponseEntity<WalletResponseModel> verifyPayment(
            Authentication authentication,
            @Valid @RequestBody PaymentVerifyRequestModel request) {
        return ResponseEntity.ok(paymentService.verifyPayment(request, authentication.getName()));
    }
}
