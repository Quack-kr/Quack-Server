package org.quack.QUACKServer.domain.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthContext;
import org.quack.QUACKServer.domain.photos.domain.Photos;
import org.quack.QUACKServer.domain.photos.dto.PhotosFileDto;
import org.quack.QUACKServer.domain.photos.enums.PhotoEnum;
import org.quack.QUACKServer.domain.photos.repository.PhotosRepository;
import org.quack.QUACKServer.domain.photos.repository.PhotosS3Repository;
import org.quack.QUACKServer.domain.restaurant.domain.CustomerSavedRestaurant;
import org.quack.QUACKServer.domain.restaurant.domain.Restaurant;
import org.quack.QUACKServer.domain.restaurant.dto.response.GetRestaurantInfoResponse;
import org.quack.QUACKServer.domain.restaurant.repository.CustomerSavedRestaurantRepository;
import org.quack.QUACKServer.domain.restaurant.repository.RestaurantRepository;
import org.quack.QUACKServer.global.common.dto.CommonResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final PhotosRepository photosRepository;
    private final PhotosS3Repository photosS3Repository;
    private final CustomerSavedRestaurantRepository customerSavedRestaurantRepository;

    public GetRestaurantInfoResponse getRestaurantBasicInfo(Long restaurantId){
        Optional<Restaurant> findRestaurant = restaurantRepository.findById(restaurantId);

        Restaurant restaurant = findRestaurant.get();

        List<String> restaurantCategory = new ArrayList<>();

        for (String category : restaurantCategory) {
            restaurantCategory.add(category);
        }

        Resource restaurantRepresentativePhoto = getRestaurantRepresentativePhoto(restaurantId);

        return GetRestaurantInfoResponse.of(
                restaurant.getRestaurantName(),
                restaurant.getDetailAddress(),
                restaurantCategory,
                restaurantRepresentativePhoto
        );
    }

    @Transactional
    public CommonResponse updateCustomerUserRestaurant(Long restaurantId) {
        Long customerUserId = QuackAuthContext.getCustomerUserId();

        Optional<CustomerSavedRestaurant> customerSavedRestaurant = customerSavedRestaurantRepository
                .findByCustomerUserIdAndRestaurantId(customerUserId,restaurantId);

        if(customerSavedRestaurant.isPresent()){
            customerSavedRestaurantRepository.delete(customerSavedRestaurant.get());
        } else {
            customerSavedRestaurantRepository.save(CustomerSavedRestaurant.create(customerUserId, restaurantId));
        }

        return CommonResponse.of("201", "해당 식당 저장을 완료했습니다.", HttpStatus.CREATED, "");

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
