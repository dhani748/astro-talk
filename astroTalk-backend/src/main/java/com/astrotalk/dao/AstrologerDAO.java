package com.astrotalk.dao;

import com.astrotalk.entity.Astrologer;
import com.astrotalk.entity.AstrologerStatus;
import com.astrotalk.repository.AstrologerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Data access layer wrapping {@link AstrologerRepository} for {@link Astrologer} entity operations.
 * Provides CRUD methods and specialized queries with logging and error handling.
 */
@Component
@RequiredArgsConstructor
public class AstrologerDAO {

    private static final Logger log = LoggerFactory.getLogger(AstrologerDAO.class);
    private final AstrologerRepository astrologerRepository;

    /**
     * Persists a new or existing astrologer.
     *
     * @param astrologer the astrologer entity to save
     * @return the saved astrologer entity
     */
    public Astrologer save(Astrologer astrologer) {
        log.debug("Saving astrologer: {}", astrologer.getEmail());
        try {
            return astrologerRepository.save(astrologer);
        } catch (Exception e) {
            log.error("Error saving astrologer: {}", astrologer.getEmail(), e);
            throw e;
        }
    }

    /**
     * Finds an astrologer by their ID.
     *
     * @param id the astrologer ID
     * @return an Optional containing the astrologer, or empty if not found
     */
    public Optional<Astrologer> findById(Long id) {
        log.debug("Finding astrologer by id: {}", id);
        try {
            return astrologerRepository.findById(id);
        } catch (Exception e) {
            log.error("Error finding astrologer by id: {}", id, e);
            throw e;
        }
    }

    /**
     * Finds an astrologer by their email address.
     *
     * @param email the astrologer's email
     * @return an Optional containing the astrologer, or empty if not found
     */
    public Optional<Astrologer> findByEmail(String email) {
        log.debug("Finding astrologer by email: {}", email);
        try {
            return astrologerRepository.findByEmail(email);
        } catch (Exception e) {
            log.error("Error finding astrologer by email: {}", email, e);
            throw e;
        }
    }

    /**
     * Checks whether an astrologer exists with the given email.
     *
     * @param email the email to check
     * @return true if an astrologer with the email exists
     */
    public boolean existsByEmail(String email) {
        log.debug("Checking if astrologer exists by email: {}", email);
        try {
            return astrologerRepository.existsByEmail(email);
        } catch (Exception e) {
            log.error("Error checking astrologer existence by email: {}", email, e);
            throw e;
        }
    }

    /**
     * Retrieves all astrologers.
     *
     * @return a list of all astrologers
     */
    public List<Astrologer> findAll() {
        log.debug("Finding all astrologers");
        try {
            return astrologerRepository.findAll();
        } catch (Exception e) {
            log.error("Error finding all astrologers", e);
            throw e;
        }
    }

    /**
     * Deletes the given astrologer entity.
     *
     * @param astrologer the astrologer to delete
     */
    public void delete(Astrologer astrologer) {
        log.debug("Deleting astrologer: {}", astrologer.getId());
        try {
            astrologerRepository.delete(astrologer);
        } catch (Exception e) {
            log.error("Error deleting astrologer: {}", astrologer.getId(), e);
            throw e;
        }
    }

    /**
     * Finds astrologers by their availability status.
     *
     * @param isAvailable the availability flag
     * @return a list of matching astrologers
     */
    public List<Astrologer> findByIsAvailable(boolean isAvailable) {
        log.debug("Finding astrologers by isAvailable: {}", isAvailable);
        try {
            return astrologerRepository.findByIsAvailable(isAvailable);
        } catch (Exception e) {
            log.error("Error finding astrologers by isAvailable: {}", isAvailable, e);
            throw e;
        }
    }

    /**
     * Finds astrologers by their specialization.
     *
     * @param specialization the specialization filter
     * @return a list of matching astrologers
     */
    public List<Astrologer> findBySpecialization(String specialization) {
        log.debug("Finding astrologers by specialization: {}", specialization);
        try {
            return astrologerRepository.findBySpecialization(specialization);
        } catch (Exception e) {
            log.error("Error finding astrologers by specialization: {}", specialization, e);
            throw e;
        }
    }

    /**
     * Finds astrologers by both specialization and availability.
     *
     * @param specialization the specialization filter
     * @param isAvailable    the availability flag
     * @return a list of matching astrologers
     */
    public List<Astrologer> findBySpecializationAndIsAvailable(String specialization, boolean isAvailable) {
        log.debug("Finding astrologers by specialization: {} and isAvailable: {}", specialization, isAvailable);
        try {
            return astrologerRepository.findBySpecializationAndIsAvailable(specialization, isAvailable);
        } catch (Exception e) {
            log.error("Error finding astrologers by specialization: {} and isAvailable: {}", specialization, isAvailable, e);
            throw e;
        }
    }

    /**
     * Finds astrologers by their verification status.
     *
     * @param isVerified the verification flag
     * @return a list of matching astrologers
     */
    public List<Astrologer> findByIsVerified(boolean isVerified) {
        log.debug("Finding astrologers by isVerified: {}", isVerified);
        try {
            return astrologerRepository.findByIsVerified(isVerified);
        } catch (Exception e) {
            log.error("Error finding astrologers by isVerified: {}", isVerified, e);
            throw e;
        }
    }

    /**
     * Finds astrologers by their online status with pagination.
     *
     * @param status   the astrologer status filter
     * @param pageable the pagination information
     * @return a page of matching astrologers
     */
    public Page<Astrologer> findByStatus(AstrologerStatus status, Pageable pageable) {
        log.debug("Finding astrologers by status: {}", status);
        try {
            return astrologerRepository.findByStatus(status, pageable);
        } catch (Exception e) {
            log.error("Error finding astrologers by status: {}", status, e);
            throw e;
        }
    }

    /**
     * Retrieves the top 6 astrologers ordered by rating descending.
     *
     * @return a list of top-rated astrologers
     */
    public List<Astrologer> findTopByRating() {
        log.debug("Finding top astrologers by rating");
        try {
            return astrologerRepository.findTop6ByOrderByRatingDesc();
        } catch (Exception e) {
            log.error("Error finding top astrologers by rating", e);
            throw e;
        }
    }

    /**
     * Searches astrologers by name or email with case-insensitive matching.
     *
     * @param name     the name search term
     * @param email    the email search term
     * @param pageable the pagination information
     * @return a page of matching astrologers
     */
    public Page<Astrologer> searchByNameOrEmail(String name, String email, Pageable pageable) {
        log.debug("Searching astrologers by name: {} or email: {}", name, email);
        try {
            return astrologerRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(name, email, pageable);
        } catch (Exception e) {
            log.error("Error searching astrologers by name: {} or email: {}", name, email, e);
            throw e;
        }
    }

    /**
     * Searches astrologers with multiple optional filters: specialization, language, price range,
     * minimum rating, and online status.
     *
     * @param specialization optional specialization filter
     * @param language       optional language filter
     * @param minPrice       optional minimum price filter
     * @param maxPrice       optional maximum price filter
     * @param minRating      optional minimum rating filter
     * @param isOnline       optional online status filter
     * @param pageable       the pagination information
     * @return a page of matching astrologers
     */
    public Page<Astrologer> searchAstrologers(String specialization, String language, BigDecimal minPrice,
                                              BigDecimal maxPrice, Double minRating, Boolean isOnline, Pageable pageable) {
        log.debug("Searching astrologers with filters - specialization: {}, language: {}, minPrice: {}, maxPrice: {}, minRating: {}, isOnline: {}",
                specialization, language, minPrice, maxPrice, minRating, isOnline);
        try {
            return astrologerRepository.searchAstrologers(specialization, language, minPrice, maxPrice, minRating, isOnline, pageable);
        } catch (Exception e) {
            log.error("Error searching astrologers with filters", e);
            throw e;
        }
    }
}
