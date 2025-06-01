package org.quack.QUACKServer.domain.auth.dto.response;

import lombok.Builder;
import org.quack.QUACKServer.domain.auth.domain.JwtTokenDto;
import org.quack.QUACKServer.domain.auth.enums.SignUpStatus;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.dto.response
 * @fileName : AuthResponse
 * @date : 25. 4. 23.
 */
@Builder
public record AuthResponse(
        SignUpStatus signUpStatus,
        String accessToken,
        String refreshToken
) {
    public static AuthResponse from(JwtTokenDto tokenDto) {
        return AuthResponse.builder()
                .signUpStatus(SignUpStatus.FINISH)
                .accessToken(tokenDto.accessToken())
                .refreshToken(tokenDto.refreshToken())
                .build();
    }
}
