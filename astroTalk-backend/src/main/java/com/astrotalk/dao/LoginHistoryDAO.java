package com.astrotalk.dao;

import com.astrotalk.entity.LoginHistory;
import com.astrotalk.repository.LoginHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginHistoryDAO {

    private final LoginHistoryRepository loginHistoryRepository;

    public void save(LoginHistory loginHistory) {
        log.debug("Saving login history for email: {}", loginHistory.getEmail());
        loginHistoryRepository.save(loginHistory);
    }

    public Optional<LoginHistory> getLastLoginByEmail(String email) {
        return loginHistoryRepository.findTopByEmailOrderByLoginTimeDesc(email);
    }
}
