package com.astrotalk.service.impl;

import com.astrotalk.config.JwtUtil;
import com.astrotalk.dto.*;
import com.astrotalk.entity.Astrologer;
import com.astrotalk.entity.Role;
import com.astrotalk.entity.User;
import com.astrotalk.exception.DuplicateResourceException;
import com.astrotalk.exception.ResourceNotFoundException;
import com.astrotalk.repository.AstrologerRepository;
import com.astrotalk.repository.UserRepository;
import com.astrotalk.service.AuthService;
import com.astrotalk.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AstrologerRepository astrologerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final WalletService walletService;

    @Override
    public AuthResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
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
        if (astrologerRepository.existsByEmail(request.getEmail())) {
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
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user != null) {
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
            return AuthResponse.builder()
                    .token(token)
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();
        }

        Astrologer astrologer = astrologerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), astrologer.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(astrologer.getEmail(), astrologer.getRole().name());
        return AuthResponse.builder()
                .token(token)
                .id(astrologer.getId())
                .name(astrologer.getName())
                .email(astrologer.getEmail())
                .role(astrologer.getRole())
                .build();
    }
}
