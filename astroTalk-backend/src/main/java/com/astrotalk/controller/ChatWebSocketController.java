package com.astrotalk.controller;

import com.astrotalk.dto.MessageRequest;
import com.astrotalk.dto.MessageResponse;
import com.astrotalk.dto.TypingIndicator;
import com.astrotalk.entity.Astrologer;
import com.astrotalk.entity.Role;
import com.astrotalk.entity.SenderRole;
import com.astrotalk.entity.User;
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

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;
    private final SimpMessageSendingOperations messagingTemplate;
    private final UserRepository userRepository;
    private final AstrologerRepository astrologerRepository;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload MessageRequest request, SimpMessageHeaderAccessor headerAccessor) {
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

    private void handleSend(MessageRequest request, Long senderId, SenderRole senderRole) {
        MessageResponse response = chatService.sendMessage(
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

    @MessageMapping("/chat.read")
    public void markAsRead(@Payload MessageRequest request, SimpMessageHeaderAccessor headerAccessor) {
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

    @MessageMapping("/chat.typing")
    public void typing(@Payload TypingIndicator indicator, SimpMessageHeaderAccessor headerAccessor) {
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
