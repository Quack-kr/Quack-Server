package org.quack.QUACKServer.domain.photos.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.photos.dto
 * @fileName : ProfileUploadRequest
 * @date : 25. 5. 24.
 */
public record ProfileUploadRequest(
        @NotNull MultipartFile photoFile
) {
}
