package org.quack.QUACKServer.global.infra.redis.dto;

import lombok.Builder;
import org.quack.QUACKServer.global.security.enums.ProviderType;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.infra.redis.dto
 * @fileName : RedisAuthTokenCustomerUserValue
 * @date : 25. 6. 1.
 */
@Builder
public record RedisAuthTokenCustomerUserValue(
        Long customerUserId,
        String nickname,
        ProviderType providerType,
        String email
) {
}
