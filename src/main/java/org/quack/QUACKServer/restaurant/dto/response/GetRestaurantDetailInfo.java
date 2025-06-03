package org.quack.QUACKServer.restaurant.dto.response;

import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.menu.dto.response.BestMenu;
import org.quack.QUACKServer.menu.dto.response.MenuSimpleInfo;
import org.quack.QUACKServer.review.dto.response.ReviewEvalSummaryRank;
import org.quack.QUACKServer.review.dto.response.ReviewSimpleInfo;


@Builder(access = AccessLevel.PRIVATE)
public record GetRestaurantDetailInfo(
        RestaurantInfo restaurantInfo,
        List<String> restaurantImages,
        Map<String, DayOperatingInfo> restaurantOperationInfo,
        List<ReviewEvalSummaryRank> negativeKeywordList,
        List<ReviewEvalSummaryRank> positiveKeywordList,
        List<BestMenu> bestMenuList,
        List<MenuSimpleInfo> menuInfoList,
        Long reviewCount,
        List<ReviewSimpleInfo> top3Review,
        Boolean isSaved
) {
    public static GetRestaurantDetailInfo of(RestaurantInfo restaurantInfo,
                                             List<String> restaurantImages,
                                             Map<String, DayOperatingInfo> restaurantOperationInfo,
                                             List<ReviewEvalSummaryRank> negativeKeywordList,
                                             List<ReviewEvalSummaryRank> positiveKeywordList,
                                             List<BestMenu> bestMenuList,
                                             List<MenuSimpleInfo> menuInfoList,
                                             Long reviewCount,
                                             List<ReviewSimpleInfo> top3Review,
                                             Boolean isSaved)
    {
        return GetRestaurantDetailInfo.builder()
                .restaurantInfo(restaurantInfo)
                .restaurantImages(restaurantImages)
                .restaurantOperationInfo(restaurantOperationInfo)
                .negativeKeywordList(negativeKeywordList)
                .positiveKeywordList(positiveKeywordList)
                .bestMenuList(bestMenuList)
                .menuInfoList(menuInfoList)
                .reviewCount(reviewCount)
                .top3Review(top3Review)
                .isSaved(isSaved)
                .build();
    }
}
