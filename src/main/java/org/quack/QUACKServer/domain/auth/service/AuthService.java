package org.quack.QUACKServer.domain.auth.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.domain.auth.dto.request.SignupRequest;
import org.quack.QUACKServer.domain.auth.dto.response.CreateUserResponse;
import org.quack.QUACKServer.domain.user.domain.User;
import org.quack.QUACKServer.domain.user.repository.UserRepository;
import org.quack.QUACKServer.global.common.dto.CommonResponse;
import org.quack.QUACKServer.global.common.dto.SocialAuthDto;
import org.quack.QUACKServer.global.security.jwt.JwtProvider;
import org.quack.QUACKServer.global.security.provider.AppleLoginAuthenticationProvider;
import org.quack.QUACKServer.global.security.provider.PublicKeyProvider;
import org.quack.QUACKServer.global.util.validation.NicknameValidator;
import org.springframework.stereotype.Service;

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
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final AppleLoginAuthenticationProvider appleLoginAuthenticationProvider;
    private final JwtProvider jwtProvider;


    public CreateUserResponse signup(SignupRequest request, String idToken) {

        switch (request.clientType()) {
            case APPLE -> {
                SocialAuthDto socialAuthDto = appleLoginAuthenticationProvider.getSocialAuth(idToken);

                User user = userRepository.findBySocialId(socialAuthDto.getProviderId())
                        .orElseThrow(() -> new ValidationException("Invalid social ID"));

                user.registerUser(request.nickname());

                String accessToken = jwtProvider.generateToken(QuackUser.from(user));
                String refreshToken = jwtProvider.generateRefreshToken(QuackUser.from(user));

                return CreateUserResponse.of(accessToken, refreshToken);
            }
            default -> {
                // TODO : 공통 예외처리 로직 적용
                throw new ValidationException("Invalid client type");
            }
        }


    }

}
