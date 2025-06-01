package org.quack.QUACKServer.domain.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthContext;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthTokenBuilder;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthTokenValue;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.auth.dto.request.SignupRequest;
import org.quack.QUACKServer.domain.auth.dto.response.AuthResponse;
import org.quack.QUACKServer.domain.auth.enums.AuthEnum;
import org.quack.QUACKServer.domain.auth.enums.SignUpStatus;
import org.quack.QUACKServer.domain.auth.enums.TokenStatus;
import org.quack.QUACKServer.domain.user.domain.CustomerUser;
import org.quack.QUACKServer.domain.user.domain.CustomerUserMetadata;
import org.quack.QUACKServer.domain.user.domain.CustomerUserNicknameSequence;
import org.quack.QUACKServer.domain.user.repository.CustomerUserMetadataRepository;
import org.quack.QUACKServer.domain.user.repository.CustomerUserRepository;
import org.quack.QUACKServer.domain.user.repository.NicknameSequenceRepository;
import org.quack.QUACKServer.global.common.constant.QuackCode;
import org.quack.QUACKServer.global.common.dto.CommonResponse;
import org.quack.QUACKServer.global.common.dto.SocialAuthDto;
import org.quack.QUACKServer.global.error.exception.QuackGlobalException;
import org.quack.QUACKServer.global.external.redis.QuackAuthTokenManager;
import org.quack.QUACKServer.global.external.redis.RedisKeyManager;
import org.quack.QUACKServer.global.external.redis.repository.RedisDocument;
import org.quack.QUACKServer.global.security.jwt.JwtProvider;
import org.quack.QUACKServer.global.security.provider.AppleLoginAuthenticationProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.regex.Pattern;

import static org.quack.QUACKServer.global.common.constant.QuackCode.ExceptionCode.*;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.service
 * @fileName : AuthService
 * @date : 25. 4. 21.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final NicknameSequenceRepository nicknameSequenceRepository;

    private final CustomerUserMetadataRepository customerUserMetadataRepository;
    private final CustomerUserRepository customerUserRepository;
    private final AppleLoginAuthenticationProvider appleLoginAuthenticationProvider;
    private final JwtProvider jwtProvider;
    private static final Pattern REGX = Pattern.compile("^[가-힣a-zA-Z0-9]+(?: [가-힣a-zA-Z0-9]+)*$");
    private final QuackAuthTokenManager quackAuthTokenManager;

    @Transactional
    public AuthResponse signup(SignupRequest request, String idToken) {

        switch (request.providerType()) {
            case APPLE -> {
                SocialAuthDto socialAuthDto = appleLoginAuthenticationProvider.getSocialAuth(idToken);

                // 1. 닉네임 중복 검증
                validateNickName(request.nickname());

                CustomerUser user = CustomerUser.createBySocial(request.providerType(),
                        socialAuthDto.getProviderId(), socialAuthDto.getEmail(), request.nickname());

                updateNicknameSequence(request.nickname());

                customerUserRepository.save(user);

                CustomerUserMetadata userMetadata = CustomerUserMetadata.create(user.getCustomerUserId(),
                        request.privacyAgreed(), request.termsAgreed(), request.locationTermsAgreed());

                customerUserMetadataRepository.save(userMetadata);

                String accessToken = jwtProvider.generateToken(QuackUser.from(user));
                String refreshToken = jwtProvider.generateRefreshToken(QuackUser.from(user));

                return AuthResponse.of(accessToken, refreshToken);
            }
            default -> {
                throw new QuackGlobalException(INVALID_PROVIDER_TYPE);
            }
        }
    }

    public AuthResponse refreshToken(HttpServletRequest request) {
        String refreshToken = getRefreshToken(request);

        validateToken(refreshToken);

        String nickname = jwtProvider.extractNickname(refreshToken);
        Long customerUserId = jwtProvider.extractUserId(refreshToken);

        if(!StringUtils.hasText(nickname) || customerUserId == null) {
            throw new QuackGlobalException(INVALID_REFRESH_TOKEN);
        }

        QuackAuthTokenValue sessionToken = getRedisToken(nickname, customerUserId);

        if (sessionToken.isExpired() || !refreshToken.equals(sessionToken.refreshToken())) {
            quackAuthTokenManager.deleteTokenByKey(sessionToken.accessToken());
            throw new QuackGlobalException(INVALID_REFRESH_TOKEN);
        }

        String newAccessToken = jwtProvider.generateToken(sessionToken.quackUser());
        String newRefreshToken = jwtProvider.generateRefreshToken(sessionToken.quackUser());

        QuackAuthTokenValue tokenValue = QuackAuthTokenBuilder
                .build(QuackUser.builder().build(), newAccessToken, newRefreshToken, TokenStatus.ACTIVE);

        quackAuthTokenManager.deleteTokenByKey(tokenValue.accessToken());


        return AuthResponse.builder()
                .signUpStatus(SignUpStatus.REFRESH)
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();

    }

    private QuackAuthTokenValue getRedisToken(String nickname, Long customerUserId) {
        String authKey = RedisKeyManager.builder()
                .append(RedisDocument.hashKey.AUTH_TOKEN.getPrefix())
                .append(String.valueOf(customerUserId))
                .append(nickname)
                .build();

        return quackAuthTokenManager.findTokenByKey(authKey);
    }
    private void validateToken(String refreshToken) {
        try {
            if(!jwtProvider.isTokenValid(refreshToken)) {
                throw new QuackGlobalException(INVALID_REFRESH_TOKEN);
            }

        }catch (Exception e){
            throw new QuackGlobalException(INVALID_REFRESH_TOKEN);
        }
    }


    public String getRefreshToken(HttpServletRequest request) {
        String refreshToken = jwtProvider.resolveToken(request);

        if(refreshToken == null) {
            throw new QuackGlobalException(INVALID_REFRESH_TOKEN);
        }

        return refreshToken;
    }

    @Transactional
    public CommonResponse deleteCustomerUser() {

        if(QuackAuthContext.getCustomerUserId() == null) {
           throw new QuackGlobalException(INVALID_REFRESH_TOKEN);
        }

        CustomerUser customerUser = customerUserRepository.findById(QuackAuthContext.getCustomerUserId())
                .orElseThrow(() -> new QuackGlobalException(QuackCode.ExceptionCode.USER_NOT_FOUND));

        customerUserRepository.delete(customerUser);

        return CommonResponse.success(HttpStatus.CREATED, "회원 탈퇴가 완료되었습니다.", null);
    }

    @Transactional
    public void updateNicknameSequence(String nickname) {
        AuthEnum.NicknameColorPrefix nicknameColorPrefix = AuthEnum.NicknameColorPrefix.getByNickname(nickname);
        AuthEnum.NicknameMenuPrefix nicknameMenuPrefix = AuthEnum.NicknameMenuPrefix.getByNickname(nickname);

        if(nicknameColorPrefix != null && nicknameMenuPrefix != null) {
            Optional<CustomerUserNicknameSequence> nicknameSequence = nicknameSequenceRepository.findByColorPrefixAndMenuPrefix(nicknameColorPrefix, nicknameMenuPrefix);

            if(nicknameSequence.isPresent()) {
                nicknameSequence.get().increase();
            } else {
                nicknameSequenceRepository.save(
                        CustomerUserNicknameSequence.createBuilder()
                                .colorPrefix(nicknameColorPrefix)
                                .menuPrefix(nicknameMenuPrefix)
                                .build());
            }

        }
    }

    public CommonResponse validateNickName(String nickname) {

        if (nickname == null) {
            throw new QuackGlobalException(INVALID_NULL_NICKNAME);
        }
        if (nickname.isBlank()) {
            throw new QuackGlobalException(INVALID_BLANK_NICKNAME);
        }
        if (nickname.length() <= 2) {
            throw new QuackGlobalException(INVALID_SHORT_LENGTH_NICKNAME);
        }
        if (nickname.length() > 15) {
            throw new QuackGlobalException(INVALID_LONG_LENGTH_NICKNAME);
        }
        if (!REGX.matcher(nickname).matches()) {
            throw new QuackGlobalException(INVALID_PATTERN_NICKNAME);
        }

        if(customerUserRepository.existsByNickname(nickname)) {
            throw new QuackGlobalException(DUPLICATE_NICKNAME);
        }

        return CommonResponse.of("200","꽥에서 사용하실 이름이에요", HttpStatus.OK, "");

    }


}
