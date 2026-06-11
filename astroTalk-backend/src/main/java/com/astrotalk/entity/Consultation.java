package com.astrotalk.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "astrologer_id", nullable = false)
    private Astrologer astrologer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConsultationType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConsultationStatus status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer durationMinutes;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal astrologerEarning;

    @Column(precision = 10, scale = 2)
    private BigDecimal platformCommission;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
