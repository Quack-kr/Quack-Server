package org.quack.QUACKServer.common;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.common
 * @fileName : CommonController
 * @date : 25. 4. 14.
 */
@RestController
@RequiredArgsConstructor
public class CommonController {

    @GetMapping("/common/health-check")
    public ResponseDto<?> healthCheck() {
        return ResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .isSuccess(true)
                .message("헬스체크 OK")
                .build();
    }
}
