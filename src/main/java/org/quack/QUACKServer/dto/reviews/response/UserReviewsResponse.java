package org.quack.QUACKServer.dto.reviews.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.domain.Review;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.dto.reviews
 * @fileName : UserReviewsResponse
 * @date : 25. 3. 30.
 */
@Builder(access = AccessLevel.PRIVATE)
public record UserReviewsResponse(

) {

    public static UserReviewsResponse from(List<Review> reviews) {
        return UserReviewsResponse.builder().build();
    }
}
