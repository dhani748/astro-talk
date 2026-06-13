package com.astrotalk.service;

import com.astrotalk.model.PaymentVerifyRequestModel;
import com.astrotalk.model.RazorpayOrderRequestModel;
import com.astrotalk.model.RazorpayOrderResponseModel;
import com.astrotalk.model.WalletResponseModel;

public interface PaymentService {

    RazorpayOrderResponseModel createOrder(RazorpayOrderRequestModel request, String email);

    WalletResponseModel verifyPayment(PaymentVerifyRequestModel request, String email);
}
