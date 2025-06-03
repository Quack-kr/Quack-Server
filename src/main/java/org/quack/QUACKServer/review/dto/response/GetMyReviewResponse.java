package org.quack.QUACKServer.review.dto.response;

import org.springframework.data.domain.Slice;

public record GetMyReviewResponse(
        Slice<ReviewWithRestaurantResponse> reviews,
        String userNickname,
        String userProfile
) {
    public static GetMyReviewResponse of(Slice<ReviewWithRestaurantResponse> reviews, String userNickname,
                                         String userProfile) {
        return new GetMyReviewResponse(reviews, userNickname, userProfile);
    }
}
