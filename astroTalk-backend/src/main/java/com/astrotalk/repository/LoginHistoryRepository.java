package com.astrotalk.repository;

import com.astrotalk.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    Optional<LoginHistory> findTopByEmailOrderByLoginTimeDesc(String email);
}
