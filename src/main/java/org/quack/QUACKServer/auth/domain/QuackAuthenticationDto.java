package org.quack.QUACKServer.auth.domain;

import lombok.Getter;
import org.quack.QUACKServer.core.security.enums.ProviderType;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.security.auth.Subject;

/**
 * @author : jung-kwanhee
 * @description : 인증 전 필터를 타기 위한 객체
 *
 * @packageName : org.quack.QUACKServer.global.security.token
 * @fileName : QuackAuthenticationDto
 * @date : 25. 4. 15.
 */

@Getter
public class QuackAuthenticationDto extends AbstractAuthenticationToken {

    private final ProviderType provider;
    private final String accessToken;
    private final String idToken;

    public QuackAuthenticationDto(ProviderType provider, String accessToken, String idToken) {
        super(null);
        this.provider = provider;
        this.accessToken = accessToken;
        this.idToken = idToken;
        setAuthenticated(false);
    }

    public QuackAuthenticationDto(CustomerUserInfo details,
                                  ProviderType provider,
                                  String accessToken,
                                  String idToken) {
        super(details.getAuthorities());
        this.provider = provider;
        super.setDetails(details);
        this.accessToken = accessToken;
        this.idToken = idToken;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public CustomerUserInfo getPrincipal() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return super.implies(subject);
    }

}

