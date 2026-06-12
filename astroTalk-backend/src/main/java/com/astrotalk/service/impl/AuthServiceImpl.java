package com.astrotalk.service.impl;

import com.astrotalk.config.JwtUtil;
import com.astrotalk.dto.*;
import com.astrotalk.entity.Astrologer;
import com.astrotalk.entity.Role;
import com.astrotalk.entity.User;
import com.astrotalk.exception.DuplicateResourceException;
import com.astrotalk.exception.EmailNotFoundException;
import com.astrotalk.exception.InvalidCredentialsException;
import com.astrotalk.repository.AstrologerRepository;
import com.astrotalk.repository.UserRepository;
import com.astrotalk.service.AuthService;
import com.astrotalk.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final AstrologerRepository astrologerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final WalletService walletService;

    @Override
    public AuthResponse registerUser(RegisterRequest request) {
        log.info("Registering user with email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Duplicate registration attempt for email: {}", request.getEmail());
            throw new DuplicateResourceException("User", "email", request.getEmail());
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .timeOfBirth(request.getTimeOfBirth())
                .placeOfBirth(request.getPlaceOfBirth())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .gender(request.getGender())
                .role(Role.USER)
                .active(true)
                .emailVerified(false)
                .build();

        user = userRepository.save(user);
        walletService.giveSignupBonus(user.getId());
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        log.info("User registered successfully: {} (id={})", user.getEmail(), user.getId());
        return AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public AuthResponse registerAstrologer(RegisterAstrologerRequest request) {
        log.info("Registering astrologer with email: {}", request.getEmail());

        if (astrologerRepository.existsByEmail(request.getEmail())) {
            log.warn("Duplicate astrologer registration attempt for email: {}", request.getEmail());
            throw new DuplicateResourceException("Astrologer", "email", request.getEmail());
        }

        Astrologer astrologer = Astrologer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .bio(request.getBio())
                .specialization(request.getSpecialization())
                .yearsOfExperience(request.getYearsOfExperience())
                .languages(request.getLanguages())
                .consultationFee(request.getConsultationFee())
                .role(Role.ASTROLOGER)
                .rating(0.0)
                .totalConsultations(0)
                .isAvailable(true)
                .isVerified(false)
                .build();

        astrologer = astrologerRepository.save(astrologer);
        String token = jwtUtil.generateToken(astrologer.getEmail(), astrologer.getRole().name());

        log.info("Astrologer registered successfully: {} (id={})", astrologer.getEmail(), astrologer.getId());
        return AuthResponse.builder()
                .token(token)
                .id(astrologer.getId())
                .name(astrologer.getName())
                .email(astrologer.getEmail())
                .role(astrologer.getRole())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user != null) {
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                log.warn("Incorrect password for user email: {}", request.getEmail());
                throw new InvalidCredentialsException("Incorrect password. Please try again.");
            }
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
            log.info("User login successful: {} (role=USER)", user.getEmail());
            return AuthResponse.builder()
                    .token(token)
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();
        }

        Astrologer astrologer = astrologerRepository.findByEmail(request.getEmail()).orElse(null);

        if (astrologer == null) {
            log.warn("No account found for email: {}", request.getEmail());
            throw new EmailNotFoundException("No account found with this email.");
        }

        if (!passwordEncoder.matches(request.getPassword(), astrologer.getPassword())) {
            log.warn("Incorrect password for astrologer email: {}", request.getEmail());
            throw new InvalidCredentialsException("Incorrect password. Please try again.");
        }

        String token = jwtUtil.generateToken(astrologer.getEmail(), astrologer.getRole().name());
        log.info("Astrologer login successful: {} (role=ASTROLOGER)", astrologer.getEmail());
        return AuthResponse.builder()
                .token(token)
                .id(astrologer.getId())
                .name(astrologer.getName())
                .email(astrologer.getEmail())
                .role(astrologer.getRole())
                .build();
    }
}
