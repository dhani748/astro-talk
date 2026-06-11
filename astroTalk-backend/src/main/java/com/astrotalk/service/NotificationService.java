package com.astrotalk.service;

import com.astrotalk.dto.NotificationResponse;
import com.astrotalk.entity.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    NotificationResponse sendNotification(Long userId, String title, String message, NotificationType type);

    Page<NotificationResponse> getNotifications(Long userId, Pageable pageable);

    void markAsRead(Long notificationId);

    void markAllAsRead(Long userId);

    long getUnreadCount(Long userId);

    void sendLowBalanceAlert(Long userId);

    void sendSessionEndAlert(Long userId);
}
