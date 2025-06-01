package org.quack.QUACKServer.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.user.dto.UpdateProfileRequest;
import org.quack.QUACKServer.user.dto.response.GetCustomerUserProfileResponse;
import org.quack.QUACKServer.user.service.CustomerUserService;
import org.springframework.web.bind.annotation.*;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.user.controller
 * @fileName : UserController
 * @date : 25. 4. 24.
 */
@RestController
@Slf4j
@RequestMapping(path = "")
@RequiredArgsConstructor
public class UserController {

    private final CustomerUserService customerUserService;

    /**
     * 마이 페이지 - 기본 정보 조회 API
     */
    @GetMapping("/my-page/profile")
    public GetCustomerUserProfileResponse getCustomerUserProfile() {
        return customerUserService.getCustomerUserProfile();
    }

    /**
     * 마이 페이지 - 프로필 이미지 조회
     */
    @GetMapping("/my-page/{profile_photos_id}")
    public ResponseDto<?> getProfilePhoto(@PathVariable("profile_photos_id") Long profileId) {
        return customerUserService.getCustomerUserProfilePhoto(profileId);
    }

    /**
     * 마이 페이지 - 프로필 수정
     */
    @PostMapping("/my-page/profile-update")
    public ResponseDto<?> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        return customerUserService.updateCustomerUserProfile(request);
    }

}
