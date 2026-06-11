package com.astrotalk.repository;

import com.astrotalk.entity.Astrologer;
import com.astrotalk.entity.AstrologerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AstrologerRepository extends JpaRepository<Astrologer, Long> {

    Optional<Astrologer> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Astrologer> findByIsAvailable(boolean isAvailable);

    List<Astrologer> findBySpecialization(String specialization);

    List<Astrologer> findBySpecializationAndIsAvailable(String specialization, boolean isAvailable);

    List<Astrologer> findByIsVerified(boolean isVerified);

    Page<Astrologer> findByStatus(AstrologerStatus status, Pageable pageable);

    List<Astrologer> findTop6ByOrderByRatingDesc();

    Page<Astrologer> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);

    @Query("SELECT a FROM Astrologer a WHERE " +
           "(:specialization IS NULL OR a.specialization = :specialization) AND " +
           "(:language IS NULL OR a.languages LIKE %:language%) AND " +
           "(:minPrice IS NULL OR a.consultationFee >= :minPrice) AND " +
           "(:maxPrice IS NULL OR a.consultationFee <= :maxPrice) AND " +
           "(:minRating IS NULL OR a.rating >= :minRating) AND " +
           "(:isOnline IS NULL OR (:isOnline = true AND a.status = 'ONLINE') OR (:isOnline = false AND a.status <> 'ONLINE'))")
    Page<Astrologer> searchAstrologers(
            @Param("specialization") String specialization,
            @Param("language") String language,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minRating") Double minRating,
            @Param("isOnline") Boolean isOnline,
            Pageable pageable);
}
