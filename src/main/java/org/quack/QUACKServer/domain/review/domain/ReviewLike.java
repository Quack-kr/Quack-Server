package org.quack.QUACKServer.domain.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.quack.QUACKServer.domain.review.enums.ReviewEnum;

@Entity
@Table(name = "review_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewLike {

    @Id
    @GeneratedValue
    private Long reviewLikeId;

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "like_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ReviewEnum.ReviewLikeType likeType;

}
