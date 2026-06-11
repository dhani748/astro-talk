package com.astrotalk.service.impl;

import com.astrotalk.dto.AstrologerResponse;
import com.astrotalk.dto.RegisterAstrologerRequest;
import com.astrotalk.dto.UpdateAstrologerRequest;
import com.astrotalk.entity.Astrologer;
import com.astrotalk.entity.Role;
import com.astrotalk.exception.DuplicateResourceException;
import com.astrotalk.exception.ResourceNotFoundException;
import com.astrotalk.repository.AstrologerRepository;
import com.astrotalk.service.AstrologerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AstrologerServiceImpl implements AstrologerService {

    private final AstrologerRepository astrologerRepository;

    @Override
    public AstrologerResponse registerAstrologer(RegisterAstrologerRequest request) {
        if (astrologerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Astrologer", "email", request.getEmail());
        }

        Astrologer astrologer = Astrologer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
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
        return toAstrologerResponse(astrologer);
    }

    @Override
    public AstrologerResponse getAstrologerById(Long id) {
        Astrologer astrologer = astrologerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Astrologer", id));
        return toAstrologerResponse(astrologer);
    }

    @Override
    public AstrologerResponse getAstrologerByEmail(String email) {
        Astrologer astrologer = astrologerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Astrologer", "email", email));
        return toAstrologerResponse(astrologer);
    }

    @Override
    public AstrologerResponse updateAstrologer(Long id, UpdateAstrologerRequest request) {
        Astrologer astrologer = astrologerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Astrologer", id));

        if (request.getName() != null) {
            astrologer.setName(request.getName());
        }
        if (request.getPhone() != null) {
            astrologer.setPhone(request.getPhone());
        }
        if (request.getProfilePicture() != null) {
            astrologer.setProfilePicture(request.getProfilePicture());
        }
        if (request.getBio() != null) {
            astrologer.setBio(request.getBio());
        }
        if (request.getSpecialization() != null) {
            astrologer.setSpecialization(request.getSpecialization());
        }
        if (request.getYearsOfExperience() != null) {
            astrologer.setYearsOfExperience(request.getYearsOfExperience());
        }
        if (request.getLanguages() != null) {
            astrologer.setLanguages(request.getLanguages());
        }
        if (request.getConsultationFee() != null) {
            astrologer.setConsultationFee(request.getConsultationFee());
        }
        if (request.getIsAvailable() != null) {
            astrologer.setAvailable(request.getIsAvailable());
        }

        astrologer = astrologerRepository.save(astrologer);
        return toAstrologerResponse(astrologer);
    }

    @Override
    public void deleteAstrologer(Long id) {
        if (!astrologerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Astrologer", id);
        }
        astrologerRepository.deleteById(id);
    }

    @Override
    public List<AstrologerResponse> getAllAstrologers() {
        return astrologerRepository.findAll().stream()
                .map(this::toAstrologerResponse)
                .toList();
    }

    @Override
    public List<AstrologerResponse> getAvailableAstrologers() {
        return astrologerRepository.findByIsAvailable(true).stream()
                .map(this::toAstrologerResponse)
                .toList();
    }

    @Override
    public List<AstrologerResponse> getAstrologersBySpecialization(String specialization) {
        return astrologerRepository.findBySpecialization(specialization).stream()
                .map(this::toAstrologerResponse)
                .toList();
    }

    @Override
    public AstrologerResponse verifyAstrologer(Long id) {
        Astrologer astrologer = astrologerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Astrologer", id));
        astrologer.setVerified(true);
        astrologer = astrologerRepository.save(astrologer);
        return toAstrologerResponse(astrologer);
    }

    @Override
    public Page<AstrologerResponse> searchAstrologers(String specialization, String language,
                                                       BigDecimal minPrice, BigDecimal maxPrice,
                                                       Double minRating, Boolean isOnline, Pageable pageable) {
        return astrologerRepository.searchAstrologers(specialization, language, minPrice, maxPrice, minRating, isOnline, pageable)
                .map(this::toAstrologerResponse);
    }

    @Override
    public List<AstrologerResponse> getTopAstrologers() {
        return astrologerRepository.findTop6ByOrderByRatingDesc().stream()
                .map(this::toAstrologerResponse)
                .toList();
    }

    @Override
    public AstrologerResponse getAstrologerProfile(Long id) {
        return getAstrologerById(id);
    }

    private AstrologerResponse toAstrologerResponse(Astrologer astrologer) {
        return AstrologerResponse.builder()
                .id(astrologer.getId())
                .name(astrologer.getName())
                .email(astrologer.getEmail())
                .phone(astrologer.getPhone())
                .profilePicture(astrologer.getProfilePicture())
                .bio(astrologer.getBio())
                .specialization(astrologer.getSpecialization())
                .yearsOfExperience(astrologer.getYearsOfExperience())
                .languages(astrologer.getLanguages())
                .rating(astrologer.getRating())
                .totalConsultations(astrologer.getTotalConsultations())
                .isAvailable(astrologer.isAvailable())
                .consultationFee(astrologer.getConsultationFee())
                .role(astrologer.getRole())
                .isVerified(astrologer.isVerified())
                .createdAt(astrologer.getCreatedAt())
                .updatedAt(astrologer.getUpdatedAt())
                .build();
    }
}
