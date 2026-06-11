package com.astrotalk.dto;

import lombok.Data;

@Data
public class SignalDTO {

    private Long consultationId;
    private Long senderId;
    private String sdp;
    private String candidate;
    private String type;
}
