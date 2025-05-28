package org.quack.QUACKServer.domain.photos.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder(access = AccessLevel.PRIVATE)
public record ReviewPhotoUploadRequest(
        Long reviewId,
        List<MultipartFile> reviewPhotos
) {
    public static ReviewPhotoUploadRequest of(Long reviewId, List<MultipartFile> reviewPhotos) {
        return ReviewPhotoUploadRequest.builder()
                .reviewId(reviewId)
                .reviewPhotos(reviewPhotos)
                .build();
    }
}
