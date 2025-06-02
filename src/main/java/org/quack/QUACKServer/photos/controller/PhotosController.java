package org.quack.QUACKServer.photos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.core.error.constant.ErrorCode;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.core.error.exception.CommonException;
import org.quack.QUACKServer.photos.dto.ProfileUploadRequest;
import org.quack.QUACKServer.photos.service.ProfilePhotoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.photos.controller
 * @fileName : PhotosController
 * @date : 25. 5. 24.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/photos")
public class PhotosController {

    private final ProfilePhotoService profilePhotoService;

    @PostMapping(path = "/upload/profile-default")
    public ResponseDto<?> createDefaultProfileImage(
            @AuthenticationPrincipal CustomerUserInfo customerUserInfo,
            @Valid @ModelAttribute ProfileUploadRequest request) {

        if(customerUserInfo == null) {
            throw new CommonException(ErrorCode.UNAUTHORIZED_USER);
        }
        return profilePhotoService.upload(request, customerUserInfo.getCustomerUserId());
    }


}
