package org.quack.QUACKServer.domain.review.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "restaurantId", nullable = false)
    private Long restaurantId;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "review_content")
    private String reviewContent;

    @Column(name = "review_disabled", columnDefinition = "bit(1) default 0")
    private Boolean disabled;

//    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = false)
//    private List<ReviewImage> reviewImages = new ArrayList<>();

    private Review(Long userId, Long restaurantId, String reviewContent) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.reviewContent = reviewContent;
        this.disabled = false;
    }

    public static Review createReview(Long userId, Long restaurantId, String reviewContent) {
        return new Review(userId, restaurantId, reviewContent);
    }


}
