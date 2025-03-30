package org.quack.QUACKServer.demo.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.demo.domain.Restaurant;
import org.quack.QUACKServer.demo.repository.SavedRestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SavedRestaurantService {

    private final SavedRestaurantRepository savedRestaurantRepository;

    public int getSavedRestaurantCountByUserId(Long userId) {
        return savedRestaurantRepository.getCountByUser_UserId(userId);
    }

    public List<Restaurant> getSavedRestaurantsByUserId(Long userId) {
        // 요청 확인하고, paging 하기
        return savedRestaurantRepository.findAllByUser_UserId(userId);
    }

    public boolean isRestaurantSaved(Long userId, Long restaurantId) {
        if (userId != null) {
            return savedRestaurantRepository.existsByUser_UserIdAndRestaurant_RestaurantId(userId, restaurantId);
        }
        return false;
    }
}
