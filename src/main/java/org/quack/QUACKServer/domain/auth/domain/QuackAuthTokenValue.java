package org.quack.QUACKServer.domain.auth.domain;

import lombok.AccessLevel;
import lombok.Builder;
import org.quack.QUACKServer.domain.auth.enums.TokenStatus;
import org.quack.QUACKServer.global.external.redis.dto.RedisAuthTokenValue;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.domain
 * @fileName : QuackAuthTokenValue
 * @date : 25. 6. 1.
 */
@Builder(access = AccessLevel.PRIVATE)
public record QuackAuthTokenValue(
        QuackUser quackUser,
        String accessToken,
        String refreshToken,
        TokenStatus tokenStatus,
        LocalDateTime generatedAt
) {
    public boolean isAfterMinute() {
        return generatedAt.plusMinutes(1).isBefore(LocalDateTime.now());
    }

    public boolean isExpired() {
        return generatedAt.plusMinutes(30).isBefore(LocalDateTime.now());
    }

    public static QuackAuthTokenValue from(RedisAuthTokenValue dto) {
        return QuackAuthTokenValue.builder()
                .accessToken(dto.accessToken())
                .refreshToken(dto.refreshToken())
                .generatedAt(dto.generatedAt())
                .quackUser(QuackUser.builder()
                        .customerUserId(dto.quackUser().customerUserId())
                        .email(dto.quackUser().email())
                        .provider(dto.quackUser().providerType())
                        .nickname(dto.quackUser().nickname())
                        .build()
                ).build();
    }
}
