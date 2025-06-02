package org.quack.QUACKServer.review.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.menu.dto.response.GetReviewMenusResponse;
import org.quack.QUACKServer.restaurant.dto.response.GetRestaurantInfoResponse;

@JsonInclude(Include.NON_EMPTY)
@Builder(access = AccessLevel.PRIVATE)
public record ReviewInitResponse(
        GetRestaurantInfoResponse restaurantInfo,
        Map<String, List<GetReviewMenusResponse>> menus
) {
    public static ReviewInitResponse of(GetRestaurantInfoResponse restaurantInfo,
                                        Map<String, List<GetReviewMenusResponse>> menus) {
        return ReviewInitResponse.builder()
                .restaurantInfo(restaurantInfo)
                .menus(menus)
                .build();
    }
}
