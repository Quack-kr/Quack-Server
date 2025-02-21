package org.quack.QUACKServer.exception.exception;

import lombok.Getter;
import org.quack.QUACKServer.exception.common.BaseCustomException;
import org.quack.QUACKServer.exception.common.ErrorCode;


@Getter
public class CustomKakaoLoginException extends BaseCustomException {
    public CustomKakaoLoginException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
