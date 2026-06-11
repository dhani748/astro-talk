package com.astrotalk.dto;

import com.astrotalk.entity.ConsultationStatus;
import com.astrotalk.entity.ConsultationType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ConsultationResponse {

    private Long id;
    private Long userId;
    private String userName;
    private Long astrologerId;
    private String astrologerName;
    private ConsultationType type;
    private ConsultationStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer durationMinutes;
    private BigDecimal totalAmount;
    private BigDecimal astrologerEarning;
    private BigDecimal platformCommission;
    private LocalDateTime createdAt;
}
