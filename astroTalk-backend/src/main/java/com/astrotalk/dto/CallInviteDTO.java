package com.astrotalk.dto;

import com.astrotalk.entity.ConsultationType;
import lombok.Data;

@Data
public class CallInviteDTO {

    private Long consultationId;
    private Long callerId;
    private String callerName;
    private ConsultationType type;
}
