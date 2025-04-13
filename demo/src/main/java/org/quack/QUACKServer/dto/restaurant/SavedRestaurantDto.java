package org.quack.QUACKServer.dto.restaurant;

public record SavedRestaurantDto(
        Long restaurantId,
        String representativeImage,
        String restaurantName,
        String category,
        String simpleDescription,
        Long distance,
        Long averagePrice,
        boolean isOpen,
        String latitude,
        String longitude
) { }
