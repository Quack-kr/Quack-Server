package org.quack.QUACKServer.domain.photos.repository;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.photos.dto.PhotosFileDto;
import org.quack.QUACKServer.global.infra.s3.repository.S3Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.photos.service
 * @fileName : PhotosS3Manager
 * @date : 25. 5. 24.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PhotosS3Repository implements S3Repository<PhotosFileDto, String> {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 s3Client;

    @Override
    public String upload(PhotosFileDto photosFileDto) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentLength(photosFileDto.getPhotoFile().getInputStream().available());

        s3Client.putObject(bucket, photosFileDto.getFileName(), photosFileDto.getPhotoFile().getInputStream(), metadata);

        return URLDecoder.decode(s3Client.getUrl(bucket, photosFileDto.getFileName()).toString(), StandardCharsets.UTF_8);

    }

    @Override
    public void delete(PhotosFileDto photosFileDto) {
        s3Client.deleteObject(bucket, photosFileDto.getKeyName());
    }

    @Override
    public String convertPath(PhotosFileDto photosFileDto) {
        Date expiration = new Date();
        long expirationMills = expiration.getTime();
        expirationMills += 1000 * 60 * 2;
        expiration.setTime(expirationMills);

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, photosFileDto.getKeyName())
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        URL url = s3Client.generatePresignedUrl(request);

        return url.toString();

    }
}
