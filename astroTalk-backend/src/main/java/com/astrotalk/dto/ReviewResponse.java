package com.astrotalk.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewResponse {

    private Long id;
    private Long userId;
    private String userName;
    private Long astrologerId;
    private String astrologerName;
    private Long consultationId;
    private int rating;
    private String comment;
    private boolean isVisible;
    private LocalDateTime createdAt;
}
