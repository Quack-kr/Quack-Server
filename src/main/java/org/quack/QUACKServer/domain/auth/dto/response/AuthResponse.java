package org.quack.QUACKServer.domain.auth.dto.response;

import lombok.Builder;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.dto.response
 * @fileName : AuthResponse
 * @date : 25. 4. 23.
 */
@Builder
public record AuthResponse(
        String accessToken,
        String refreshToken
) {
    public static AuthResponse of(String accessToken, String refreshToken) {
        return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }
}
