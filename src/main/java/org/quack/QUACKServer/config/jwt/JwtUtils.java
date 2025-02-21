package org.quack.QUACKServer.config.jwt;

import static org.quack.QUACKServer.config.jwt.JwtValidationType.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProvider jwtProvider;

    public JwtValidationType validateJwt(String token) {
        try {
            Claims claims = getClaims(token);
            String tokenType = claims.getSubject();

            if (tokenType.equals("access")) {
                return ACCESS_VALID;
            }
            return REFRESH_VALID;

        } catch (ExpiredJwtException e) {
            if (getClaims(token).getSubject().equals("access")) {
                return ACCESS_EXPIRED;
            }
            return REFRESH_EXPIRED;

        } catch (MalformedJwtException e) {
            return INVALID_FORMAT_TOKEN;

        } catch (SecurityException e) {
            return ILLEGAL_SIGNATURE_TOKEN;

        } catch (UnsupportedJwtException e) {
            return UNSUPPORTED_TOKEN;

        } catch (IllegalArgumentException e) {
            return ILLEGAL_TOKEN;
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
