package org.quack.QUACKServer.global.security.exception;

import lombok.Getter;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.global.common.dto.SocialAuthDto;
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

    private final QuackUser quackUser;

    public BeforeSignUpException(String msg, Throwable cause, QuackUser quackUser) {
        super(msg, cause);
        this.quackUser = quackUser;
    }

    public BeforeSignUpException(String msg, QuackUser quackUser) {
        super(msg);
        this.quackUser = quackUser;
    }
}
