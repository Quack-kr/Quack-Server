package org.quack.QUACKServer.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quack.QUACKServer.auth.dto.request.SignupRequest;
import org.quack.QUACKServer.auth.dto.response.AuthResponse;
import org.quack.QUACKServer.auth.enums.AuthEnum;
import org.quack.QUACKServer.auth.enums.SignUpStatus;
import org.quack.QUACKServer.core.error.constant.ErrorCode;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.core.common.dto.SocialAuthDto;
import org.quack.QUACKServer.core.error.exception.CommonException;
import org.quack.QUACKServer.core.external.redis.repository.AuthRedisRepository;
import org.quack.QUACKServer.core.security.enums.ProviderType;
import org.quack.QUACKServer.core.security.jwt.JwtUtil;
import org.quack.QUACKServer.core.security.provider.AppleLoginAuthenticationProvider;
import org.quack.QUACKServer.core.security.provider.KakaoLoginAuthenticationProvider;
import org.quack.QUACKServer.auth.domain.JwtTokenDto;
import org.quack.QUACKServer.user.domain.CustomerUser;
import org.quack.QUACKServer.user.domain.CustomerUserMetadata;
import org.quack.QUACKServer.user.domain.CustomerUserNicknameSequence;
import org.quack.QUACKServer.user.repository.CustomerUserMetadataRepository;
import org.quack.QUACKServer.user.repository.CustomerUserRepository;
import org.quack.QUACKServer.user.repository.NicknameSequenceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

import static org.quack.QUACKServer.core.error.constant.ErrorCode.*;

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
    private final JwtUtil jwtUtil;
    private static final Pattern REGX = Pattern.compile("^[가-힣a-zA-Z0-9]+(?: [가-힣a-zA-Z0-9]+)*$");
    private final KakaoLoginAuthenticationProvider kakaoLoginAuthenticationProvider;
    private final AuthRedisRepository authRedisRepository;

    @Transactional
    public AuthResponse signup(SignupRequest request, String idToken) {
        SocialAuthDto socialAuthDto = switch (request.providerType()) {
            case APPLE -> appleLoginAuthenticationProvider.getSocialAuth(idToken);
            case KAKAO -> kakaoLoginAuthenticationProvider.getSocialAuth(idToken);
            default -> throw new CommonException(ErrorCode.INVALID_PROVIDER_TYPE);
        };

        return processSignup(request, socialAuthDto);
    }

    private AuthResponse processSignup(SignupRequest request, SocialAuthDto socialAuthDto) {
        // 1. 닉네임 중복 검증
        validateNickName(request.nickname());
        validateProviderAndId(request.providerType(), socialAuthDto.getProviderId());

        // 2. 사용자 생성 및 저장
        CustomerUser user = CustomerUser.createBySocial(
                request.providerType(),
                socialAuthDto.getProviderId(),
                socialAuthDto.getEmail(),
                request.nickname()
        );
        updateNicknameSequence(request.nickname());
        customerUserRepository.save(user);

        // 3. 사용자 메타데이터 저장
        CustomerUserMetadata metadata = CustomerUserMetadata.create(
                user.getCustomerUserId(),
                request.privacyAgreed(),
                request.termsAgreed(),
                request.locationTermsAgreed()
        );
        customerUserMetadataRepository.save(metadata);

        // 4. JWT 토큰 발급 및 Redis 저장
        JwtTokenDto jwtToken = jwtUtil.generateToken(user.getEmail(), user.getCustomerUserId());
        authRedisRepository.insert(jwtToken.getRedisKey(), jwtToken);

        return AuthResponse.from(jwtToken);
    }


    public AuthResponse refreshToken(HttpServletRequest request) {
        String refreshToken = getRefreshToken(request);

        validateToken(refreshToken);

        Long customerUserId = jwtUtil.getCustomerUserIdByToken(refreshToken);
        String email = jwtUtil.getEmailByToken(refreshToken);

        if(customerUserId == null || StringUtils.isEmpty(email)) {
            throw new CommonException(INVALID_REFRESH_TOKEN);
        }

        JwtTokenDto newToken = jwtUtil.generateToken(email, customerUserId);

        authRedisRepository.delete(newToken.getRedisKey());
        authRedisRepository.insert(newToken.getRedisKey(), newToken);

        return AuthResponse.builder()
                .signUpStatus(SignUpStatus.REFRESH)
                .accessToken(newToken.accessToken())
                .refreshToken(newToken.refreshToken())
                .build();

    }


    private void validateToken(String refreshToken) {
        try {
            if(!jwtUtil.isValidToken(refreshToken)) {
                throw new CommonException(INVALID_REFRESH_TOKEN);
            }

        }catch (Exception e){
            throw new CommonException(INVALID_REFRESH_TOKEN);
        }
    }


    public String getRefreshToken(HttpServletRequest request) {
        String refreshToken = jwtUtil.getTokenByRequest(request);

        if(refreshToken == null) {
            throw new CommonException(INVALID_REFRESH_TOKEN);
        }

        return refreshToken;
    }

    @Transactional
    public ResponseDto<?> deleteCustomerUser(Long customerUserId) {

        CustomerUser customerUser = customerUserRepository.findById(customerUserId)
                .orElseThrow(() -> new CommonException(ErrorCode.USER_NOT_FOUND));

        customerUserRepository.delete(customerUser);

        return ResponseDto.success(null);
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

    public ResponseDto<?> validateNickName(String nickname) {

        if (nickname == null) {
            throw new CommonException(INVALID_NULL_NICKNAME);
        }
        if (nickname.isBlank()) {
            throw new CommonException(INVALID_BLANK_NICKNAME);
        }
        if (nickname.length() <= 2) {
            throw new CommonException(INVALID_SHORT_LENGTH_NICKNAME);
        }
        if (nickname.length() > 15) {
            throw new CommonException(INVALID_LONG_LENGTH_NICKNAME);
        }
        if (!REGX.matcher(nickname).matches()) {
            throw new CommonException(INVALID_PATTERN_NICKNAME);
        }

        if(customerUserRepository.existsByNickname(nickname)) {
            throw new CommonException(DUPLICATE_NICKNAME);
        }

        return ResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .message("꽥에서 사용하실 이름이에요")
                .isSuccess(true)
                .build();
    }

    public void validateProviderAndId(ProviderType providerType, String providerId) {
        if (customerUserRepository.existsByProviderAndProviderId(providerType, providerId)) {
            throw new CommonException(DUPLICATE_USER);
        }
    }
}
