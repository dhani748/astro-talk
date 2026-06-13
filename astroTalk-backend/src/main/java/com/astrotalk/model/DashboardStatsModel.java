package com.astrotalk.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DashboardStatsModel {
    private long totalUsers;
    private long totalAstrologers;
    private long activeConsultations;
    private BigDecimal todayRevenue;
    private BigDecimal totalRevenue;
    private long newUsersToday;
}
