package org.quack.QUACKServer.photos.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.photos.domain.Photos;
import org.quack.QUACKServer.photos.dto.PhotosFileDto;
import org.quack.QUACKServer.photos.dto.ReviewPhotoUploadRequest;
import org.quack.QUACKServer.photos.enums.PhotoEnum.PhotoType;
import org.quack.QUACKServer.photos.repository.PhotosRepository;
import org.quack.QUACKServer.photos.repository.PhotosS3Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewPhotoService implements PhotoService<Object, ReviewPhotoUploadRequest>{

    private final PhotosS3Repository photosS3Repository;
    private final PhotosRepository photosRepository;

    @Override
    public ResponseDto<?> upload(ReviewPhotoUploadRequest reviewPhotoUploadRequest, Long customerUserId) {

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

        return ResponseDto.successCreate(null);
    }
}
