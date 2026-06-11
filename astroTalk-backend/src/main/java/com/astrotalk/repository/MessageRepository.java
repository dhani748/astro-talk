package com.astrotalk.repository;

import com.astrotalk.entity.Message;
import com.astrotalk.entity.SenderRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByConsultationIdOrderByCreatedAtAsc(Long consultationId, Pageable pageable);

    List<Message> findByConsultationIdAndIsReadFalse(Long consultationId);

    long countByConsultationIdAndSenderRoleAndIsReadFalse(Long consultationId, SenderRole senderRole);

    @Modifying
    @Query("UPDATE Message m SET m.isRead = true WHERE m.consultationId = :consultationId AND m.senderRole <> :senderRole AND m.isRead = false")
    int markMessagesAsRead(@Param("consultationId") Long consultationId, @Param("senderRole") SenderRole senderRole);
}
