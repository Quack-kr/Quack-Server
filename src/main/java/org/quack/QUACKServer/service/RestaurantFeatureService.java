package org.quack.QUACKServer.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.dto.restaurant.RestaurantFeatureDto;
import org.quack.QUACKServer.repository.RestaurantFeatureRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantFeatureService {
    private final RestaurantFeatureRepository featureRepository;

    public List<RestaurantFeatureDto> getFeatureInfo(Long restaurantId) {
        List<RestaurantFeatureDto> featureInfo = featureRepository.findFeaturesByRestaurantId(
                restaurantId);
        return featureInfo;
    }
}
