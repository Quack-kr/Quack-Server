package org.quack.QUACKServer.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.auth.domain.PrincipalManager;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.auth.enums.AuthEnum;
import org.quack.QUACKServer.auth.service.AuthService;
import org.quack.QUACKServer.photos.domain.Photos;
import org.quack.QUACKServer.photos.enums.PhotoEnum;
import org.quack.QUACKServer.photos.repository.PhotosRepository;
import org.quack.QUACKServer.user.domain.CustomerUser;
import org.quack.QUACKServer.user.domain.CustomerUserMetadata;
import org.quack.QUACKServer.user.dto.UpdateProfileRequest;
import org.quack.QUACKServer.user.dto.response.GetCustomerUserProfileResponse;
import org.quack.QUACKServer.user.repository.CustomerUserMetadataRepository;
import org.quack.QUACKServer.user.repository.CustomerUserRepository;
import org.quack.QUACKServer.user.repository.NicknameSequenceRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Objects;

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
    private final NicknameSequenceRepository nicknameSequenceRepository;
    private final AuthService authService;

    public GetCustomerUserProfileResponse getCustomerUserProfile() {

        CustomerUserInfo customerUserInfo = PrincipalManager.getCustomerUserInfo();

        CustomerUser customerUser = customerUserRepository.findById(customerUserInfo.getCustomerUserId())
                .orElseThrow(() -> new IllegalStateException("유저 정보를 찾을 수 없습니다."));

        CustomerUserMetadata customerUserMetadata = customerUserMetadataRepository.findByCustomerUserId(customerUser.getCustomerUserId())
                .orElseThrow(() -> new IllegalStateException("유저 정보를 찾을 수 없습니다."));

        return GetCustomerUserProfileResponse.of(customerUser, customerUserMetadata);
    }

    public ResponseDto<?> getCustomerUserProfilePhoto(Long profileId) {

        Photos photos = photosRepository.findFirstByTargetIdAndPhotoType(profileId, PhotoEnum.PhotoType.DEFAULT_PROFILE.name())
                .orElseThrow(() -> new IllegalArgumentException("이미지 정보 없음"));

        return ResponseDto.success(photos.getImageUrl());
    }

    @Transactional
    public ResponseDto<?> updateCustomerUserProfile(UpdateProfileRequest request) {

        CustomerUserInfo customerUserInfo = PrincipalManager.getCustomerUserInfo();

        CustomerUser customerUser = customerUserRepository.findById(customerUserInfo.getCustomerUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저 정보를 찾을 수 없습니다."));

        CustomerUserMetadata customerUserMetadata = customerUserMetadataRepository.findByCustomerUserId(customerUserInfo.getCustomerUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저 정보를 찾을 수 없습니다."));

        authService.validateNickName(request.nickname());

        customerUser.updateNickname(request.nickname());

        authService.updateNicknameSequence(request.nickname());

        if(request.profileImageId() != null && request.profileImageId() > 0) {
            customerUserMetadata.updateProfileImageId(request.profileImageId());
        }
        
        return ResponseDto.successCreate(null);
    }

    public String generateNickname() {
        LocalDateTime now = LocalDateTime.now();

        DayOfWeek dayOfWeek = now.getDayOfWeek();
        Integer currentHour = now.getHour();

        AuthEnum.NicknameColorPrefix colorPrefix = AuthEnum.NicknameColorPrefix.of(dayOfWeek);
        AuthEnum.NicknameMenuPrefix menuPrefix = AuthEnum.NicknameMenuPrefix.of(currentHour);

        long seq = nicknameSequenceRepository.findMaxSequenceByPrefix(colorPrefix, menuPrefix);

        return Objects.requireNonNull(colorPrefix).getDescription() + Objects.requireNonNull(menuPrefix).getDescription() + String.format("%04d", seq);

    }
}
