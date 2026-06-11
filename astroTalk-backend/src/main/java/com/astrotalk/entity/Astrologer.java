package com.astrotalk.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "astrologers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Astrologer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phone;

    private String profilePicture;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String specialization;

    private Integer yearsOfExperience;

    private String languages;

    private Double rating;

    private Integer totalConsultations;

    private boolean isAvailable;

    @Column(precision = 10, scale = 2)
    private BigDecimal consultationFee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private boolean isVerified;

    @Enumerated(EnumType.STRING)
    private AstrologerStatus status;

    @Column(precision = 14, scale = 2)
    private BigDecimal totalEarnings;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public BigDecimal getTotalEarnings() {
        return totalEarnings == null ? BigDecimal.ZERO : totalEarnings;
    }
}
