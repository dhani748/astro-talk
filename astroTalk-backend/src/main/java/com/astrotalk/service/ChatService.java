package com.astrotalk.service;

import com.astrotalk.model.ChatHistoryResponseModel;
import com.astrotalk.model.MessageResponseModel;
import com.astrotalk.entity.MessageType;
import com.astrotalk.entity.SenderRole;

public interface ChatService {

    MessageResponseModel sendMessage(Long consultationId, Long senderId, SenderRole senderRole, String content, MessageType messageType);

    ChatHistoryResponseModel getMessageHistory(Long consultationId, int page, int size);

    void markAsRead(Long consultationId, SenderRole senderRole);

    long getUnreadCount(Long consultationId, SenderRole senderRole);
}
