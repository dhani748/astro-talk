package com.astrotalk.service;

import com.astrotalk.dto.AstrologerResponse;
import com.astrotalk.dto.RegisterAstrologerRequest;
import com.astrotalk.dto.UpdateAstrologerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface AstrologerService {

    AstrologerResponse registerAstrologer(RegisterAstrologerRequest request);

    AstrologerResponse getAstrologerById(Long id);

    AstrologerResponse getAstrologerByEmail(String email);

    AstrologerResponse updateAstrologer(Long id, UpdateAstrologerRequest request);

    void deleteAstrologer(Long id);

    List<AstrologerResponse> getAllAstrologers();

    List<AstrologerResponse> getAvailableAstrologers();

    List<AstrologerResponse> getAstrologersBySpecialization(String specialization);

    AstrologerResponse verifyAstrologer(Long id);

    Page<AstrologerResponse> searchAstrologers(String specialization, String language,
                                                BigDecimal minPrice, BigDecimal maxPrice,
                                                Double minRating, Boolean isOnline, Pageable pageable);

    List<AstrologerResponse> getTopAstrologers();

    AstrologerResponse getAstrologerProfile(Long id);
}
