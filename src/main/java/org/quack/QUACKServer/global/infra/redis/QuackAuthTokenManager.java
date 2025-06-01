package org.quack.QUACKServer.global.infra.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthTokenValue;
import org.quack.QUACKServer.global.infra.redis.dto.RedisAuthTokenValue;
import org.quack.QUACKServer.global.infra.redis.repository.AuthRedisRepository;
import org.springframework.stereotype.Service;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.infra.redis
 * @fileName : QuackAuthTokenManager
 * @date : 25. 6. 1.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class QuackAuthTokenManager {

    private final AuthRedisRepository authRedisRepository;

    public void insertToken(QuackAuthTokenValue token) {
        try{
            authRedisRepository.insert(token.quackUser().getAuthKey(),
                    RedisAuthTokenValue.from(token), 3600);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public QuackAuthTokenValue findTokenByKey(String authKey) {
        RedisAuthTokenValue authTokenValue = authRedisRepository.get(authKey)
                .orElseThrow(RuntimeException::new);
        return QuackAuthTokenValue.from(authTokenValue);
    }
}
