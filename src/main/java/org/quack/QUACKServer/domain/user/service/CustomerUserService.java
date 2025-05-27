package org.quack.QUACKServer.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthContext;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.photos.domain.Photos;
import org.quack.QUACKServer.domain.photos.dto.PhotosFileDto;
import org.quack.QUACKServer.domain.photos.enums.PhotoEnum;
import org.quack.QUACKServer.domain.photos.repository.PhotosRepository;
import org.quack.QUACKServer.domain.photos.repository.PhotosS3Repository;
import org.quack.QUACKServer.domain.user.domain.CustomerUser;
import org.quack.QUACKServer.domain.user.domain.CustomerUserMetadata;
import org.quack.QUACKServer.domain.user.dto.response.GetCustomerUserProfileResponse;
import org.quack.QUACKServer.domain.user.repository.CustomerUserMetadataRepository;
import org.quack.QUACKServer.domain.user.repository.CustomerUserRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.user.service
 * @fileName : CustomerUserService
 * @date : 25. 5. 25.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerUserService {


    private final CustomerUserRepository customerUserRepository;
    private final CustomerUserMetadataRepository customerUserMetadataRepository;
    private final PhotosRepository photosRepository;
    private final PhotosS3Repository photosS3Repository;

    public GetCustomerUserProfileResponse getCustomerUserProfile() {

        QuackUser quackUser = QuackAuthContext.getQuackUserDetails();

        CustomerUser customerUser = customerUserRepository.findById(quackUser.getCustomerUserId())
                .orElseThrow(() -> new IllegalStateException("유저 정보를 찾을 수 없습니다."));

        CustomerUserMetadata customerUserMetadata = customerUserMetadataRepository.findByCustomerUserId(customerUser.getCustomerUserId())
                .orElseThrow(() -> new IllegalStateException("유저 정보를 찾을 수 없습니다."));

        return GetCustomerUserProfileResponse.of(customerUser, customerUserMetadata);
    }

    public Resource getCustomerUserProfilePhoto(Long profileId) {

        Photos photos = photosRepository.findFirstByTargetIdAndPhotoType(profileId, PhotoEnum.PhotoType.DEFAULT_PROFILE.name())
                .orElseThrow(() -> new IllegalArgumentException("이미지 정보 없음"));

        return photosS3Repository.get(PhotosFileDto.builder().keyName(photos.getImageUrl()).build());


    }
}
