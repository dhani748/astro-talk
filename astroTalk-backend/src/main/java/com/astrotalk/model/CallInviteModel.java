package com.astrotalk.model;

import com.astrotalk.entity.ConsultationType;
import lombok.Data;

@Data
public class CallInviteModel {
    private Long consultationId;
    private Long callerId;
    private String callerName;
    private ConsultationType type;
}
