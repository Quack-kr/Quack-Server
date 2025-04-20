package org.quack.QUACKServer.global.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.common.dto
 * @fileName : BaseResponse
 * @date : 25. 4. 20.
 */
@Builder
public record BaseResponse<T>(
        String path,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp,
        String message,
        String code,
        T data
) {
    public BaseResponse {
        timestamp = LocalDateTime.now();
    }
}
