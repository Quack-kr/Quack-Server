package org.quack.QUACKServer.domain.auth.domain;

import lombok.*;
import org.quack.QUACKServer.domain.user.domain.CustomerUser;
import org.quack.QUACKServer.global.external.redis.RedisKeyManager;
import org.quack.QUACKServer.global.external.redis.repository.RedisDocument;
import org.quack.QUACKServer.global.security.enums.ProviderType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    private Long customerUserId;
    private ProviderType provider;
    private String nickname;
    private String email;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    public String getAuthKey() {
        return RedisKeyManager.builder()
                .append(RedisDocument.hashKey.AUTH_TOKEN.getPrefix())
                .append(String.valueOf(this.customerUserId))
                .append(this.nickname)
                .build();
    }

    public static QuackUser from (CustomerUser user) {
        return QuackUser.builder()
                .customerUserId(user.getCustomerUserId())
                .nickname(user.getNickname())
                .provider(user.getProvider())
                .email(user.getEmail())
                .build();
    }

    public static QuackUser empty() {
        return QuackUser.builder()
                .build();
    }

    public boolean isEmpty() {
        return customerUserId == null && nickname == null && email == null ;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() {
        return true;
    }
}

