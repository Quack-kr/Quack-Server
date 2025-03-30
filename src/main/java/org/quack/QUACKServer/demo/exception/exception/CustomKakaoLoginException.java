package org.quack.QUACKServer.demo.exception.exception;

import lombok.Getter;
import org.quack.QUACKServer.demo.exception.common.BaseCustomException;
import org.quack.QUACKServer.demo.exception.common.ErrorCode;


@Getter
public class CustomKakaoLoginException extends BaseCustomException {
    public CustomKakaoLoginException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
