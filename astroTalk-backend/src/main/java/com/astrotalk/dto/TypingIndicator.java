package com.astrotalk.dto;

import com.astrotalk.entity.SenderRole;
import lombok.Data;

@Data
public class TypingIndicator {

    private Long consultationId;
    private Long senderId;
    private SenderRole senderRole;
    private boolean typing;
}
