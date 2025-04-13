package org.quack.QUACKServer.service;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.dto.restaurant.SavedRestaurantDto;
import org.quack.QUACKServer.repository.SavedRestaurantRepository;
import org.quack.QUACKServer.util.DistanceCalculator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SavedRestaurantService {

    private final SavedRestaurantRepository savedRestaurantRepository;
    private final RestaurantOperatingService operatingService;

    public int getSavedRestaurantCountByUserId(Long userId) {
        return savedRestaurantRepository.getCountByUser_UserId(userId);
    }

    public Page<SavedRestaurantDto> getSavedRestaurantsByUserId(Long userId,
                                                                int pageNum, int pageSize,
                                                                double userLatitude, double userLongitude) {

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<SavedRestaurantDto> savedRestaurants = savedRestaurantRepository.findSavedRestaurantsByUserId(userId, pageable);

        return savedRestaurants.map(dto -> {
            double restaurantLatitude = Double.parseDouble(dto.latitude());
            double restaurantLongitude = Double.parseDouble(dto.longitude());
            double kmDistance = DistanceCalculator.haversineCalculateDistance(userLatitude, userLongitude, restaurantLatitude,
                    restaurantLongitude);
            long distance = (long) (kmDistance * 1000);

            boolean isOpen = operatingService.isRestaurantOpen(dto.restaurantId());

            return new SavedRestaurantDto(
                    dto.restaurantId(),
                    dto.representativeImage(),
                    dto.restaurantName(),
                    dto.category(),
                    dto.simpleDescription(),
                    distance,
                    dto.averagePrice(),
                    isOpen,
                    dto.latitude(),
                    dto.longitude()
            );
        });
    }

    public boolean isRestaurantSaved(Long userId, Long restaurantId) {
        if (userId != null) {
            return savedRestaurantRepository.existsByUser_UserIdAndRestaurant_RestaurantId(userId, restaurantId);
        }
        return false;
    }
}
