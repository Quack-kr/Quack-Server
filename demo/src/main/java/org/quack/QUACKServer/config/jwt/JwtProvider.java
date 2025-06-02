package org.quack.QUACKServer.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access.expiration-time}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh.expiration-time}")
    private long refreshTokenExpiration;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(Long userId) {
        return createToken(userId, accessTokenExpiration, "access");
    }

    public String createRefreshToken(Long userId) {
        return createToken(userId, refreshTokenExpiration, "refresh");
    }

    private String createToken(Long userId, Long expiration, String tokenType) {

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(generateClaims(userId, expiration, tokenType))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims generateClaims(Long userId, Long expiration, String tokenType) {
        long now = System.currentTimeMillis();
        final Claims claims = Jwts.claims()
                .setSubject(tokenType)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiration));
        claims.put("userId", userId);
        return claims;
    }

    protected SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

}