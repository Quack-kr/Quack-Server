package org.quack.QUACKServer.demo.exception.exception;

import org.quack.QUACKServer.demo.exception.common.BaseCustomException;
import org.quack.QUACKServer.demo.exception.common.ErrorCode;

public class CustomUserException extends BaseCustomException {

    public CustomUserException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
