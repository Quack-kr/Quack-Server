package org.quack.QUACKServer.domain.review.dto.response;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
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
