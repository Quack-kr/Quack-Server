package org.quack.QUACKServer.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.photos.domain.Photos;
import org.quack.QUACKServer.photos.enums.PhotoEnum;
import org.quack.QUACKServer.photos.repository.PhotosRepository;
import org.quack.QUACKServer.restaurant.domain.Restaurant;
import org.quack.QUACKServer.restaurant.domain.RestaurantCategory;
import org.quack.QUACKServer.restaurant.dto.response.GetRestaurantInfoResponse;
import org.quack.QUACKServer.restaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.restaurant.service
 * @fileName : RestaurantManager
 * @date : 25. 6. 3.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantManager {
    private final RestaurantRepository restaurantRepository;
    private final PhotosRepository photosRepository;

    public GetRestaurantInfoResponse getRestaurantBasicInfo(Long restaurantId){
        Restaurant findRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("식당 정보 없음"));

        List<RestaurantCategory> restaurantCategories = findRestaurant.getRestaurantCategories();

        List<String> restaurantCategory = new ArrayList<>();

        if(!restaurantCategories.isEmpty()){
            for (RestaurantCategory category : restaurantCategories) {
                restaurantCategory.add(category.getRestaurantCategoryName().getDescription());
            }
        }

        String restaurantRepresentativePhoto = getRestaurantRepresentativePhoto(restaurantId);

        return GetRestaurantInfoResponse.of(
                findRestaurant.getRestaurantName(),
                findRestaurant.getDetailAddress(),
                restaurantCategory,
                restaurantRepresentativePhoto
        );
    }

    public String getRestaurantRepresentativePhoto(Long restaurantId) {

        Photos photos = photosRepository.findFirstByTargetIdAndPhotoType(restaurantId, PhotoEnum.PhotoType.RESTAURANT.name())
                .orElseThrow(() -> new IllegalArgumentException("이미지 정보 없음"));

        return photos.getImageUrl();

    }


    public boolean validateExistence(Long restaurantId){
        return restaurantRepository.existsById(restaurantId);
    }
}
