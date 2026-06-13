package com.astrotalk.service.impl;

import com.astrotalk.model.PaymentVerifyRequestModel;
import com.astrotalk.model.RazorpayOrderRequestModel;
import com.astrotalk.model.RazorpayOrderResponseModel;
import com.astrotalk.model.WalletResponseModel;
import com.astrotalk.exception.ResourceNotFoundException;
import com.astrotalk.repository.UserRepository;
import com.astrotalk.service.PaymentService;
import com.astrotalk.service.WalletService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final RazorpayClient razorpayClient;
    private final WalletService walletService;
    private final UserRepository userRepository;

    @Value("${razorpay.key-id}")
    private String keyId;

    @Value("${razorpay.key-secret}")
    private String keySecret;

    @Override
    public RazorpayOrderResponseModel createOrder(RazorpayOrderRequestModel request, String email) {
        try {
            JSONObject orderRequest = new JSONObject();
            int amountInPaise = request.getAmount().multiply(BigDecimal.valueOf(100)).intValue();
            orderRequest.put("amount", amountInPaise);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

            Order order = razorpayClient.orders.create(orderRequest);

            return RazorpayOrderResponseModel.builder()
                    .orderId(order.get("id"))
                    .amount(request.getAmount())
                    .currency("INR")
                    .keyId(keyId)
                    .build();

        } catch (RazorpayException e) {
            throw new RuntimeException("Failed to create Razorpay order: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public WalletResponseModel verifyPayment(PaymentVerifyRequestModel request, String email) {
        String generatedSignature = generateSignature(
                request.getRazorpayOrderId() + "|" + request.getRazorpayPaymentId());

        if (!generatedSignature.equals(request.getRazorpaySignature())) {
            throw new RuntimeException("Invalid payment signature");
        }

        Long userId = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email))
                .getId();

        return walletService.addBalance(userId, BigDecimal.valueOf(100),
                "Payment via Razorpay (Order: " + request.getRazorpayOrderId() + ")");
    }

    private String generateSignature(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(keySecret.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hmacBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to generate payment signature", e);
        }
    }
}
