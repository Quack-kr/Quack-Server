package org.quack.QUACKServer.restaurant.dto.response;

import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record DayOperatingInfo(
        LocalTime openTime,
        LocalTime closeTime,
        LocalTime lastOrderTime,
        LocalTime breakStart,
        LocalTime breakEnd,
        LocalTime breakLastOrderTime,
        Boolean isHoliday
) {
    public static DayOperatingInfo of(LocalTime openTime, LocalTime closeTime, LocalTime lastOrderTime,
                                      LocalTime breakStart, LocalTime breakEnd, LocalTime breakLastOrderTime,
                                      Boolean isHoliday) {
        return DayOperatingInfo.builder()
                .openTime(openTime)
                .closeTime(closeTime)
                .lastOrderTime(lastOrderTime)
                .breakStart(breakStart)
                .breakEnd(breakEnd)
                .breakLastOrderTime(breakLastOrderTime)
                .isHoliday(isHoliday)
                .build();
    }

}
