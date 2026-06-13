package com.astrotalk.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateAstrologerRequestModel {
    private String name;
    private String phone;
    private String profilePicture;
    private String bio;
    private String specialization;
    private Integer yearsOfExperience;
    private String languages;
    private BigDecimal consultationFee;
    private Boolean isAvailable;
}
