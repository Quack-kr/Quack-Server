package org.quack.QUACKServer.global.error.exception;

import org.quack.QUACKServer.global.common.constant.QuackCode;
import org.quack.QUACKServer.global.common.constant.QuackCode.ExceptionCode;

public class BaseException extends RuntimeException {
    private QuackCode.ExceptionCode exceptionCode;

    public BaseException(ExceptionCode exceptionCode) {
        super(exceptionCode.getDescription());
        this.exceptionCode = exceptionCode;
    }
}
