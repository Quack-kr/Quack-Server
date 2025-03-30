package org.quack.QUACKServer.global.jwt;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
/**
 * @author : changhyun9
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.jwt
 * @fileName : UserAuthentication
 * @date : 25. 3. 29.
 */

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    public UserAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities){
        super(principal, credentials, authorities);
    }
}
