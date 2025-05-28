package org.quack.QUACKServer.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.domain.menu.dto.response.GetReviewMenusResponse;
import org.quack.QUACKServer.domain.restaurant.dto.response.GetRestaurantInfoResponse;

@JsonInclude(Include.NON_EMPTY)
@Builder(access = AccessLevel.PRIVATE)
public record ReviewInitResponse(
        GetRestaurantInfoResponse restaurantInfo,
        List<GetReviewMenusResponse> menus
) {
    public static ReviewInitResponse of(GetRestaurantInfoResponse restaurantInfo, List<GetReviewMenusResponse> menus) {
        return ReviewInitResponse.builder()
                .restaurantInfo(restaurantInfo)
                .menus(menus)
                .build();
    }
}
