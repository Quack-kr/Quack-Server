package org.quack.QUACKServer.core.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.common.dto
 * @fileName : BaseResponse
 * @date : 25. 4. 20.
 */
@Builder
public record ResponseDto<T>(
        @JsonIgnore HttpStatus httpStatus,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp,
        @NotNull Boolean isSuccess,
        @NotNull String message,
        @Nullable T data
) {

    public static ResponseDto<?> success(Object data) {
        return ResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .isSuccess(true)
                .message("성공")
                .data(data)
                .build();
    }

    public static ResponseDto<?> successCreate(final Object data) {
        return ResponseDto.builder()
                .httpStatus(HttpStatus.CREATED)
                .timestamp(LocalDateTime.now())
                .isSuccess(true)
                .message("생성 성공")
                .data(data)
                .build();
    }

    public static ResponseDto<?> fail(final CommonExceptionResponse e) {
        return ResponseDto.builder()
                .httpStatus(e.code().getHttpStatus())
                .isSuccess(false)
                .message(e.customMessaged())
                .data(null)
                .build();
    }
}
