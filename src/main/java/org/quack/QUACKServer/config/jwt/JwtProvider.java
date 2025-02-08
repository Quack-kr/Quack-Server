package org.quack.QUACKServer.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 서비스에서 사용할 JWT 토큰을 생성하고, 검증하는 클래스
 */
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.accessToken.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refreshToken.expiration}")
    private long refreshTokenExpiration;

    /**
     * Access Token 생성
     */
    public String createAccessToken(Long userId, String roleType) {
        return createToken(userId, roleType, accessTokenExpiration);
    }

    /**
     * Refresh Token 생성
     */
    public String createRefreshToken(Long userId, String roleType) {
        return createToken(userId, roleType, refreshTokenExpiration);
    }

    private String createToken(Long userId, String roleType, long tokenValidity) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidity);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("role", roleType)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    //TODO: 토큰 검증/파싱 메서드 추가
}