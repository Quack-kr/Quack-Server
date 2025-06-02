package org.quack.QUACKServer.review.repository;


import java.util.List;
import org.quack.QUACKServer.review.domain.ReviewEvalSummary;
import org.quack.QUACKServer.review.dto.response.ReviewEvalSummaryRank;
import org.quack.QUACKServer.review.enums.ReviewEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewEvalSummaryRepository extends JpaRepository<ReviewEvalSummary, Long> {

    @Query("select new org.quack.QUACKServer.review.dto.response.ReviewEvalSummaryRank(r.reviewSummaryRank,"
            + " r.keywordType, r.reviewSummaryTag, r.totalCount) "
            + "from ReviewEvalSummary r "
            + "where r.restaurantId = :restaurantId and r.keywordType = :keywordType "
            + "order by r.reviewSummaryRank asc")
    List<ReviewEvalSummaryRank> findByRestaurantIdAndReviewType(Long restaurantId,
                                                                ReviewEnum.ReviewKeywordType keywordType,
                                                                PageRequest request);



}
