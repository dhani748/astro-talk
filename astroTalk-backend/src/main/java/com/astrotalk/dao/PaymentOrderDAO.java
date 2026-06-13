package com.astrotalk.dao;

import com.astrotalk.entity.PaymentOrder;
import com.astrotalk.repository.PaymentOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentOrderDAO {

    private final PaymentOrderRepository paymentOrderRepository;

    public PaymentOrder save(PaymentOrder paymentOrder) {
        log.debug("Saving payment order: {}", paymentOrder.getRazorpayOrderId());
        return paymentOrderRepository.save(paymentOrder);
    }

    public Optional<PaymentOrder> findByRazorpayOrderId(String razorpayOrderId) {
        return paymentOrderRepository.findByRazorpayOrderId(razorpayOrderId);
    }

    public boolean isAlreadyPaid(String razorpayOrderId) {
        return paymentOrderRepository.existsByRazorpayOrderIdAndStatus(razorpayOrderId, "paid");
    }
}
