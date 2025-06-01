package org.quack.QUACKServer.review.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record ReviewInfoResponse(
        Long reviewId,
        String reviewContent,
        LocalDateTime reviewCreatedAt,
        Long likeCount,
        Long dislikeCount,
        String restaurantName
) {
    public static ReviewInfoResponse of(Long reviewId, String reviewContent, LocalDateTime reviewCreatedAt,
                                        Long likeCount, Long dislikeCount, String restaurantName) {
        return ReviewInfoResponse.builder()
                .reviewId(reviewId)
                .reviewContent(reviewContent)
                .reviewCreatedAt(reviewCreatedAt)
                .likeCount(likeCount)
                .dislikeCount(dislikeCount)
                .restaurantName(restaurantName)
                .build();
    }
}
