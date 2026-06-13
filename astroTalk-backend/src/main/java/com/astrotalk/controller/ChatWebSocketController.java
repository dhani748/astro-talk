package com.astrotalk.controller;

import com.astrotalk.entity.Astrologer;
import com.astrotalk.entity.Role;
import com.astrotalk.entity.SenderRole;
import com.astrotalk.entity.User;
import com.astrotalk.model.MessageRequestModel;
import com.astrotalk.model.MessageResponseModel;
import com.astrotalk.model.TypingIndicatorModel;
import com.astrotalk.repository.AstrologerRepository;
import com.astrotalk.repository.UserRepository;
import com.astrotalk.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * WebSocket controller for real-time chat messaging.
 * Handles sending messages, read receipts, and typing indicators via STOMP.
 */
@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;
    private final SimpMessageSendingOperations messagingTemplate;
    private final UserRepository userRepository;
    private final AstrologerRepository astrologerRepository;

    /**
     * Handles /chat.send - Persists and broadcasts a chat message to the consultation topic.
     *
     * @param request       the message payload (consultation ID, content, type)
     * @param headerAccessor used to extract the authenticated sender principal
     */
    @MessageMapping("/chat.send")
    public void sendMessage(@Payload MessageRequestModel request, SimpMessageHeaderAccessor headerAccessor) {
        Principal principal = headerAccessor.getUser();
        if (principal == null) return;

        String email = principal.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {
            handleSend(request, user.getId(), SenderRole.USER);
            return;
        }

        Astrologer astrologer = astrologerRepository.findByEmail(email)
                .orElse(null);
        if (astrologer != null) {
            handleSend(request, astrologer.getId(), SenderRole.ASTROLOGER);
        }
    }

    private void handleSend(MessageRequestModel request, Long senderId, SenderRole senderRole) {
        MessageResponseModel response = chatService.sendMessage(
                request.getConsultationId(),
                senderId,
                senderRole,
                request.getContent(),
                request.getMessageType()
        );

        messagingTemplate.convertAndSend(
                "/topic/consultation/" + request.getConsultationId(),
                response
        );
    }

    /**
     * Handles /chat.read - Marks messages as read and broadcasts a read receipt.
     *
     * @param request       the message payload containing the consultation ID
     * @param headerAccessor used to extract the authenticated principal
     */
    @MessageMapping("/chat.read")
    public void markAsRead(@Payload MessageRequestModel request, SimpMessageHeaderAccessor headerAccessor) {
        Principal principal = headerAccessor.getUser();
        if (principal == null) return;

        SenderRole senderRole = resolveRole(principal.getName());
        if (senderRole == null) return;

        chatService.markAsRead(request.getConsultationId(), senderRole);

        messagingTemplate.convertAndSend(
                "/topic/consultation/" + request.getConsultationId() + "/read",
                request.getConsultationId()
        );
    }

    /**
     * Handles /chat.typing - Broadcasts a typing indicator to the consultation topic.
     *
     * @param indicator     the typing indicator payload (consultation ID, sender info)
     * @param headerAccessor used to extract the authenticated principal
     */
    @MessageMapping("/chat.typing")
    public void typing(@Payload TypingIndicatorModel indicator, SimpMessageHeaderAccessor headerAccessor) {
        Principal principal = headerAccessor.getUser();
        if (principal == null) return;

        String email = principal.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {
            indicator.setSenderId(user.getId());
            indicator.setSenderRole(SenderRole.USER);
        } else {
            Astrologer astrologer = astrologerRepository.findByEmail(email).orElse(null);
            if (astrologer == null) return;
            indicator.setSenderId(astrologer.getId());
            indicator.setSenderRole(SenderRole.ASTROLOGER);
        }

        messagingTemplate.convertAndSend(
                "/topic/consultation/" + indicator.getConsultationId() + "/typing",
                indicator
        );
    }

    private SenderRole resolveRole(String email) {
        if (userRepository.findByEmail(email).isPresent()) return SenderRole.USER;
        if (astrologerRepository.findByEmail(email).isPresent()) return SenderRole.ASTROLOGER;
        return null;
    }
}
