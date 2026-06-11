package com.astrotalk.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class RevenueReportDTO {

    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalRevenue;
    private long totalConsultations;
}
