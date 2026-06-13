package com.astrotalk.controller;

import com.astrotalk.constant.WebResource;
import com.astrotalk.model.AstrologerResponseModel;
import com.astrotalk.model.DashboardStatsModel;
import com.astrotalk.model.RevenueReportModel;
import com.astrotalk.model.UserResponseModel;
import com.astrotalk.model.WalletAdjustRequestModel;
import com.astrotalk.model.WalletResponseModel;
import com.astrotalk.service.AdminService;
import com.astrotalk.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

/**
 * REST controller for admin-only operations. Provides endpoints for dashboard
 * statistics, user/astrologer management, revenue reports, wallet adjustments,
 * and review moderation.
 */
@RestController
@RequestMapping(WebResource.ADMIN)
@Validated
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin-only dashboard and management endpoints")
public class AdminController {

    private final AdminService adminService;
    private final ReviewService reviewService;

    /**
     * GET /admin/dashboard - Returns platform-wide dashboard statistics.
     *
     * @return {@link DashboardStatsModel} containing aggregate platform metrics
     */
    @GetMapping(WebResource.DASHBOARD)
    @Operation(summary = "Get dashboard stats", description = "Returns platform-wide statistics")
    public ResponseEntity<DashboardStatsModel> getDashboard() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }

    /**
     * GET /admin/users - Returns a paginated list of all users with optional search filter.
     *
     * @param page   zero-based page index (default 0)
     * @param size   page size (default 20)
     * @param search optional keyword to filter users by name or email
     * @return paginated {@link UserResponseModel} list
     */
    @GetMapping(WebResource.USERS_ADMIN)
    @Operation(summary = "Get all users", description = "Returns paginated list of users with optional search")
    public ResponseEntity<Page<UserResponseModel>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(adminService.getAllUsers(PageRequest.of(page, size), search));
    }

    /**
     * GET /admin/astrologers - Returns a paginated list of astrologers with optional status filter.
     *
     * @param page   zero-based page index (default 0)
     * @param size   page size (default 20)
     * @param status optional status filter (e.g. PENDING, VERIFIED, REJECTED)
     * @return paginated {@link AstrologerResponseModel} list
     */
    @GetMapping(WebResource.ASTROLOGERS_ADMIN)
    @Operation(summary = "Get all astrologers", description = "Returns paginated list of astrologers with optional status filter")
    public ResponseEntity<Page<AstrologerResponseModel>> getAstrologers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(adminService.getAllAstrologers(PageRequest.of(page, size), status));
    }

    /**
     * PUT /admin/astrologers/{id}/verify - Approves an astrologer's verification request.
     *
     * @param id the astrologer's ID
     * @return the updated {@link AstrologerResponseModel}
     */
    @PutMapping(WebResource.VERIFY_ASTROLOGER)
    @Operation(summary = "Verify astrologer", description = "Approves astrologer verification and sends notification")
    public ResponseEntity<AstrologerResponseModel> verifyAstrologer(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.verifyAstrologer(id));
    }

    /**
     * PUT /admin/astrologers/{id}/reject - Rejects an astrologer's verification with a reason.
     *
     * @param id   the astrologer's ID
     * @param body map containing the rejection reason
     * @return the updated {@link AstrologerResponseModel}
     */
    @PutMapping(WebResource.REJECT_ASTROLOGER)
    @Operation(summary = "Reject astrologer", description = "Rejects astrologer verification with reason")
    public ResponseEntity<AstrologerResponseModel> rejectAstrologer(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String reason = body.getOrDefault("reason", "No reason provided");
        return ResponseEntity.ok(adminService.rejectAstrologer(id, reason));
    }

    /**
     * GET /admin/revenue - Returns aggregated revenue data for a given date range.
     *
     * @param startDate inclusive start date (ISO format)
     * @param endDate   inclusive end date (ISO format)
     * @return {@link RevenueReportModel} with revenue breakdown
     */
    @GetMapping(WebResource.REVENUE)
    @Operation(summary = "Get revenue report", description = "Returns revenue data for a date range")
    public ResponseEntity<RevenueReportModel> getRevenueReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(adminService.getRevenueReport(startDate, endDate));
    }

    /**
     * POST /admin/wallet/adjust - Manually credits or debits a user's wallet (admin only).
     *
     * @param request the wallet adjustment details
     * @return updated {@link WalletResponseModel}
     */
    @PostMapping(WebResource.ADJUST)
    @Operation(summary = "Adjust wallet balance", description = "Admin manually credits or debits a user's wallet")
    public ResponseEntity<WalletResponseModel> adjustWallet(@Valid @RequestBody WalletAdjustRequestModel request) {
        return ResponseEntity.ok(adminService.adjustWallet(request));
    }

    /**
     * DELETE /admin/reviews/{id} - Deletes a review by ID (admin only).
     *
     * @param id the review ID to delete
     * @return 204 No Content
     */
    @DeleteMapping(WebResource.REVIEWS_ADMIN)
    @Operation(summary = "Delete a review", description = "Admin deletes a review by ID")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
