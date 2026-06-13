package com.astrotalk.service;

import com.astrotalk.model.AstrologerResponseModel;
import com.astrotalk.model.RegisterAstrologerRequestModel;
import com.astrotalk.model.UpdateAstrologerRequestModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface AstrologerService {

    AstrologerResponseModel registerAstrologer(RegisterAstrologerRequestModel request);

    AstrologerResponseModel getAstrologerById(Long id);

    AstrologerResponseModel getAstrologerByEmail(String email);

    AstrologerResponseModel updateAstrologer(Long id, UpdateAstrologerRequestModel request);

    void deleteAstrologer(Long id);

    List<AstrologerResponseModel> getAllAstrologers();

    List<AstrologerResponseModel> getAvailableAstrologers();

    List<AstrologerResponseModel> getAstrologersBySpecialization(String specialization);

    AstrologerResponseModel verifyAstrologer(Long id);

    Page<AstrologerResponseModel> searchAstrologers(String specialization, String language,
                                                     BigDecimal minPrice, BigDecimal maxPrice,
                                                     Double minRating, Boolean isOnline, Pageable pageable);

    List<AstrologerResponseModel> getTopAstrologers();

    AstrologerResponseModel getAstrologerProfile(Long id);
}
