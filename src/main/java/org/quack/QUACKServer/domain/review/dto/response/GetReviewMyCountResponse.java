package org.quack.QUACKServer.domain.review.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.review.dto
 * @fileName : GetReviewMyCountResponse
 * @date : 25. 5. 27.
 */

@Builder(access = AccessLevel.PRIVATE)
public record GetReviewMyCountResponse(
        Long reviewCount
) {

    public static GetReviewMyCountResponse from(Long reviewCount) {
        return GetReviewMyCountResponse.builder().reviewCount(reviewCount).build();
    }
}
