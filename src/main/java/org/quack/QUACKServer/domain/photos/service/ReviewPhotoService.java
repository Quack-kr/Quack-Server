package org.quack.QUACKServer.domain.photos.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthContext;
import org.quack.QUACKServer.domain.photos.domain.Photos;
import org.quack.QUACKServer.domain.photos.dto.PhotosFileDto;
import org.quack.QUACKServer.domain.photos.dto.ReviewPhotoUploadRequest;
import org.quack.QUACKServer.domain.photos.enums.PhotoEnum;
import org.quack.QUACKServer.domain.photos.enums.PhotoEnum.PhotoType;
import org.quack.QUACKServer.domain.photos.repository.PhotosRepository;
import org.quack.QUACKServer.domain.photos.repository.PhotosS3Repository;
import org.quack.QUACKServer.global.common.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewPhotoService implements PhotoService<Object, ReviewPhotoUploadRequest>{

    private final PhotosS3Repository photosS3Repository;
    private final PhotosRepository photosRepository;

    @Override
    public CommonResponse upload(ReviewPhotoUploadRequest reviewPhotoUploadRequest) {

        try{
            List<MultipartFile> multipartFiles = reviewPhotoUploadRequest.reviewPhotos();
            for (MultipartFile file : multipartFiles) {
                String fileName = PhotoType.REVIEW.getValue() + UUID.randomUUID() + file.getOriginalFilename();

                PhotosFileDto fileUploadDto = PhotosFileDto.builder()
                        .photoFile(file)
                        .fileName(fileName)
                        .build();

                String fullPath = photosS3Repository.upload(fileUploadDto);

                Photos photos = Photos.builder()
                        .imageUrl(fullPath)
                        .targetId(reviewPhotoUploadRequest.reviewId())
                        .sortOrder(1)
                        .photoType(PhotoType.REVIEW.name())
                        .build();

                photosRepository.save(photos);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return CommonResponse.of("201", "파일 업로드 성공", HttpStatus.CREATED, "");
    }
}
