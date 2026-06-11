package com.astrotalk.dto;

import com.astrotalk.entity.ConsultationType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StartConsultationRequest {

    @NotNull(message = "Astrologer ID is required")
    private Long astrologerId;

    @NotNull(message = "Consultation type is required")
    private ConsultationType type;
}
