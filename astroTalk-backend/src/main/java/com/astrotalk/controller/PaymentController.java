package com.astrotalk.controller;

import com.astrotalk.dto.PaymentVerifyRequest;
import com.astrotalk.dto.RazorpayOrderRequest;
import com.astrotalk.dto.RazorpayOrderResponse;
import com.astrotalk.dto.WalletResponse;
import com.astrotalk.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Razorpay payment integration")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-order")
    @Operation(summary = "Create Razorpay order", description = "Creates a payment order via Razorpay")
    public ResponseEntity<RazorpayOrderResponse> createOrder(
            Authentication authentication,
            @Valid @RequestBody RazorpayOrderRequest request) {
        return ResponseEntity.ok(paymentService.createOrder(request, authentication.getName()));
    }

    @PostMapping("/verify")
    @Operation(summary = "Verify payment", description = "Verifies Razorpay payment signature and credits wallet")
    public ResponseEntity<WalletResponse> verifyPayment(
            Authentication authentication,
            @Valid @RequestBody PaymentVerifyRequest request) {
        return ResponseEntity.ok(paymentService.verifyPayment(request, authentication.getName()));
    }
}
