package org.quack.QUACKServer.global.external.redis.dto;

import lombok.Builder;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthTokenValue;
import org.quack.QUACKServer.domain.auth.enums.TokenStatus;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.infra.redis.dto
 * @fileName : RedisAuthTokenValue
 * @date : 25. 6. 1.
 */
@RedisHash("auth")
public record RedisAuthTokenValue(
        RedisAuthTokenCustomerUserValue quackUser,
        String accessToken,
        String refreshToken,
        TokenStatus tokenStatus,
        LocalDateTime generatedAt
) {

    @Builder(builderMethodName = "createBuilder")
    public RedisAuthTokenValue(RedisAuthTokenCustomerUserValue quackUser, String accessToken,
                               String refreshToken, TokenStatus tokenStatus, LocalDateTime generatedAt) {
        this.quackUser = quackUser;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenStatus = tokenStatus;
        this.generatedAt = generatedAt;
    }

    public static RedisAuthTokenValue of(RedisAuthTokenCustomerUserValue quackUser, String accessToken,
                                         String refreshToken, TokenStatus tokenStatus, LocalDateTime generatedAt) {
        return RedisAuthTokenValue.createBuilder()
                .quackUser(quackUser)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenStatus(tokenStatus)
                .generatedAt(generatedAt)
                .build();
    }

    public static RedisAuthTokenValue from(QuackAuthTokenValue token) {
        return RedisAuthTokenValue.createBuilder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .tokenStatus(token.tokenStatus())
                .generatedAt(token.generatedAt())
                .quackUser(RedisAuthTokenCustomerUserValue.builder()
                        .customerUserId(token.quackUser().getCustomerUserId())
                        .providerType(token.quackUser().getProvider())
                        .email(token.quackUser().getEmail())
                        .nickname(token.quackUser().getNickname())
                        .build()
                ).build();

    }

}
