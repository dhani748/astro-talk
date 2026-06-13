package com.astrotalk.model;

import com.astrotalk.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AstrologerResponseModel {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String profilePicture;
    private String bio;
    private String specialization;
    private Integer yearsOfExperience;
    private String languages;
    private Double rating;
    private Integer totalConsultations;
    private boolean isAvailable;
    private BigDecimal consultationFee;
    private Role role;
    private boolean isVerified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
