package org.quack.QUACKServer.demo.dto.restaurant;

import org.quack.QUACKServer.demo.domain.common.DayOfWeek;

public record OperatingInfoDto(
        DayOfWeek dayOfWeek,
        String startTime,
        String endTime,
        String breakStartTime,
        String breakEndTime,
        String lastOrderTime,
        boolean isHoliday
) { }
