package com.astrotalk.repository;

import com.astrotalk.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByAstrologerIdOrderByCreatedAtDesc(Long astrologerId, Pageable pageable);

    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM Review r WHERE r.astrologer.id = :astrologerId")
    Double findAverageRatingByAstrologerId(@Param("astrologerId") Long astrologerId);

    boolean existsByUserIdAndConsultationId(Long userId, Long consultationId);
}
