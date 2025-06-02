package org.quack.QUACKServer.review.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.menu.dto.response.MenuEvalResponse;

import java.time.LocalDateTime;
import java.util.List;


@Builder(access = AccessLevel.PRIVATE)
public record ReviewWithRestaurantResponse(
        Long reviewId,
        String restaurantName,
        LocalDateTime reviewCreatedAt,
        String reviewContent,
        List<ReviewImageResponse> reviewImages,
        List<MenuEvalResponse> menuEvals,
        Long likeCount,
        Long dislikeCount
) {
    public static ReviewWithRestaurantResponse of(Long reviewId, String restaurantName, LocalDateTime reviewCreatedAt,
                                                  String reviewContent,
                                                  List<ReviewImageResponse> reviewImages,
                                                  List<MenuEvalResponse> menuEvals, Long likeCount,
                                                  Long dislikeCount) {

        return ReviewWithRestaurantResponse.builder()
                .restaurantName(restaurantName)
                .reviewCreatedAt(reviewCreatedAt)
                .reviewContent(reviewContent)
                .reviewImages(reviewImages)
                .menuEvals(menuEvals)
                .likeCount(likeCount)
                .dislikeCount(dislikeCount)
                .build();
    }
}
