package com.astrotalk.service.impl;

import com.astrotalk.dto.ChatHistoryResponse;
import com.astrotalk.dto.MessageResponse;
import com.astrotalk.entity.Message;
import com.astrotalk.entity.MessageType;
import com.astrotalk.entity.SenderRole;
import com.astrotalk.repository.ConsultationRepository;
import com.astrotalk.repository.MessageRepository;
import com.astrotalk.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MessageRepository messageRepository;
    private final ConsultationRepository consultationRepository;

    @Override
    @Transactional
    public MessageResponse sendMessage(Long consultationId, Long senderId, SenderRole senderRole, String content, MessageType messageType) {
        Message message = Message.builder()
                .consultationId(consultationId)
                .senderId(senderId)
                .senderRole(senderRole)
                .content(content)
                .messageType(messageType)
                .isRead(false)
                .build();

        message = messageRepository.save(message);
        return toMessageResponse(message);
    }

    @Override
    public ChatHistoryResponse getMessageHistory(Long consultationId, int page, int size) {
        Page<Message> messagePage = messageRepository
                .findByConsultationIdOrderByCreatedAtAsc(consultationId, PageRequest.of(page, size));

        return ChatHistoryResponse.builder()
                .messages(messagePage.getContent().stream().map(this::toMessageResponse).toList())
                .currentPage(messagePage.getNumber())
                .totalPages(messagePage.getTotalPages())
                .totalElements(messagePage.getTotalElements())
                .build();
    }

    @Override
    @Transactional
    public void markAsRead(Long consultationId, SenderRole senderRole) {
        messageRepository.markMessagesAsRead(consultationId, senderRole);
    }

    @Override
    public long getUnreadCount(Long consultationId, SenderRole senderRole) {
        return messageRepository.countByConsultationIdAndSenderRoleAndIsReadFalse(consultationId, senderRole);
    }

    private MessageResponse toMessageResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .consultationId(message.getConsultationId())
                .senderId(message.getSenderId())
                .senderRole(message.getSenderRole())
                .content(message.getContent())
                .messageType(message.getMessageType())
                .isRead(message.isRead())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
