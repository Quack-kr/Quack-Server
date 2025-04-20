package org.quack.QUACKServer.domain.auth.domain;

import lombok.Getter;
import org.quack.QUACKServer.global.security.enums.ClientType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

/**
 * @author : jung-kwanhee
 * @description :
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

    public QuackAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object details, ClientType clientType, String accessToken, String idToken) {
        super(authorities);
        this.clientType = clientType;
        this.accessToken = accessToken;
        // details 는 QuackUser
        super.setDetails(details);
        this.idToken = idToken;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return super.implies(subject);
    }

}

