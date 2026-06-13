package com.astrotalk.service.impl;

import com.astrotalk.model.NotificationResponseModel;
import com.astrotalk.entity.Notification;
import com.astrotalk.entity.NotificationType;
import com.astrotalk.exception.ResourceNotFoundException;
import com.astrotalk.repository.NotificationRepository;
import com.astrotalk.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    @Transactional
    public NotificationResponseModel sendNotification(Long userId, String title, String message, NotificationType type) {
        Notification notification = Notification.builder()
                .userId(userId)
                .title(title)
                .message(message)
                .type(type)
                .isRead(false)
                .build();

        notification = notificationRepository.save(notification);
        NotificationResponseModel response = toNotificationResponse(notification);

        messagingTemplate.convertAndSend(
                "/topic/user/" + userId + "/notifications",
                response
        );

        return response;
    }

    @Override
    public Page<NotificationResponseModel> getNotifications(Long userId, Pageable pageable) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(this::toNotificationResponse);
    }

    @Override
    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", notificationId));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void markAllAsRead(Long userId) {
        notificationRepository.markAllAsRead(userId);
    }

    @Override
    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    @Override
    public NotificationResponseModel getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", id));
        return toNotificationResponse(notification);
    }

    @Override
    @Transactional
    public void sendLowBalanceAlert(Long userId) {
        sendNotification(userId, "Low Balance Warning",
                "Your wallet balance is low. Please recharge to continue consultations.",
                NotificationType.LOW_BALANCE);
    }

    @Override
    @Transactional
    public void sendSessionEndAlert(Long userId) {
        sendNotification(userId, "Session Ended",
                "Your consultation session has ended. Please leave a review!",
                NotificationType.SESSION_START);
    }

    private NotificationResponseModel toNotificationResponse(Notification n) {
        return NotificationResponseModel.builder()
                .id(n.getId())
                .userId(n.getUserId())
                .title(n.getTitle())
                .message(n.getMessage())
                .type(n.getType())
                .isRead(n.isRead())
                .createdAt(n.getCreatedAt())
                .build();
    }
}
