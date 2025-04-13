package org.quack.QUACKServer.domain.common;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.global.common.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public CommonResponse healthCheck() {
        return CommonResponse.of(String.valueOf(HttpStatus.OK.value()), "헬스 체크 성공", HttpStatus.OK, null);
    }
}
