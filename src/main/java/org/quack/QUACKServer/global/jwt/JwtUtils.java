package org.quack.QUACKServer.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author : changhyun9
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.jwt
 * @fileName : JwtUtils
 * @date : 25. 3. 29.
 */

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProvider jwtProvider;

    public JwtValidationType validateJwt(String token) {
        try {
            Claims claims = getClaims(token);
            String tokenType = claims.getSubject();

            if (tokenType.equals("access")) {
                return JwtValidationType.ACCESS_VALID;
            }
            return JwtValidationType.REFRESH_VALID;

        } catch (ExpiredJwtException e) {
            if (getClaims(token).getSubject().equals("access")) {
                return JwtValidationType.ACCESS_EXPIRED;
            }
            return JwtValidationType.REFRESH_EXPIRED;

        } catch (MalformedJwtException e) {
            return JwtValidationType.INVALID_FORMAT_TOKEN;

        } catch (SecurityException e) {
            return JwtValidationType.ILLEGAL_SIGNATURE_TOKEN;

        } catch (UnsupportedJwtException e) {
            return JwtValidationType.UNSUPPORTED_TOKEN;

        } catch (IllegalArgumentException e) {
            return JwtValidationType.ILLEGAL_TOKEN;
        }
    }

    public Long getUserIdFromJwt(String token) {
        Claims claim = getClaims(token);
        return claim.get("userId", Long.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProvider.getSignKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

}
