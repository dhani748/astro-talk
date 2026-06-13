package com.astrotalk.repository;

import com.astrotalk.entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {

    Optional<PaymentOrder> findByRazorpayOrderId(String razorpayOrderId);

    boolean existsByRazorpayOrderIdAndStatus(String razorpayOrderId, String status);
}
