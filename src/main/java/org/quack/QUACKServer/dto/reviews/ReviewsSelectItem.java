package org.quack.QUACKServer.dto.reviews;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.quack.QUACKServer.domain.Review;
import org.quack.QUACKServer.domain.ReviewLike;
import org.quack.QUACKServer.domain.ReviewMenu;
import org.quack.QUACKServer.domain.common.LikeType;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.dto.reviews
 * @fileName : ReviewsSelectItem
 * @date : 25. 3. 31.
 */
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewsSelectItem {

    private final Long reviewId;
    private final ReviewsSelectUserItem user;
    private final List<ReviewsSelectPhotoItem> reviewImages;
    private final String content;
    private final String createDate;
    private final List<String> evaluations;
    private final ReviewsSelectLikeItem gongGams;

    public static ReviewsSelectItem of(Long userId, Review review) {

        return ReviewsSelectItem.builder()
                .reviewId(review.getReviewId())
                .user(ReviewsSelectUserItem.from(review.getUser()))
                .content(review.getContent())
                .evaluations(review.getReviewMenus().stream()
                        .map(ReviewMenu::getEvaluation)
                        .toList())
                .gongGams(ReviewsSelectLikeItem.of(userId, review.getReviewLike()))
                .build();

    }
}
