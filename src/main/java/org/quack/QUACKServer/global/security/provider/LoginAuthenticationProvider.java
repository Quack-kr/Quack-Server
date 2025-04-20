package org.quack.QUACKServer.global.security.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;
import org.quack.QUACKServer.global.common.dto.SocialAuthDto;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.text.ParseException;
import java.util.Base64;
import java.util.Map;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.security.handler
 * @fileName : LoginAuthenticationProvider
 * @date : 25. 4. 15.
 */
public interface LoginAuthenticationProvider extends AuthenticationProvider {
//    Authentication authenticate(String idToken);

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

