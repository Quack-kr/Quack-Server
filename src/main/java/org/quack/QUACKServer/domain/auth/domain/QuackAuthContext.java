package org.quack.QUACKServer.domain.auth.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.util
 * @fileName : QuackAuthContext
 * @date : 25. 4. 25.
 */

@Slf4j
public class QuackAuthContext {

    public Long getCustomerUserId() { return Objects.requireNonNull(getQuackUserDetails()).getCustomerUserId(); }

    public static QuackUser getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth instanceof UsernamePasswordAuthenticationToken) {
            return (QuackUser) auth.getPrincipal();
        }

        return null;
    }

    public static QuackUser getQuackUserDetails() {
        QuackUser userDetails = getAuthenticatedUser();
        if(userDetails != null) {
            return userDetails;
        }
        return null;
    }

    public static boolean hasUserDetails() {
        return getAuthenticatedUser() != null;
    }
}
