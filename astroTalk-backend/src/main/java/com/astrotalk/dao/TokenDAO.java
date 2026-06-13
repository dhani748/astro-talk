package com.astrotalk.dao;

import com.astrotalk.entity.Token;
import com.astrotalk.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenDAO {

    private final TokenRepository tokenRepository;

    public Token save(Token token) {
        log.debug("Saving token for email: {}", token.getEmail());
        return tokenRepository.save(token);
    }

    public Optional<Token> findByRefreshToken(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken);
    }

    public Optional<Token> findByAccessToken(String accessToken) {
        return tokenRepository.findByAccessToken(accessToken);
    }

    public List<Token> findByEmail(String email) {
        return tokenRepository.findByEmail(email);
    }

    public List<Token> findByEmailAndIsActive(String email, boolean isActive) {
        return tokenRepository.findByEmailAndIsActive(email, isActive);
    }

    public void delete(Token token) {
        log.debug("Deleting token with id: {}", token.getId());
        tokenRepository.delete(token);
    }

    public void deleteAll(List<Token> tokens) {
        log.debug("Deleting {} token(s)", tokens.size());
        tokenRepository.deleteAll(tokens);
    }
}
