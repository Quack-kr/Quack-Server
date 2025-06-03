package org.quack.QUACKServer.review.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.review.domain
 * @fileName : ReportedReview
 * @date : 25. 6. 1.
 */
@Entity
@Table(name = "review_report")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_report_id")
    private Long id;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "is_reported")
    private Boolean isReported;
}
