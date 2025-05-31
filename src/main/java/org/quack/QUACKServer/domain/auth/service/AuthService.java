package org.quack.QUACKServer.domain.auth.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.auth.dto.request.SignupRequest;
import org.quack.QUACKServer.domain.auth.dto.response.AuthResponse;
import org.quack.QUACKServer.domain.auth.enums.AuthEnum;
import org.quack.QUACKServer.domain.user.domain.CustomerUser;
import org.quack.QUACKServer.domain.user.domain.CustomerUserMetadata;
import org.quack.QUACKServer.domain.user.domain.NicknameSequence;
import org.quack.QUACKServer.domain.user.repository.CustomerUserMetadataRepository;
import org.quack.QUACKServer.domain.user.repository.CustomerUserRepository;
import org.quack.QUACKServer.domain.user.repository.NicknameSequenceRepository;
import org.quack.QUACKServer.global.common.dto.CommonResponse;
import org.quack.QUACKServer.global.common.dto.SocialAuthDto;
import org.quack.QUACKServer.global.security.jwt.JwtProvider;
import org.quack.QUACKServer.global.security.provider.AppleLoginAuthenticationProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

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
                // TODO : 공통 예외처리 로직 적용
                throw new ValidationException("Invalid client type");
            }
        }


    }

    public void updateNicknameSequence(String nickname) {
        AuthEnum.NicknameColorPrefix nicknameColorPrefix = AuthEnum.NicknameColorPrefix.getByNickname(nickname);
        AuthEnum.NicknameMenuPrefix nicknameMenuPrefix = AuthEnum.NicknameMenuPrefix.getByNickname(nickname);

        if(nicknameColorPrefix != null && nicknameMenuPrefix != null) {
            Optional<NicknameSequence> nicknameSequence = nicknameSequenceRepository.findByColorPrefixAndMenuPrefix(nicknameColorPrefix, nicknameMenuPrefix);

            if(nicknameSequence.isPresent()) {
                nicknameSequence.get().increase();
            } else {
                nicknameSequenceRepository.save(
                        NicknameSequence.createBuilder()
                                .colorPrefix(nicknameColorPrefix)
                                .menuPrefix(nicknameMenuPrefix)
                                .build());
            }

        }
    }

    public CommonResponse validateNickName(String nickname) {

        if (nickname == null) {
            throw new IllegalArgumentException("닉네임을 입력하세요.");
        }
        if (nickname.isBlank()) {
            throw new IllegalArgumentException("닉네임을 입력하세요.");
        }
        if (nickname.length() <= 2) {
            throw new IllegalArgumentException("3자이상 입력해주세요.");
        }
        if (nickname.length() > 15) {
            throw new IllegalArgumentException("15자미만 입력해주세요.");
        }
        if (!REGX.matcher(nickname).matches()) {
            throw new IllegalArgumentException("닉네임은 한글만 가능합니다.");
        }

        if(customerUserRepository.existsByNickname(nickname)) {
            throw new ValidationException("사용중인 닉네임이에요");
        }

        return CommonResponse.of("200","꽥에서 사용하실 이름이에요", HttpStatus.OK, "");

    }


}
