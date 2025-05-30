package org.quack.QUACKServer.domain.restaurant.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.photos.domain.Photos;
import org.quack.QUACKServer.domain.photos.dto.PhotosFileDto;
import org.quack.QUACKServer.domain.photos.enums.PhotoEnum;
import org.quack.QUACKServer.domain.photos.repository.PhotosRepository;
import org.quack.QUACKServer.domain.photos.repository.PhotosS3Repository;
import org.quack.QUACKServer.domain.restaurant.domain.Restaurant;
import org.quack.QUACKServer.domain.restaurant.domain.RestaurantCategory;
import org.quack.QUACKServer.domain.restaurant.dto.response.GetRestaurantInfoResponse;
import org.quack.QUACKServer.domain.restaurant.repository.RestaurantRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final PhotosRepository photosRepository;
    private final PhotosS3Repository photosS3Repository;

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

        Resource restaurantRepresentativePhoto = getRestaurantRepresentativePhoto(restaurantId);

        return GetRestaurantInfoResponse.of(
                findRestaurant.getRestaurantName(),
                findRestaurant.getDetailAddress(),
                restaurantCategory,
                restaurantRepresentativePhoto
        );
    }

    public Resource getRestaurantRepresentativePhoto(Long restaurantId) {

        Photos photos = photosRepository.findFirstByTargetIdAndPhotoType(restaurantId, PhotoEnum.PhotoType.RESTAURANT.name())
                .orElseThrow(() -> new IllegalArgumentException("이미지 정보 없음"));

        return photosS3Repository.get(PhotosFileDto.builder().keyName(photos.getImageUrl()).build());


    }

    public boolean validateExistence(Long restaurantId){
        return restaurantRepository.existsById(restaurantId);
    }


}
