package org.quack.QUACKServer.dto.restaurant;

import java.util.List;

public record SubtractionSearchDto(
        Long restaurantId,
        String representativeImage,
        String restaurantName,
        String category,
        String simpleDescription,
        double distance,
        double averagePrice,
        List<String> menuPhotos,
        String operatingInfo,
        boolean isSaved,
        String latitude,
        String longitude
) { }
