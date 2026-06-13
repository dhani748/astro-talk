package com.astrotalk.service.impl;

import com.astrotalk.model.*;
import com.astrotalk.entity.*;
import com.astrotalk.exception.ResourceNotFoundException;
import com.astrotalk.repository.*;
import com.astrotalk.service.AdminService;
import com.astrotalk.service.NotificationService;
import com.astrotalk.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final AstrologerRepository astrologerRepository;
    private final ConsultationRepository consultationRepository;
    private final WalletService walletService;
    private final NotificationService notificationService;

    @Override
    public Page<UserResponseModel> getAllUsers(Pageable pageable, String search) {
        if (search != null && !search.isBlank()) {
            return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search, pageable)
                    .map(this::toUserResponse);
        }
        return userRepository.findAll(pageable).map(this::toUserResponse);
    }

    @Override
    public Page<AstrologerResponseModel> getAllAstrologers(Pageable pageable, String status) {
        if (status != null && !status.isBlank()) {
            return astrologerRepository.findByStatus(AstrologerStatus.valueOf(status.toUpperCase()), pageable)
                    .map(this::toAstrologerResponse);
        }
        return astrologerRepository.findAll(pageable).map(this::toAstrologerResponse);
    }

    @Override
    @Transactional
    public AstrologerResponseModel verifyAstrologer(Long astrologerId) {
        Astrologer astrologer = astrologerRepository.findById(astrologerId)
                .orElseThrow(() -> new ResourceNotFoundException("Astrologer", astrologerId));
        astrologer.setVerified(true);
        astrologer.setStatus(AstrologerStatus.ONLINE);
        astrologer = astrologerRepository.save(astrologer);

        notificationService.sendNotification(astrologerId, "Verification Approved",
                "Congratulations! Your profile has been verified and you are now online.",
                NotificationType.VERIFICATION);

        return toAstrologerResponse(astrologer);
    }

    @Override
    @Transactional
    public AstrologerResponseModel rejectAstrologer(Long astrologerId, String reason) {
        if (!astrologerRepository.existsById(astrologerId)) {
            throw new ResourceNotFoundException("Astrologer", astrologerId);
        }

        notificationService.sendNotification(astrologerId, "Verification Rejected",
                "Your verification was rejected. Reason: " + reason,
                NotificationType.VERIFICATION);

        return toAstrologerResponse(astrologerRepository.findById(astrologerId).orElseThrow());
    }

    @Override
    public DashboardStatsModel getDashboardStats() {
        long totalUsers = userRepository.count();
        long totalAstrologers = astrologerRepository.count();
        long activeConsultations = consultationRepository.countByStatus(ConsultationStatus.ACTIVE);
        BigDecimal totalRevenue = consultationRepository.getTotalRevenue();

        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime todayEnd = LocalDate.now().atTime(LocalTime.MAX);
        BigDecimal todayRevenue = consultationRepository.getRevenueBetween(todayStart, todayEnd);
        long newUsersToday = userRepository.countByCreatedAtAfter(todayStart);

        return DashboardStatsModel.builder()
                .totalUsers(totalUsers)
                .totalAstrologers(totalAstrologers)
                .activeConsultations(activeConsultations)
                .todayRevenue(todayRevenue)
                .totalRevenue(totalRevenue)
                .newUsersToday(newUsersToday)
                .build();
    }

    @Override
    public RevenueReportModel getRevenueReport(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        BigDecimal revenue = consultationRepository.getRevenueBetween(start, end);
        long count = consultationRepository.countByCreatedAtAfter(start);

        return RevenueReportModel.builder()
                .startDate(startDate)
                .endDate(endDate)
                .totalRevenue(revenue)
                .totalConsultations(count)
                .build();
    }

    @Override
    @Transactional
    public WalletResponseModel adjustWallet(WalletAdjustRequestModel request) {
        if (request.isCredit()) {
            return walletService.addBalance(request.getUserId(), request.getAmount(),
                    "Admin credit: " + request.getReason());
        } else {
            return walletService.deductBalance(request.getUserId(), request.getAmount(),
                    "Admin debit: " + request.getReason());
        }
    }

    private UserResponseModel toUserResponse(User user) {
        return UserResponseModel.builder()
                .id(user.getId()).name(user.getName()).email(user.getEmail())
                .phone(user.getPhone()).dateOfBirth(user.getDateOfBirth())
                .timeOfBirth(user.getTimeOfBirth()).placeOfBirth(user.getPlaceOfBirth())
                .latitude(user.getLatitude()).longitude(user.getLongitude())
                .gender(user.getGender()).profilePicture(user.getProfilePicture())
                .role(user.getRole()).emailVerified(user.isEmailVerified())
                .active(user.isActive()).createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt()).build();
    }

    private AstrologerResponseModel toAstrologerResponse(Astrologer a) {
        return AstrologerResponseModel.builder()
                .id(a.getId()).name(a.getName()).email(a.getEmail()).phone(a.getPhone())
                .profilePicture(a.getProfilePicture()).bio(a.getBio())
                .specialization(a.getSpecialization()).yearsOfExperience(a.getYearsOfExperience())
                .languages(a.getLanguages()).rating(a.getRating())
                .totalConsultations(a.getTotalConsultations()).isAvailable(a.isAvailable())
                .consultationFee(a.getConsultationFee()).role(a.getRole()).isVerified(a.isVerified())
                .createdAt(a.getCreatedAt()).updatedAt(a.getUpdatedAt()).build();
    }
}
