package com.astrotalk.dao;

import com.astrotalk.entity.Message;
import com.astrotalk.entity.SenderRole;
import com.astrotalk.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Data access layer wrapping {@link MessageRepository} for {@link Message} entity operations.
 * Provides CRUD methods and queries for consultation chat messages.
 */
@Component
@RequiredArgsConstructor
public class MessageDAO {

    private static final Logger log = LoggerFactory.getLogger(MessageDAO.class);
    private final MessageRepository messageRepository;

    /**
     * Persists a new chat message.
     *
     * @param message the message entity to save
     * @return the saved message entity
     */
    public Message save(Message message) {
        log.debug("Saving message for consultation: {}", message.getConsultationId());
        try {
            return messageRepository.save(message);
        } catch (Exception e) {
            log.error("Error saving message for consultation: {}", message.getConsultationId(), e);
            throw e;
        }
    }

    /**
     * Finds messages for a consultation ordered by creation time ascending.
     *
     * @param consultationId the consultation ID
     * @param pageable       the pagination information
     * @return a page of messages
     */
    public Page<Message> findByConsultationIdOrderByCreatedAtAsc(Long consultationId, Pageable pageable) {
        log.debug("Finding messages by consultation id: {}", consultationId);
        try {
            return messageRepository.findByConsultationIdOrderByCreatedAtAsc(consultationId, pageable);
        } catch (Exception e) {
            log.error("Error finding messages by consultation id: {}", consultationId, e);
            throw e;
        }
    }

    /**
     * Finds all unread messages for a consultation.
     *
     * @param consultationId the consultation ID
     * @return a list of unread messages
     */
    public List<Message> findByConsultationIdAndIsReadFalse(Long consultationId) {
        log.debug("Finding unread messages by consultation id: {}", consultationId);
        try {
            return messageRepository.findByConsultationIdAndIsReadFalse(consultationId);
        } catch (Exception e) {
            log.error("Error finding unread messages by consultation id: {}", consultationId, e);
            throw e;
        }
    }

    /**
     * Counts unread messages for a consultation filtered by the sender's role.
     *
     * @param consultationId the consultation ID
     * @param senderRole     the sender role to exclude from the unread count
     * @return the count of unread messages
     */
    public long countUnreadByConsultationIdAndSenderRole(Long consultationId, SenderRole senderRole) {
        log.debug("Counting unread messages by consultation id: {} and sender role: {}", consultationId, senderRole);
        try {
            return messageRepository.countByConsultationIdAndSenderRoleAndIsReadFalse(consultationId, senderRole);
        } catch (Exception e) {
            log.error("Error counting unread messages by consultation id: {} and sender role: {}", consultationId, senderRole, e);
            throw e;
        }
    }

    /**
     * Marks all unread messages as read for a given consultation,
     * excluding messages sent by the specified sender role.
     *
     * @param consultationId the consultation ID
     * @param senderRole     the sender role whose messages should not be marked as read
     * @return the number of messages marked as read
     */
    public int markMessagesAsRead(Long consultationId, SenderRole senderRole) {
        log.debug("Marking messages as read for consultation: {} and sender role: {}", consultationId, senderRole);
        try {
            return messageRepository.markMessagesAsRead(consultationId, senderRole);
        } catch (Exception e) {
            log.error("Error marking messages as read for consultation: {} and sender role: {}", consultationId, senderRole, e);
            throw e;
        }
    }
}
