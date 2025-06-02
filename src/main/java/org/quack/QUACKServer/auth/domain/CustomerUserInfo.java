package org.quack.QUACKServer.auth.domain;

import lombok.*;
import org.quack.QUACKServer.auth.enums.Role;
import org.quack.QUACKServer.user.domain.CustomerUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.domain
 * @fileName : CustomerUserInfo
 * @date : 25. 4. 16.
 */

@ToString
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUserInfo implements UserDetails {

    @Setter
    private Long customerUserId;
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
        return List.of(new SimpleGrantedAuthority(Role.USER.name()));
    }

    public static CustomerUserInfo from (CustomerUser user) {
        return CustomerUserInfo.builder()
                .customerUserId(user.getCustomerUserId())
                .email(user.getEmail())
                .build();
    }

    public static CustomerUserInfo empty() {
        return CustomerUserInfo.builder()
                .build();
    }

    public boolean isEmpty() {
        return customerUserId == null && email == null ;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() {
        return true;
    }
}

