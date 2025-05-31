package org.quack.QUACKServer.domain.review.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.domain.menu.dto.response.MenuEvalResponse;


@Builder(access = AccessLevel.PRIVATE)
public record ReviewWithRestaurantResponse(
        String restaurantName,
        LocalDateTime reviewCreatedAt,
        String reviewContent,
        List<ReviewImageResponse> reviewImages,
        List<MenuEvalResponse> menuEvals,
        Long likeCount,
        Long dislikeCount
) {
    public static ReviewWithRestaurantResponse of(String restaurantName, LocalDateTime reviewCreatedAt,
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
