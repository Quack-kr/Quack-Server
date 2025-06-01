package org.quack.QUACKServer.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.global.external.redis.RedisKeyManager;
import org.quack.QUACKServer.global.external.redis.repository.RedisDocument;
import org.quack.QUACKServer.global.security.enums.ProviderType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.PublicKey;
import java.util.*;
import java.util.concurrent.TimeUnit;
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
    private static final String USER_ID = "userId";
    private static final String PROVIDER = "provider";
    private static final String PROVIDER_ID = "providerId";
    private static final String NICKNAME = "nickname";
    private static final String EMAIL = "email";


    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public Long extractUserId(String accessToken) {
        return extractClaim(accessToken, claims -> claims.get(USER_ID, Long.class));
    }

    public String resolveToken(HttpServletRequest request) {
        final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        return bearerToken.substring(TOKEN_PREFIX.length()).trim();

    }
    public boolean isTokenValid(String token) {
        return extractUserId(token) != null && !isTokenExpired(token);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = extractAllClaims(accessToken);

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
    public Claims parseClaims(String token, PublicKey publicKey) {

     return Jwts
             .parserBuilder()
             .setSigningKey(publicKey)
             .build()
             .parseClaimsJws(token)
             .getBody();
    }

    public String getAuthKey(String jwt) {
        String nickname = extractNickname(jwt);
        Long customerUserId = extractUserId(jwt);
        return RedisKeyManager.builder()
                .append(RedisDocument.hashKey.AUTH_TOKEN.getPrefix())
                .append(String.valueOf(customerUserId))
                .append(nickname)
                .build();
    }
    public String extractNickname(String accessToken) {
        return extractClaim(accessToken, claims -> claims.get(NICKNAME, String.class));
    }

    public UserDetails extractUserDetails(String token) {
        return extractClaim(token,claims -> QuackUser.builder()
                .customerUserId(claims.get(USER_ID, Long.class))
                .nickname(claims.get(NICKNAME, String.class))
                .email(claims.get(EMAIL, String.class))
                .provider(ProviderType.from(claims.get(PROVIDER, String.class)))
                .build());
    }

    public String generateToken(QuackUser quackUser) {
        return generateToken(new HashMap<>(), quackUser);
    }
    public String generateRefreshToken(QuackUser quackUser) {
        return buildToken(new HashMap<>(), quackUser, TimeUnit.MINUTES.toMillis(refreshTokenExpiration));
    }

    public String generateToken(Map<String, Object> extraClaims, QuackUser userDetails) {
        return buildToken(extraClaims, userDetails, TimeUnit.MINUTES.toMillis(accessTokenExpiration));
    }

    public String buildToken(Map<String, Object> extraClaims, QuackUser userDetails, long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .claim(AUTHORITIES_KEY, userDetails.getAuthorities())
                .claim(USER_ID, userDetails.getCustomerUserId())
                .claim(PROVIDER, userDetails.getProvider())
                .claim(NICKNAME, userDetails.getNickname())
                .claim(EMAIL, userDetails.getEmail())
                .setSubject(String.valueOf(userDetails.getCustomerUserId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
}