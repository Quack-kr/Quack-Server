package org.quack.QUACKServer.global.security.provider;

import com.nimbusds.jwt.SignedJWT;
import org.quack.QUACKServer.global.common.dto.SocialAuthDto;
import org.springframework.security.authentication.AuthenticationProvider;

import java.text.ParseException;
import java.util.Map;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.security.handler
 * @fileName : LoginAuthenticationProvider
 * @date : 25. 4. 15.
 */

public interface LoginAuthenticationProvider extends AuthenticationProvider {

    SocialAuthDto getSocialAuth(String idToken);


    default Map<String, String> parseHeaders(String idToken) {

        try {
            SignedJWT signedJWT = SignedJWT.parse(idToken);
            return Map.of("kid", signedJWT.getHeader().getKeyID(), "alg", signedJWT.getHeader().getAlgorithm().getName());

        } catch ( ParseException e) {
            // TODO : 공통 예외 처리 로직으로 수정
            throw new RuntimeException(e);
        }
    }
}

