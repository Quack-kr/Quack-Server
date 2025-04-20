package org.quack.QUACKServer.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKey;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.function.Function;


@Component
@RequiredArgsConstructor
public class JwtProvider {
    public static final String TOKEN_PREFIX = "Bearer ";
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access.expiration-time}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh.expiration-time}")
    private long refreshTokenExpiration;
    private static final String AUTHORITIES_KEY = "auth";
//
//    @PostConstruct
//    protected void init() {
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
//    }
//
//    public String createAccessToken(Long userId) {
//        return createToken(userId, accessTokenExpiration, "access");
//    }
//
//    public String createRefreshToken(Long userId) {
//        return createToken(userId, refreshTokenExpiration, "refresh");
//    }
//
//    private String createToken(Long userId, Long expiration, String tokenType) {
//
//        return Jwts.builder()
//                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
//                .setClaims(generateClaims(userId, expiration, tokenType))
//                .signWith(getSignKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    private Claims generateClaims(Long userId, Long expiration, String tokenType) {
//        long now = System.currentTimeMillis();
//        final Claims claims = Jwts.claims()
//                .setSubject(tokenType)
//                .setIssuedAt(new Date(now))
//                .setExpiration(new Date(now + expiration));
//        claims.put("userId", userId);
//        return claims;
//    }

//    protected SecretKey getSignKey() {
//        return Keys.hmacShaKeyFor(secretKey.getBytes());
//    }


    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String extractUser(String accessToken) {
        return extractClaim(accessToken, Claims::getSubject);
    }

    public String resolveToken(HttpServletRequest request) {
        final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        return bearerToken.substring(TOKEN_PREFIX.length()).trim();

    }
    public boolean isTokenValid(String token) {
        return !extractUser(token).isBlank() && !isTokenExpired(token);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = extractAllClaims(accessToken);

//        if(claims.get(AUTHORITIES_KEY) == null) {
//
//        }
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(
                        claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);

    }




    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    // APPLE 로그인 jwt 토큰 새 Claims
    public Claims parseClaims(String token, PublicKey publicKey) {
     return Jwts
             .parserBuilder()
             .setSigningKey(publicKey)
             .build()
             .parseClaimsJws(token)
             .getBody();
    }

    public String getAuthKey(String jwt) {
        String userNickname = extractUser(jwt);

        return userNickname;
    }

}