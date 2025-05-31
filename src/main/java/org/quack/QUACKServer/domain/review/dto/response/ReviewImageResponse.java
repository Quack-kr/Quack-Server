package org.quack.QUACKServer.domain.review.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ReviewImageResponse(
        String reviewImagePath
) {
    public static ReviewImageResponse from(String reviewImagePath) {
        return ReviewImageResponse.builder()
                .reviewImagePath(reviewImagePath)
                .build();
    }
}
