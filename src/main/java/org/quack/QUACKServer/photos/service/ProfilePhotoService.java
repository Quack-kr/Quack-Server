package org.quack.QUACKServer.photos.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.core.error.constant.ErrorCode;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.core.error.exception.CommonException;
import org.quack.QUACKServer.photos.domain.Photos;
import org.quack.QUACKServer.photos.dto.PhotosFileDto;
import org.quack.QUACKServer.photos.dto.ProfileUploadRequest;
import org.quack.QUACKServer.photos.enums.PhotoEnum;
import org.quack.QUACKServer.photos.repository.PhotosRepository;
import org.quack.QUACKServer.photos.repository.PhotosS3Repository;
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
    public ResponseDto<?> upload(ProfileUploadRequest profileUploadRequest, Long customerUserId) {

        try {
            MultipartFile file = profileUploadRequest.photoFile();
            String fileName = PhotoEnum.PhotoType.DEFAULT_PROFILE.getValue() + UUID.randomUUID() + file.getOriginalFilename();

            PhotosFileDto fileUploadDto = PhotosFileDto.builder()
                    .photoFile(file)
                    .fileName(fileName)
                    .build();

            String fullPath = photosS3Repository.upload(fileUploadDto);

            Photos photos = Photos.builder()
                    .imageUrl(fullPath)
                    .targetId(customerUserId)
                    .sortOrder(1)
                    .photoType(PhotoEnum.PhotoType.DEFAULT_PROFILE.name())
                    .build();

            photosRepository.save(photos);

            return ResponseDto.successCreate(photos.getPhotosId());
        } catch (Exception e) {
            throw new CommonException(ErrorCode.SERVER_ERROR);
        }


    }
}
