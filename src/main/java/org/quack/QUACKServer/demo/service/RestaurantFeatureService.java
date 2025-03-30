package org.quack.QUACKServer.demo.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.demo.dto.restaurant.RestaurantFeatureDto;
import org.quack.QUACKServer.demo.repository.RestaurantFeatureRepository;
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
