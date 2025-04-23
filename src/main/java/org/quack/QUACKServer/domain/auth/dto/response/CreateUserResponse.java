package org.quack.QUACKServer.domain.auth.dto.response;

import lombok.Builder;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.dto.response
 * @fileName : CreateUserResponse
 * @date : 25. 4. 23.
 */
@Builder
public record CreateUserResponse(
        String accessToken,
        String refreshToken
) {
    public static CreateUserResponse of(String accessToken, String refreshToken) {
        return CreateUserResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }
}
