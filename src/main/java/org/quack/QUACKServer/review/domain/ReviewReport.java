package org.quack.QUACKServer.review.domain;

import jakarta.persistence.*;
import lombok.*;

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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PROTECTED)
public class ReviewReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_report_id")
    private Long id;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "customer_user_id", nullable = false)
    private Long customerUserId;

    @Column(name = "content")
    private String reviewContent;

    @Column(name = "is_reported")
    private Boolean isReported;


    public static ReviewReport create(Review review, String reviewContent, Long customerUserId) {
        return ReviewReport.builder()
                .restaurantId(review.getRestaurantId())
                .reviewId(review.getReviewId())
                .reviewContent(reviewContent)
                .customerUserId(customerUserId)
                .isReported(true)
                .build();
    }

}
