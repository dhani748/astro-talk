package com.astrotalk.repository;

import com.astrotalk.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByRefreshToken(String refreshToken);

    Optional<Token> findByAccessToken(String accessToken);

    List<Token> findByEmail(String email);

    List<Token> findByEmailAndIsActive(String email, boolean isActive);
}
