package org.quack.QUACKServer.demo.dto.restaurant;

public record RestaurantBasicInfoDto(
        Long restaurantId,
        String restaurantName,
        String representativeImage,
        String address,
        String category,
        String simpleDescription,
        String detailDescription,
        String latitude,
        String longitude
) { }
