package org.quack.QUACKServer.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.Restaurant;
import org.quack.QUACKServer.repository.SavedRestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SavedRestaurantService {

    private final SavedRestaurantRepository savedRestaurantRepository;

    public int getSavedRestaurantCountByUserId(Long userId) {
        return savedRestaurantRepository.getCountByUserId(userId);
    }

    public List<Restaurant> getSavedRestaurantsByUserId(Long userId) {
        // 요청 확인하고, paging 하기
        return savedRestaurantRepository.findAllByUserId(userId);
    }

    public boolean isRestaurantSaved(Long userId, Long restaurantId) {
        if (userId != null) {
            return savedRestaurantRepository.existsByUserIdAndRestaurantId(userId, restaurantId);
        }
        return false;
    }
}
