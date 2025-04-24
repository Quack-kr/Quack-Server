package org.quack.QUACKServer.domain.auth.domain;

import lombok.Getter;
import org.quack.QUACKServer.global.security.enums.ClientType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

/**
 * @author : jung-kwanhee
 * @description : 인증 전 필터를 타기 위한 객체
 *
 * @packageName : org.quack.QUACKServer.global.security.token
 * @fileName : QuackAuthenticationToken
 * @date : 25. 4. 15.
 */

@Getter
public class QuackAuthenticationToken extends AbstractAuthenticationToken {

    private final ClientType clientType;
    private final String accessToken;
    private final String idToken;

    public QuackAuthenticationToken(ClientType clientType, String accessToken, String idToken) {
        super(null);
        this.clientType = clientType;
        this.accessToken = accessToken;
        this.idToken = idToken;
        setAuthenticated(false);
    }

    public QuackAuthenticationToken(QuackUser details,
                                    ClientType clientType,
                                    String accessToken,
                                    String idToken) {
        super(details.getAuthorities());
        this.clientType = clientType;
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
    public QuackUser getPrincipal() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return super.implies(subject);
    }

}

