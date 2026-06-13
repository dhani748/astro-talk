package com.astrotalk.dao;

import com.astrotalk.entity.Consultation;
import com.astrotalk.entity.ConsultationStatus;
import com.astrotalk.repository.ConsultationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Data access layer wrapping {@link ConsultationRepository} for {@link Consultation} entity operations.
 * Provides CRUD methods and specialized queries with logging and error handling.
 */
@Component
@RequiredArgsConstructor
public class ConsultationDAO {

    private static final Logger log = LoggerFactory.getLogger(ConsultationDAO.class);
    private final ConsultationRepository consultationRepository;

    /**
     * Persists a new or existing consultation.
     *
     * @param consultation the consultation entity to save
     * @return the saved consultation entity
     */
    public Consultation save(Consultation consultation) {
        log.debug("Saving consultation: {}", consultation.getId());
        try {
            return consultationRepository.save(consultation);
        } catch (Exception e) {
            log.error("Error saving consultation", e);
            throw e;
        }
    }

    /**
     * Finds a consultation by its ID.
     *
     * @param id the consultation ID
     * @return an Optional containing the consultation, or empty if not found
     */
    public Optional<Consultation> findById(Long id) {
        log.debug("Finding consultation by id: {}", id);
        try {
            return consultationRepository.findById(id);
        } catch (Exception e) {
            log.error("Error finding consultation by id: {}", id, e);
            throw e;
        }
    }

    /**
     * Finds consultations by user ID ordered by creation date descending.
     *
     * @param userId   the user ID
     * @param pageable the pagination information
     * @return a page of consultations for the user
     */
    public Page<Consultation> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable) {
        log.debug("Finding consultations by user id: {}", userId);
        try {
            return consultationRepository.findByUserUserIdOrderByCreatedAtDesc(userId, pageable);
        } catch (Exception e) {
            log.error("Error finding consultations by user id: {}", userId, e);
            throw e;
        }
    }

    /**
     * Finds consultations by astrologer ID and status.
     *
     * @param astrologerId the astrologer ID
     * @param status       the consultation status filter
     * @return a list of matching consultations
     */
    public List<Consultation> findByAstrologerIdAndStatus(Long astrologerId, ConsultationStatus status) {
        log.debug("Finding consultations by astrologer id: {} and status: {}", astrologerId, status);
        try {
            return consultationRepository.findByAstrologerIdAndStatus(astrologerId, status);
        } catch (Exception e) {
            log.error("Error finding consultations by astrologer id: {} and status: {}", astrologerId, status, e);
            throw e;
        }
    }

    /**
     * Finds a consultation by user ID and status.
     *
     * @param userId the user ID
     * @param status the consultation status filter
     * @return an Optional containing the matching consultation, or empty if not found
     */
    public Optional<Consultation> findByUserIdAndStatus(String userId, ConsultationStatus status) {
        log.debug("Finding consultation by user id: {} and status: {}", userId, status);
        try {
            return consultationRepository.findByUserUserIdAndStatus(userId, status);
        } catch (Exception e) {
            log.error("Error finding consultation by user id: {} and status: {}", userId, status, e);
            throw e;
        }
    }

    /**
     * Finds all consultations with the given status.
     *
     * @param status the consultation status filter
     * @return a list of matching consultations
     */
    public List<Consultation> findByStatus(ConsultationStatus status) {
        log.debug("Finding consultations by status: {}", status);
        try {
            return consultationRepository.findByStatus(status);
        } catch (Exception e) {
            log.error("Error finding consultations by status: {}", status, e);
            throw e;
        }
    }

    /**
     * Counts consultations with the given status.
     *
     * @param status the consultation status to count
     * @return the count of consultations with that status
     */
    public long countByStatus(ConsultationStatus status) {
        log.debug("Counting consultations by status: {}", status);
        try {
            return consultationRepository.countByStatus(status);
        } catch (Exception e) {
            log.error("Error counting consultations by status: {}", status, e);
            throw e;
        }
    }

    /**
     * Counts consultations created after the given date-time.
     *
     * @param dateTime the threshold date-time
     * @return the count of consultations created after
     */
    public long countByCreatedAtAfter(LocalDateTime dateTime) {
        log.debug("Counting consultations created after: {}", dateTime);
        try {
            return consultationRepository.countByCreatedAtAfter(dateTime);
        } catch (Exception e) {
            log.error("Error counting consultations created after: {}", dateTime, e);
            throw e;
        }
    }

    /**
     * Computes the total revenue from all completed consultations.
     *
     * @return the total revenue amount
     */
    public BigDecimal getTotalRevenue() {
        log.debug("Getting total revenue from consultations");
        try {
            return consultationRepository.getTotalRevenue();
        } catch (Exception e) {
            log.error("Error getting total revenue", e);
            throw e;
        }
    }

    /**
     * Computes the revenue from completed consultations within a date range.
     *
     * @param startDate the start of the range (inclusive)
     * @param endDate   the end of the range (inclusive)
     * @return the revenue amount in the date range
     */
    public BigDecimal getRevenueBetween(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Getting revenue between: {} and {}", startDate, endDate);
        try {
            return consultationRepository.getRevenueBetween(startDate, endDate);
        } catch (Exception e) {
            log.error("Error getting revenue between: {} and {}", startDate, endDate, e);
            throw e;
        }
    }
}
