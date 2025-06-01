package org.quack.QUACKServer.auth.domain;

import lombok.AccessLevel;
import lombok.Builder;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.domain
 * @fileName : QuackAuthTokenValue
 * @date : 25. 6. 1.
 */
@Builder(access = AccessLevel.PRIVATE)
public record JwtTokenDto(
        Long customerUserId,
        String accessToken,
        String refreshToken
) {

    public String getRedisKey() {
        return "AUTH_JWT_TOKEN:" + customerUserId;
    }

    public static JwtTokenDto create(Long customerUserId, String accessToken, String refreshToken) {
        return JwtTokenDto.builder()
                .customerUserId(customerUserId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
