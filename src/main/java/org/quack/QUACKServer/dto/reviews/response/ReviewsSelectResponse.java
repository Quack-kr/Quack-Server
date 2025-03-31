package org.quack.QUACKServer.dto.reviews.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.domain.Review;
import org.quack.QUACKServer.dto.reviews.ReviewsSelectItem;
import org.quack.QUACKServer.dto.reviews.request.ReviewsSelectRequest;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.dto.reviews.response
 * @fileName : ReviewsSelectResponse
 * @date : 25. 3. 31.
 */

@Builder(access = AccessLevel.PRIVATE)
public record ReviewsSelectResponse(
        List<ReviewsSelectItem> reviews) {

    public static ReviewsSelectResponse of(Long userId, List<Review> reviews) {

        return ReviewsSelectResponse.builder()
                .reviews(reviews.stream()
                        .map(review -> ReviewsSelectItem.of(userId, review))
                        .toList())
                .build();
    }
}
