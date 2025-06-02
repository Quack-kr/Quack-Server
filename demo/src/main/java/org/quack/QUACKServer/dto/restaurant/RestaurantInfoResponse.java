package org.quack.QUACKServer.dto.restaurant;

import java.util.List;
import lombok.Builder;

@Builder
public record RestaurantInfoResponse(
        RestaurantBasicInfoDto restaurantBasicInfoDto,
        List<OperatingInfoDto> operatingInfo,
        List<MenuDto> menus,
        List<RestaurantFeatureDto> features,
        List<ReviewKeywordDto> top3PositiveKeywords,
        List<ReviewKeywordDto> top3NegativeKeywords,
        List<RecentReviewDto> top3RecentReviews,
        boolean isSaved,
        boolean isOpen
) { }
