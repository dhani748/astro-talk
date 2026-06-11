package com.astrotalk.service;

import com.astrotalk.dto.ChatHistoryResponse;
import com.astrotalk.dto.MessageResponse;
import com.astrotalk.entity.MessageType;
import com.astrotalk.entity.SenderRole;

public interface ChatService {

    MessageResponse sendMessage(Long consultationId, Long senderId, SenderRole senderRole, String content, MessageType messageType);

    ChatHistoryResponse getMessageHistory(Long consultationId, int page, int size);

    void markAsRead(Long consultationId, SenderRole senderRole);

    long getUnreadCount(Long consultationId, SenderRole senderRole);
}
