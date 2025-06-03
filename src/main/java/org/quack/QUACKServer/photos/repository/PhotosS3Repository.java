package org.quack.QUACKServer.photos.repository;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.photos.dto.PhotosFileDto;
import org.quack.QUACKServer.core.error.exception.CommonException;
import org.quack.QUACKServer.core.external.s3.repository.S3Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.quack.QUACKServer.core.error.constant.ErrorCode.PHOTO_UPLOAD_ERROR;


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
    public String upload(PhotosFileDto photosFileDto) {

        try{
            ObjectMetadata metadata = new ObjectMetadata();

            metadata.setContentLength(photosFileDto.getPhotoFile().getInputStream().available());

            s3Client.putObject(bucket, photosFileDto.getFileName(), photosFileDto.getPhotoFile().getInputStream(), metadata);

            return URLDecoder.decode(s3Client.getUrl(bucket, photosFileDto.getFileName()).toString(), StandardCharsets.UTF_8);

        }catch (IOException e){
            throw new CommonException(PHOTO_UPLOAD_ERROR);
        }
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

    public Resource get(PhotosFileDto photosFileDto) {
        String path = convertPath(photosFileDto);

        S3Object file = s3Client.getObject(bucket, path);
        S3ObjectInputStream inputStream = file.getObjectContent();

        return new InputStreamResource(inputStream);

    }
}
