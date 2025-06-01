package org.quack.QUACKServer.core.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.quack.QUACKServer.auth.domain.CustomerUserInfo;
import org.quack.QUACKServer.auth.domain.JwtTokenDto;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.PublicKey;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class JwtUtil implements InitializingBean {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access.expiration-time}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh.expiration-time}")
    private long refreshTokenExpiration;

    private Key key;

    public static final String BEARER_PREFIX = "Bearer ";
    private static final String USER_ID = "customerUserId";
    private static final String EMAIL = "email";

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String email, Long customerUserId, Long expirationTime) {

        Claims claims = Jwts.claims();
        claims.put(EMAIL, email);
        claims.put(USER_ID, customerUserId);

        Date now = new Date();
        Date tokenValidateTime = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(tokenValidateTime)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public JwtTokenDto generateToken(String email, Long customerUserId) {
        return JwtTokenDto
                .create(customerUserId,
                        createToken(email, customerUserId, accessTokenExpiration),
                        createToken(email, customerUserId, refreshTokenExpiration)
                );
    }

    public Long getCustomerUserIdByToken(String token) {
        return getClaimsFromToken(token).get(USER_ID, Long.class);
    }

    public String getEmailByToken(String token) {
        return getClaimsFromToken(token).get(EMAIL, String.class);
    }
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
    }

    public CustomerUserInfo getQuackUserByToken(String token){

        final Claims claims = getClaimsFromToken(token);

        return CustomerUserInfo.builder()
                .customerUserId(claims.get(USER_ID, Long.class))
                .email(claims.get(EMAIL, String.class))
                .build();
    }

    public String getTokenByRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(BEARER_PREFIX);
        if(!StringUtils.isEmpty(bearerToken)) {
            return bearerToken;
        } else {
            return null;
        }
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaimsByPublicKey(String idToken, PublicKey publicKey) {
     return Jwts
             .parserBuilder()
             .setSigningKey(publicKey)
             .build()
             .parseClaimsJws(idToken)
             .getBody();
    }


}