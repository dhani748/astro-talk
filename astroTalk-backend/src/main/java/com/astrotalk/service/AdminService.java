package com.astrotalk.service;

import com.astrotalk.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface AdminService {

    Page<UserResponse> getAllUsers(Pageable pageable, String search);

    Page<AstrologerResponse> getAllAstrologers(Pageable pageable, String status);

    AstrologerResponse verifyAstrologer(Long astrologerId);

    AstrologerResponse rejectAstrologer(Long astrologerId, String reason);

    DashboardStatsDTO getDashboardStats();

    RevenueReportDTO getRevenueReport(LocalDate startDate, LocalDate endDate);

    WalletResponse adjustWallet(WalletAdjustRequest request);
}
