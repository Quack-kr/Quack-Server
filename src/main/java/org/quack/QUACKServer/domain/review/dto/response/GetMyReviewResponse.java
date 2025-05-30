package org.quack.QUACKServer.domain.review.dto.response;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Slice;

public record GetMyReviewResponse(
        Slice<ReviewWithRestaurantResponse> reviews,
        String userNickname,
        Resource userProfile
) {
    public static GetMyReviewResponse of(Slice<ReviewWithRestaurantResponse> reviews, String userNickname,
                                         Resource userProfile) {
        return new GetMyReviewResponse(reviews, userNickname, userProfile);
    }
}
