package com.astrotalk.controller;

import com.astrotalk.dto.*;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin-only dashboard and management endpoints")
public class AdminController {

    private final AdminService adminService;
    private final ReviewService reviewService;

    @GetMapping("/dashboard")
    @Operation(summary = "Get dashboard stats", description = "Returns platform-wide statistics")
    public ResponseEntity<DashboardStatsDTO> getDashboard() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }

    @GetMapping("/users")
    @Operation(summary = "Get all users", description = "Returns paginated list of users with optional search")
    public ResponseEntity<Page<UserResponse>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(adminService.getAllUsers(PageRequest.of(page, size), search));
    }

    @GetMapping("/astrologers")
    @Operation(summary = "Get all astrologers", description = "Returns paginated list of astrologers with optional status filter")
    public ResponseEntity<Page<AstrologerResponse>> getAstrologers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(adminService.getAllAstrologers(PageRequest.of(page, size), status));
    }

    @PutMapping("/astrologers/{id}/verify")
    @Operation(summary = "Verify astrologer", description = "Approves astrologer verification and sends notification")
    public ResponseEntity<AstrologerResponse> verifyAstrologer(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.verifyAstrologer(id));
    }

    @PutMapping("/astrologers/{id}/reject")
    @Operation(summary = "Reject astrologer", description = "Rejects astrologer verification with reason")
    public ResponseEntity<AstrologerResponse> rejectAstrologer(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String reason = body.getOrDefault("reason", "No reason provided");
        return ResponseEntity.ok(adminService.rejectAstrologer(id, reason));
    }

    @GetMapping("/revenue")
    @Operation(summary = "Get revenue report", description = "Returns revenue data for a date range")
    public ResponseEntity<RevenueReportDTO> getRevenueReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(adminService.getRevenueReport(startDate, endDate));
    }

    @PostMapping("/wallet/adjust")
    @Operation(summary = "Adjust wallet balance", description = "Admin manually credits or debits a user's wallet")
    public ResponseEntity<WalletResponse> adjustWallet(@Valid @RequestBody WalletAdjustRequest request) {
        return ResponseEntity.ok(adminService.adjustWallet(request));
    }

    @DeleteMapping("/reviews/{id}")
    @Operation(summary = "Delete a review", description = "Admin deletes a review by ID")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
