package org.quack.QUACKServer.global.common.exception;

import lombok.Getter;
import org.quack.QUACKServer.demo.exception.common.ErrorCode;
import org.quack.QUACKServer.global.common.constans.QuackCode;

/**
 * @author : changhyun9
 * @description :
 * @packageName : org.quack.QUACKServer.global.common.exception
 * @fileName : BaseCustomException
 * @date : 25. 3. 29.
 */
@Getter
public class BaseCustomException extends RuntimeException{

    private final QuackCode errorCode;

    public BaseCustomException(QuackCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    /**
     * Exception 생성 비용은 Stack Trace의 경로가 차지한다. depth 10 당 4000ns가 소요되며
     * 일반적으로 예외 생성에 1~5ms가 소비된다. 성능 개선을 위해 stack trace하는 fillInStackTrace()
     * 메서드를 오버라이딩하여 80ns 정도로 성능 향상할 수 있다.
     * 추후, trace가 필요한 경우 제거하기.
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

