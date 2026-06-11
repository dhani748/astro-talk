package com.astrotalk.controller;

import com.astrotalk.dto.NotificationResponse;
import com.astrotalk.entity.User;
import com.astrotalk.exception.ResourceNotFoundException;
import com.astrotalk.repository.UserRepository;
import com.astrotalk.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Push notifications and alerts")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @GetMapping
    @Operation(summary = "Get notifications", description = "Returns paginated notifications for the user")
    public ResponseEntity<Page<NotificationResponse>> getNotifications(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(notificationService.getNotifications(user.getId(), PageRequest.of(page, size)));
    }

    @GetMapping("/unread-count")
    @Operation(summary = "Get unread notification count", description = "Returns count of unread notifications")
    public ResponseEntity<Map<String, Long>> getUnreadCount(Authentication authentication) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(Map.of("unreadCount", notificationService.getUnreadCount(user.getId())));
    }

    @PutMapping("/{id}/read")
    @Operation(summary = "Mark notification as read", description = "Marks a single notification as read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/read-all")
    @Operation(summary = "Mark all as read", description = "Marks all unread notifications as read")
    public ResponseEntity<Void> markAllAsRead(Authentication authentication) {
        User user = getCurrentUser(authentication);
        notificationService.markAllAsRead(user.getId());
        return ResponseEntity.ok().build();
    }

    private User getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }
}
