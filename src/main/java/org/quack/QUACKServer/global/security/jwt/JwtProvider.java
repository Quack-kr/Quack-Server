package org.quack.QUACKServer.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.security.PublicKey;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.global.security.enums.ClientType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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
    private static final String SOCIAL_TYPE = "socialType";
    private static final String SOCIAL_ID = "socialId";
    private static final String IS_SIGN_UP = "isSignUp";
    private static final String HAS_AGREED = "hasAgreed";
    private static final String NICKNAME = "nickname";
    private static final String EMAIL = "email";

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

    public String extractSocialId(String accessToken) {
        return extractClaim(accessToken, claims -> claims.get(SOCIAL_ID, String.class));
    }

    public String resolveToken(HttpServletRequest request) {
        final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        return bearerToken.substring(TOKEN_PREFIX.length()).trim();

    }
    public boolean isTokenValid(String token) {
        return !extractSocialId(token).isBlank() && !isTokenExpired(token);
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
        return extractSocialId(jwt);
    }

    public UserDetails extractUserDetails(String token) {
        return extractClaim(token,claims -> QuackUser.builder()
                .userId(claims.get(USER_ID, Long.class))
                .isSignUp(claims.get(IS_SIGN_UP, Boolean.class))
                .nickname(claims.get(NICKNAME, String.class))
                .email(claims.get(EMAIL, String.class))
                .socialType(ClientType.from(claims.get(SOCIAL_TYPE, String.class)))
                .isMarketingCheck(claims.get(HAS_AGREED, Boolean.class))
                .socialId(claims.get(SOCIAL_ID, String.class))
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
                .claim(USER_ID, userDetails.getUserId())
                .claim(SOCIAL_TYPE, userDetails.getSocialType())
                .claim(SOCIAL_ID, userDetails.getSocialId())
                .claim(IS_SIGN_UP, userDetails.isSignUp())
                .claim(HAS_AGREED, userDetails.getIsMarketingCheck())
                .claim(NICKNAME, userDetails.getNickname())
                .claim(EMAIL, userDetails.getEmail())
                .setSubject(userDetails.getSocialId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
}