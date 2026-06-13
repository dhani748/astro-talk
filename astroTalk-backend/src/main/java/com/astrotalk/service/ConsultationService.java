package com.astrotalk.service;

import com.astrotalk.model.ConsultationResponseModel;
import com.astrotalk.model.StartConsultationRequestModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultationService {

    ConsultationResponseModel startConsultation(Long userId, StartConsultationRequestModel request);

    ConsultationResponseModel endConsultation(Long consultationId);

    ConsultationResponseModel getActiveConsultation(Long userId);

    Page<ConsultationResponseModel> getConsultationHistory(Long userId, Pageable pageable);

    ConsultationResponseModel getConsultationById(Long id);
}
