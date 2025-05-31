package org.quack.QUACKServer.domain.review.dto.response;

import org.springframework.data.domain.Slice;

public record MyReviewResponse(
        Slice<ReviewWithRestaurantResponse> reviews,
        String userNickname,
        Long userProfileId
) {
    public static MyReviewResponse of(Slice<ReviewWithRestaurantResponse> reviews, String userNickname,
                               Long userProfileId) {
        return new MyReviewResponse(reviews, userNickname, userProfileId);
    }
}
