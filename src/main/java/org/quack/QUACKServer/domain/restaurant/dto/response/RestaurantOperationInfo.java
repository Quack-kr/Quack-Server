package org.quack.QUACKServer.domain.restaurant.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record RestaurantOperationInfo(
        Boolean isOpened,
        List<DayOperatingInfo> dayOperationInfoList
) {
    public static RestaurantOperationInfo of(Boolean isOpened, List<DayOperatingInfo> dayOperationInfoList) {
        return RestaurantOperationInfo.builder()
                .isOpened(isOpened)
                .dayOperationInfoList(dayOperationInfoList)
                .build();
    }
}
