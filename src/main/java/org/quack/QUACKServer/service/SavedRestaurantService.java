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

    private final SavedRestaurantRepository restaurantRepository;

    public int getSavedRestaurantCountByUserId(Long userId) {
        return restaurantRepository.getCountByUserId(userId);
    }

    public List<Restaurant> getSavedRestaurantsByUserId(Long userId) {
        // 요청 확인하고, paging 하기
        return restaurantRepository.findAllByUserId(userId);
    }
}
