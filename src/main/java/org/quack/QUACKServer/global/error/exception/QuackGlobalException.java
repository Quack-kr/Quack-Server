package org.quack.QUACKServer.global.error.exception;

import lombok.Getter;
import org.quack.QUACKServer.global.common.constant.QuackCode;
import org.quack.QUACKServer.global.common.constant.QuackCode.ExceptionCode;

@Getter
public class QuackGlobalException extends RuntimeException {
    private final QuackCode.ExceptionCode exceptionCode;

    public QuackGlobalException(ExceptionCode exceptionCode) {
        super(exceptionCode.getDescription());
        this.exceptionCode = exceptionCode;
    }
}
