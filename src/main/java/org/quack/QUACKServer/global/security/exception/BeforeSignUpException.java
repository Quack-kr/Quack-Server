package org.quack.QUACKServer.global.security.exception;

import lombok.Getter;
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

    private final SocialAuthDto socialAuthDto;

    public BeforeSignUpException(String msg, Throwable cause, SocialAuthDto socialAuthDto) {
        super(msg, cause);
        this.socialAuthDto = socialAuthDto;
    }

    public BeforeSignUpException(String msg, SocialAuthDto socialAuthDto) {
        super(msg);
        this.socialAuthDto = socialAuthDto;
    }
}
