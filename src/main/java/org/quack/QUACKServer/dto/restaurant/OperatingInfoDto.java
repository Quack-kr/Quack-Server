package org.quack.QUACKServer.dto.restaurant;

import org.quack.QUACKServer.domain.common.DayOfWeek;

public record OperatingInfoDto(
        DayOfWeek dayOfWeek,
        String startTime,
        String endTime,
        String breakStartTime,
        String breakEndTime,
        String lastOrderTime,
        boolean isHoliday
) { }
