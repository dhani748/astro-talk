package com.astrotalk.service;

import com.astrotalk.dto.ConsultationResponse;
import com.astrotalk.dto.StartConsultationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultationService {

    ConsultationResponse startConsultation(Long userId, StartConsultationRequest request);

    ConsultationResponse endConsultation(Long consultationId);

    ConsultationResponse getActiveConsultation(Long userId);

    Page<ConsultationResponse> getConsultationHistory(Long userId, Pageable pageable);
}
