package org.quack.QUACKServer.global.external.redis.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthTokenValue;
import org.quack.QUACKServer.global.common.constant.QuackCode;
import org.quack.QUACKServer.global.error.exception.QuackGlobalException;
import org.quack.QUACKServer.global.external.redis.dto.RedisAuthTokenValue;
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
                    RedisAuthTokenValue.from(token));
        } catch (Exception e) {
            throw new QuackGlobalException(QuackCode.ExceptionCode.UN_AUTHENTICATION_ACCESS);
        }
    }

    public QuackAuthTokenValue findTokenByKey(String authKey) {
        RedisAuthTokenValue authTokenValue = authRedisRepository.get(authKey)
                .orElseThrow(() -> new QuackGlobalException(QuackCode.ExceptionCode.UN_AUTHENTICATION_ACCESS));
        return QuackAuthTokenValue.from(authTokenValue);
    }

    public void deleteTokenByKey(String authKey) {
        authRedisRepository.delete(authKey);
    }


    public String buildKey(String nickname, Long customerUsrId) {
        return "AUTH" + ":" + customerUsrId + ":" + nickname;
    }
}
