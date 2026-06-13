package com.astrotalk.model;

import lombok.Data;

@Data
public class SignalModel {
    private Long consultationId;
    private Long senderId;
    private String sdp;
    private String candidate;
    private String type;
}
