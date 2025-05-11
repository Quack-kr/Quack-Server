package org.quack.QUACKServer.domain.review.domain;

import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.quack.QUACKServer.domain.review.enums.ReviewEnum;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description : 식당별 리뷰 3개월 간 상세 정보 Entity
 * @packageName : org.quack.QUACKServer.domain.review.domain
 * @fileName : ReviewSummary
 * @date : 25. 5. 4.
 */

@Getter
@Table(name = "review_summary")
@Entity
public class ReviewSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_summary_id", nullable = false)
    private long reviewSummaryId;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "history_date", nullable = false, updatable = false)
    private LocalDateTime historyDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "keyword_type", nullable = false, length = 10)
    private ReviewEnum.ReviewKeywordType reviewKeywordType;

    @Column(name = "review_summary_rank", nullable = false)
    private Long rank;

    @Column(name = "total_count", nullable = false)
    private Long count;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "review_summary_tag", nullable = false)
    private ReviewEnum.ReviewTag reviewTag;


}
