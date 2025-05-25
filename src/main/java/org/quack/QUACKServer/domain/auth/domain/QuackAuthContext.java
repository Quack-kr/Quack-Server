package org.quack.QUACKServer.domain.auth.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.util
 * @fileName : QuackAuthContext
 * @date : 25. 4. 25.
 */

@Slf4j
public class QuackAuthContext {

    public static Long getCustomerUserId() {
        return getAuthenticatedUser() == null ? null : getAuthenticatedUser().getCustomerUserId();
    }

    public static QuackUser getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof UsernamePasswordAuthenticationToken
                && auth.getPrincipal() instanceof QuackUser quackUser) {
            return quackUser;
        }

        return null;
    }

    public static boolean isAnonymous() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth instanceof AnonymousAuthenticationToken;
    }


    public static QuackUser getQuackUserDetails() {
        return getAuthenticatedUser();
    }

    public static boolean hasUserDetails() {
        return getAuthenticatedUser() != null;
    }
}
