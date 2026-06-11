package com.astrotalk.service;

import com.astrotalk.dto.PaymentVerifyRequest;
import com.astrotalk.dto.RazorpayOrderRequest;
import com.astrotalk.dto.RazorpayOrderResponse;
import com.astrotalk.dto.WalletResponse;

public interface PaymentService {

    RazorpayOrderResponse createOrder(RazorpayOrderRequest request, String email);

    WalletResponse verifyPayment(PaymentVerifyRequest request, String email);
}
