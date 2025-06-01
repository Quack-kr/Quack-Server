package org.quack.QUACKServer.core.error.exception;

import lombok.Getter;
import org.quack.QUACKServer.core.common.constant.ErrorCode;

@Getter
public class CommonException extends RuntimeException {
    private final ErrorCode errorCode;

    public CommonException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
}
