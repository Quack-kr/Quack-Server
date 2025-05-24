package org.quack.QUACKServer.domain.photos.controller;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.photos.dto.ProfileUploadRequest;
import org.quack.QUACKServer.domain.photos.service.ProfilePhotoService;
import org.quack.QUACKServer.global.common.dto.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(path = "/upload/profile-default")
    public CommonResponse createDefaultProfileImage(ProfileUploadRequest request) {
        return profilePhotoService.upload(request);
    }


}
