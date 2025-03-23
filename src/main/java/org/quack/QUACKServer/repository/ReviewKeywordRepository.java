package org.quack.QUACKServer.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.quack.QUACKServer.domain.ReviewKeyword;
import org.quack.QUACKServer.domain.common.KeywordType;
import org.quack.QUACKServer.dto.restaurant.ReviewKeywordDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewKeywordRepository extends JpaRepository<ReviewKeyword, Long> {

    @Query("SELECT new org.quack.QUACKServer.dto.restaurant.ReviewKeywordDto(k.keywordName, COUNT(rk)) " +
            "FROM ReviewKeyword rk " +
            "JOIN rk.keyword k " +
            "JOIN rk.review r " +
            "WHERE r.restaurant.restaurantId = :restaurantId " +
            "  AND r.createdDate >= :date " +
            "  AND k.keywordType = :keywordType " +
            "GROUP BY k.keywordName " +
            "ORDER BY COUNT(rk) DESC")
    List<ReviewKeywordDto> findTopKeywordsByType(Long restaurantId, LocalDateTime date,
                                                 KeywordType keywordType, Pageable pageable);
}
