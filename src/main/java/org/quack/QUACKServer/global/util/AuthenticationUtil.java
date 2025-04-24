package org.quack.QUACKServer.global.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.util
 * @fileName : AutheticationUtil
 * @date : 25. 4. 20.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationUtil {

    public static boolean isSignUp(QuackUser user) {
        return false;
    }

}