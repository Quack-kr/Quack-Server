package org.quack.QUACKServer.exception.exception;

import org.quack.QUACKServer.exception.common.BaseCustomException;
import org.quack.QUACKServer.exception.common.ErrorCode;

public class CustomUserException extends BaseCustomException {

    public CustomUserException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
