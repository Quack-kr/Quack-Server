package org.quack.QUACKServer.global.common.dto;

import lombok.Getter;
import org.quack.QUACKServer.global.common.constant.QuackCode;
import org.quack.QUACKServer.global.common.constant.QuackCode.ExceptionCode;

@Getter
public class CommonExceptionResponse {
    private final QuackCode.ExceptionCode exceptionCode;
    private final String customMessaged;

    public CommonExceptionResponse(ExceptionCode exceptionCode, String customMessaged) {
        this.exceptionCode = exceptionCode;
        this.customMessaged = customMessaged;
    }
}
