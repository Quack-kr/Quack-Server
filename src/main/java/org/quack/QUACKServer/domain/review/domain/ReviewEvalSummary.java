package org.quack.QUACKServer.domain.review.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.quack.QUACKServer.domain.review.enums.ReviewEnum;

import java.time.LocalDate;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.review.domain
 * @fileName : ReviewEvalSummary
 * @date : 25. 5. 18.
 */

@Table(name = "review_eval_summary")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEvalSummary {

    @Id
    @Column(name = "review_eval_summary_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewEvalSummaryId;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "history_at", nullable = false)
    private LocalDate historyAt;

    @Column(name = "keyword_type")
    @Enumerated(EnumType.STRING)
    private ReviewEnum.ReviewKeywordType keywordType;

    @Column(name = "review_summary_tag")
    @Enumerated(EnumType.STRING)
    private ReviewEnum.ReviewTag reviewSummaryTag;

    @Column(name = "review_summary_rank")
    private Integer reviewSummaryRank;

    @Column(name = "total_count")
    private Long totalCount;

}
