package com.astrotalk.model;

import com.astrotalk.entity.SenderRole;
import lombok.Data;

@Data
public class TypingIndicatorModel {
    private Long consultationId;
    private Long senderId;
    private SenderRole senderRole;
    private boolean typing;
}
