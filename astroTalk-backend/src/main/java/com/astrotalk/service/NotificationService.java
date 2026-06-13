package com.astrotalk.service;

import com.astrotalk.model.NotificationResponseModel;
import com.astrotalk.entity.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    NotificationResponseModel sendNotification(Long userId, String title, String message, NotificationType type);

    Page<NotificationResponseModel> getNotifications(Long userId, Pageable pageable);

    void markAsRead(Long notificationId);

    void markAllAsRead(Long userId);

    long getUnreadCount(Long userId);

    void sendLowBalanceAlert(Long userId);

    void sendSessionEndAlert(Long userId);

    NotificationResponseModel getNotificationById(Long id);
}
