package com.astrotalk.repository;

import com.astrotalk.entity.User;
import com.astrotalk.entity.WalletTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

    Page<WalletTransaction> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
