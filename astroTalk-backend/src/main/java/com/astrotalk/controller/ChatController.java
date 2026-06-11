package com.astrotalk.controller;

import com.astrotalk.dto.ChatHistoryResponse;
import com.astrotalk.entity.Astrologer;
import com.astrotalk.entity.SenderRole;
import com.astrotalk.entity.User;
import com.astrotalk.repository.AstrologerRepository;
import com.astrotalk.repository.UserRepository;
import com.astrotalk.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "Chat", description = "Real-time chat and messaging")
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;
    private final AstrologerRepository astrologerRepository;

    @GetMapping("/{consultationId}/messages")
    @Operation(summary = "Get chat messages", description = "Returns paginated message history for a consultation")
    public ResponseEntity<ChatHistoryResponse> getMessages(
            @PathVariable Long consultationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(chatService.getMessageHistory(consultationId, page, size));
    }

    @PostMapping("/{consultationId}/read")
    @Operation(summary = "Mark messages as read", description = "Marks all unread messages as read for the current user")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long consultationId,
            Authentication authentication) {
        SenderRole role = resolveRole(authentication.getName());
        if (role != null) {
            chatService.markAsRead(consultationId, role);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{consultationId}/unread-count")
    @Operation(summary = "Get unread message count", description = "Returns unread message count for a consultation")
    public ResponseEntity<Map<String, Long>> getUnreadCount(
            @PathVariable Long consultationId,
            Authentication authentication) {
        SenderRole role = resolveRole(authentication.getName());
        long count = role != null ? chatService.getUnreadCount(consultationId, role) : 0;
        return ResponseEntity.ok(Map.of("unreadCount", count));
    }

    private SenderRole resolveRole(String email) {
        if (userRepository.findByEmail(email).isPresent()) return SenderRole.USER;
        if (astrologerRepository.findByEmail(email).isPresent()) return SenderRole.ASTROLOGER;
        return null;
    }
}
