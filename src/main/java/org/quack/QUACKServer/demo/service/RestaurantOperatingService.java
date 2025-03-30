package org.quack.QUACKServer.demo.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.demo.domain.RestaurantOperating;
import org.quack.QUACKServer.demo.domain.common.DayOfWeek;
import org.quack.QUACKServer.demo.dto.restaurant.OperatingInfoDto;
import org.quack.QUACKServer.demo.repository.RestaurantOperatingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantOperatingService {
    private final RestaurantOperatingRepository operatingRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public List<OperatingInfoDto> getOperatingInfo(Long restaurantId) {
        List<OperatingInfoDto> operatingInfo = operatingRepository.findOperatingInfoByRestaurantId(restaurantId);
        return operatingInfo;
    }

    public boolean isRestaurantOpen(Long restaurantId) {
        java.time.DayOfWeek day = LocalDate.now().getDayOfWeek();

        DayOfWeek operatingDay = DayOfWeek.valueOf(day.name());

        Optional<RestaurantOperating> operatingOpt = operatingRepository.findByRestaurant_RestaurantIdAndDayOfWeek(
                restaurantId, operatingDay);

        // 식당 정보가 없을 경우
        if (!operatingOpt.isPresent()) {
            return false;
        }

        RestaurantOperating operatingInfo = operatingOpt.get();

        // 휴무인 경우
        if (operatingInfo.getIsHoliday().equals(Boolean.TRUE)) {
            return false;
        }

        LocalTime nowTime = LocalTime.now();
        LocalTime startTime = LocalTime.parse(operatingInfo.getStartTime(), formatter);
        LocalTime endTime = LocalTime.parse(operatingInfo.getEndTime(), formatter);

        // 현재 시간이 운영 시간 전이나 후 인 경우
        if (nowTime.isBefore(startTime) || nowTime.isAfter(endTime)) {
            return false;
        }

        // 식당 브레이크 타임 확인
        if (operatingInfo.getBreakStartTime() != null && operatingInfo.getBreakEndTime() != null) {
            LocalTime breakStart = LocalTime.parse(operatingInfo.getBreakStartTime(), formatter);
            LocalTime breakEnd = LocalTime.parse(operatingInfo.getBreakEndTime(), formatter);
            if (!nowTime.isBefore(breakStart) && !nowTime.isAfter(breakEnd)) {
                return false;
            }
        }

        return true;
    }

}
