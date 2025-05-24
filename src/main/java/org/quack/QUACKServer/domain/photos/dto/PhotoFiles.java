package org.quack.QUACKServer.domain.photos.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.photos.dto
 * @fileName : PhotoFiles
 * @date : 25. 5. 24.
 */
public record PhotoFiles(
        List<MultipartFile> photoFiles
) {
}
