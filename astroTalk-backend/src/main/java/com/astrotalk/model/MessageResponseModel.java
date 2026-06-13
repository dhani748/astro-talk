package com.astrotalk.model;

import com.astrotalk.entity.MessageType;
import com.astrotalk.entity.SenderRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageResponseModel {
    private Long id;
    private Long consultationId;
    private Long senderId;
    private SenderRole senderRole;
    private String content;
    private MessageType messageType;
    private boolean isRead;
    private LocalDateTime createdAt;
}
