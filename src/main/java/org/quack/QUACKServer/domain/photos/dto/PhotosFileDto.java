package org.quack.QUACKServer.domain.photos.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.photos.dto
 * @fileName : PhotosFileDto
 * @date : 25. 5. 24.
 */

@Getter
@Builder
public class PhotosFileDto {

    private MultipartFile photoFile;
    private String fileName;
    private String keyName;
}
