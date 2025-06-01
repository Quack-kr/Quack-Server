package org.quack.QUACKServer.domain.photos.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.photos.dto.ProfileUploadRequest;
import org.quack.QUACKServer.domain.photos.service.ProfilePhotoService;
import org.quack.QUACKServer.global.common.dto.CommonResponse;
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
    public CommonResponse createDefaultProfileImage(@Valid @ModelAttribute ProfileUploadRequest request) {
        return profilePhotoService.upload(request);
    }


}
