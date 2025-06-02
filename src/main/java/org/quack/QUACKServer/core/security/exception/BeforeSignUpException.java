package org.quack.QUACKServer.core.security.exception;

import lombok.Getter;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.springframework.security.core.AuthenticationException;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.security.exception
 * @fileName : BeforeSignUpException
 * @date : 25. 4. 20.
 */
@Getter
public class BeforeSignUpException extends AuthenticationException {

    private final CustomerUserInfo customerUserInfo;

    public BeforeSignUpException(String msg, Throwable cause, CustomerUserInfo customerUserInfo) {
        super(msg, cause);
        this.customerUserInfo = customerUserInfo;
    }

    public BeforeSignUpException(String msg, CustomerUserInfo customerUserInfo) {
        super(msg);
        this.customerUserInfo = customerUserInfo;
    }
}
