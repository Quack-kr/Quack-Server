package org.quack.QUACKServer.global.security.dto;

import ch.qos.logback.core.net.server.Client;
import lombok.*;
import org.quack.QUACKServer.demo.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : jung-kwanhee
 * @description : Client Session -> 인증 시 가지고 있는 객체
 * @packageName : org.quack.QUACKServer.global.security.dto
 * @fileName : ClientSession
 * @date : 25. 3. 29.
 */

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientSession implements UserDetails, OAuth2User {

    private Set<SimpleGrantedAuthority> authorities;
    private Map<String, Object> attributes;
    private User user;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return user.getNickname();
    }


    @Override
    public String getName() {
        return "";
    }

    public static ClientSession empty() {
        return new ClientSession();
    }

}
