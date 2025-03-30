package org.quack.QUACKServer.global.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.global.security.dto.ClientSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.security
 * @fileName : SessionContextHolder
 * @date : 25. 3. 29.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionContextHolder {

    public static boolean hasClientSession() {
        return getAuthentication() != null && getAuthentication().getPrincipal() instanceof ClientSession;
    }

    public static ClientSession getClientSession() {
        if(!hasClientSession()) {
            log.warn("인증 정보가 올바르지 않습니다.");
            return ClientSession.empty();
        }

        return (ClientSession) getAuthentication().getPrincipal();
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


}
