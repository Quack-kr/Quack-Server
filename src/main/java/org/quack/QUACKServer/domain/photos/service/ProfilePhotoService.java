package org.quack.QUACKServer.domain.photos.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthContext;
import org.quack.QUACKServer.domain.photos.domain.Photos;
import org.quack.QUACKServer.domain.photos.dto.PhotosFileDto;
import org.quack.QUACKServer.domain.photos.dto.ProfileUploadRequest;
import org.quack.QUACKServer.domain.photos.enums.PhotoEnum;
import org.quack.QUACKServer.domain.photos.repository.PhotosRepository;
import org.quack.QUACKServer.domain.photos.repository.PhotosS3Repository;
import org.quack.QUACKServer.global.common.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.photos.service
 * @fileName : ProfilePhotoService
 * @date : 25. 5. 24.
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfilePhotoService implements PhotoService<Object, ProfileUploadRequest> {

    private final PhotosS3Repository photosS3Repository;
    private final PhotosRepository photosRepository;

    @Override
    public CommonResponse upload(ProfileUploadRequest profileUploadRequest) {

        try{
            MultipartFile file = profileUploadRequest.photoFile();
            String fileName = PhotoEnum.PhotoType.DEFAULT_PROFILE.getValue() + UUID.randomUUID() + file.getOriginalFilename();

            PhotosFileDto fileUploadDto = PhotosFileDto.builder()
                    .photoFile(file)
                    .fileName(fileName)
                    .build();

            String fullPath = photosS3Repository.upload(fileUploadDto);

            Photos photos = Photos.builder()
                    .imageUrl(fullPath)
                    .targetId(QuackAuthContext.getCustomerUserId())
                    .sortOrder(1)
                    .photoType(PhotoEnum.PhotoType.DEFAULT_PROFILE.name())
                    .build();

            photosRepository.save(photos);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return CommonResponse.of("201", "파일 업로드 성공", HttpStatus.CREATED, "");
    }
}
