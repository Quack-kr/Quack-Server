package org.quack.QUACKServer.dto.reviews;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.quack.QUACKServer.domain.Review;
import org.quack.QUACKServer.domain.ReviewLike;
import org.quack.QUACKServer.domain.common.LikeType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.dto.reviews
 * @fileName : ReviewsSelectLikeItem
 * @date : 25. 3. 31.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class ReviewsSelectLikeItem {

    private final String myLike;
    private final Long hackLikeCount;
    private final Long noLikeCount;


    public static ReviewsSelectLikeItem empty() {
        return ReviewsSelectLikeItem.builder().build();
    }
    public static ReviewsSelectLikeItem of(Long userId, List<ReviewLike> reviewLikes) {

        if(CollectionUtils.isEmpty(reviewLikes)) {
            return ReviewsSelectLikeItem.empty();
        } else {

            ReviewLike myReviewLike =  reviewLikes.stream()
                    .filter(reviewLike -> reviewLike.getUser().getUserId().equals(userId))
                    .findFirst()
                    .orElse(null);

            return ReviewsSelectLikeItem.builder()
                    .myLike(ObjectUtils.isEmpty(myReviewLike) ? "NO_SELECTED" : myReviewLike.getLikeType().name())
                    .hackLikeCount(reviewLikes.stream().filter(reviewLike -> reviewLike.getLikeType().equals(LikeType.HACK_GONG_GAM)).count())
                    .noLikeCount((reviewLikes.stream().filter(reviewLike -> reviewLike.getLikeType().equals(LikeType.NO_GANG_GAM)).count())).build();
        }

    }

}
