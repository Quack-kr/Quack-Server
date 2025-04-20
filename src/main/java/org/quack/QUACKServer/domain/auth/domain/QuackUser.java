package org.quack.QUACKServer.domain.auth.domain;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.quack.QUACKServer.domain.user.domain.User;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.domain
 * @fileName : QuackUser
 * @date : 25. 4. 16.
 */

@ToString
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuackUser implements UserDetails {

    @Setter
    private Long userId;
    private String provideId;
    @Setter
    private Boolean isMarketingCheck;
    @Setter
    private String nickname;
    @Setter
    private String email;
    @Setter
    private boolean isSignUp;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return provideId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    public static QuackUser from (User user) {
        return QuackUser.builder()
                .userId(user.getUserId())
                .provideId(user.getSocialId())
                .email(user.getEmail())
                .isSignUp(user.isSignUp())
                .build();
    }

    public static QuackUser empty(String provideId) {
        return QuackUser.builder()
                .provideId(provideId)
                .isSignUp(false)
                .build();
    }

    public boolean isBeforeSignUp() {
        return !isSignUp;
    }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() {
        return true;
    }
}

