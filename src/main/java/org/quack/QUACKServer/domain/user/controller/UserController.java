package org.quack.QUACKServer.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.user.dto.response.GetCustomerUserProfileResponse;
import org.quack.QUACKServer.domain.user.service.CustomerUserService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/my-page/{profile-id}")
    public ResponseEntity<Resource> getProfilePhoto(@PathVariable("profile-id") Long profileId) {
        Resource resource = customerUserService.getCustomerUserProfilePhoto(profileId);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // @GetMapping("/my-page/")
}
