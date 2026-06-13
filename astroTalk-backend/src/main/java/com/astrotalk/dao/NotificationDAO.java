package com.astrotalk.dao;

import com.astrotalk.entity.Notification;
import com.astrotalk.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Data access layer wrapping {@link NotificationRepository} for {@link Notification} entity operations.
 * Provides methods for saving, querying, and managing notification read status.
 */
@Component
@RequiredArgsConstructor
public class NotificationDAO {

    private static final Logger log = LoggerFactory.getLogger(NotificationDAO.class);
    private final NotificationRepository notificationRepository;

    /**
     * Checks if a notification exists by its ID.
     *
     * @param id the notification ID
     * @return true if the notification exists
     */
    public boolean existsById(Long id) {
        log.debug("Checking if notification exists by id: {}", id);
        try {
            return notificationRepository.existsById(id);
        } catch (Exception e) {
            log.error("Error checking notification existence by id: {}", id, e);
            throw e;
        }
    }

    /**
     * Finds a notification by its ID.
     *
     * @param id the notification ID
     * @return an Optional containing the notification, or empty if not found
     */
    public java.util.Optional<Notification> findById(Long id) {
        log.debug("Finding notification by id: {}", id);
        try {
            return notificationRepository.findById(id);
        } catch (Exception e) {
            log.error("Error finding notification by id: {}", id, e);
            throw e;
        }
    }

    /**
     * Persists a new or existing notification.
     *
     * @param notification the notification entity to save
     * @return the saved notification entity
     */
    public Notification save(Notification notification) {
        log.debug("Saving notification for user: {}", notification.getUserId());
        try {
            return notificationRepository.save(notification);
        } catch (Exception e) {
            log.error("Error saving notification for user: {}", notification.getUserId(), e);
            throw e;
        }
    }

    /**
     * Finds notifications for a user ordered by creation date descending.
     *
     * @param userId   the user ID
     * @param pageable the pagination information
     * @return a page of notifications
     */
    public Page<Notification> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable) {
        log.debug("Finding notifications by user id: {}", userId);
        try {
            return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        } catch (Exception e) {
            log.error("Error finding notifications by user id: {}", userId, e);
            throw e;
        }
    }

    /**
     * Counts unread notifications for a user.
     *
     * @param userId the user ID
     * @return the count of unread notifications
     */
    public long countByUserIdAndIsReadFalse(Long userId) {
        log.debug("Counting unread notifications by user id: {}", userId);
        try {
            return notificationRepository.countByUserIdAndIsReadFalse(userId);
        } catch (Exception e) {
            log.error("Error counting unread notifications by user id: {}", userId, e);
            throw e;
        }
    }

    /**
     * Marks all unread notifications as read for a user.
     *
     * @param userId the user ID
     * @return the number of notifications marked as read
     */
    public int markAllAsRead(Long userId) {
        log.debug("Marking all notifications as read for user: {}", userId);
        try {
            return notificationRepository.markAllAsRead(userId);
        } catch (Exception e) {
            log.error("Error marking all notifications as read for user: {}", userId, e);
            throw e;
        }
    }
}
