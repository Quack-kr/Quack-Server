package org.quack.QUACKServer.demo.exception.exception;

import org.quack.QUACKServer.demo.exception.common.BaseCustomException;
import org.quack.QUACKServer.demo.exception.common.ErrorCode;

public class CustomTokenException extends BaseCustomException {

    public CustomTokenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
