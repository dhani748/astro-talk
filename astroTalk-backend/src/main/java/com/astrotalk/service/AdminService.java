package com.astrotalk.service;

import com.astrotalk.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface AdminService {

    Page<UserResponseModel> getAllUsers(Pageable pageable, String search);

    Page<AstrologerResponseModel> getAllAstrologers(Pageable pageable, String status);

    AstrologerResponseModel verifyAstrologer(Long astrologerId);

    AstrologerResponseModel rejectAstrologer(Long astrologerId, String reason);

    DashboardStatsModel getDashboardStats();

    RevenueReportModel getRevenueReport(LocalDate startDate, LocalDate endDate);

    WalletResponseModel adjustWallet(WalletAdjustRequestModel request);
}
