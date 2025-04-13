package org.quack.QUACKServer.dto.restaurant;

public record NormalSearchDto(
        Long restaurantId,
        String representativeImage,
        String restaurantName,
        String category,
        String simpleDescription,
        double distance,
        double averagePrice,
        boolean isOpen
) { }
