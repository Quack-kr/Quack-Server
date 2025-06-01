package org.quack.QUACKServer.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthContext;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.auth.enums.AuthEnum;
import org.quack.QUACKServer.domain.auth.service.AuthService;
import org.quack.QUACKServer.domain.photos.domain.Photos;
import org.quack.QUACKServer.domain.photos.enums.PhotoEnum;
import org.quack.QUACKServer.domain.photos.repository.PhotosRepository;
import org.quack.QUACKServer.domain.user.domain.CustomerUser;
import org.quack.QUACKServer.domain.user.domain.CustomerUserMetadata;
import org.quack.QUACKServer.domain.user.dto.UpdateProfileRequest;
import org.quack.QUACKServer.domain.user.dto.response.GetCustomerUserProfileResponse;
import org.quack.QUACKServer.domain.user.repository.CustomerUserMetadataRepository;
import org.quack.QUACKServer.domain.user.repository.CustomerUserRepository;
import org.quack.QUACKServer.domain.user.repository.NicknameSequenceRepository;
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

        QuackUser quackUser = QuackAuthContext.getQuackUserDetails();

        CustomerUser customerUser = customerUserRepository.findById(quackUser.getCustomerUserId())
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

        QuackUser quackUser = QuackAuthContext.getQuackUserDetails();

        CustomerUser customerUser = customerUserRepository.findById(quackUser.getCustomerUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저 정보를 찾을 수 없습니다."));

        CustomerUserMetadata customerUserMetadata = customerUserMetadataRepository.findByCustomerUserId(quackUser.getCustomerUserId())
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
