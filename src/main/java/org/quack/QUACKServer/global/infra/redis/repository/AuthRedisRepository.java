package org.quack.QUACKServer.global.infra.redis.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.global.infra.redis.dto.RedisAuthTokenValue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.infra.redis.repository
 * @fileName : AuthRedisRepository
 * @date : 25. 6. 1.
 */
@Repository
@Slf4j
public class AuthRedisRepository implements RedisRepository<String, RedisAuthTokenValue> {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> authTokenRedisTemplate;

    @Value("${spring.datasource.data.redis.default-ttl}")
    long defaultTtl;

    @Resource(name = "authTokenRedisTemplate")
    private ValueOperations<String, String> valueOperations;

    public AuthRedisRepository(
            ObjectMapper objectMapper, @Qualifier("authTokenRedisTemplate") RedisTemplate<String, String> authTokenRedisTemplate) {
        this.objectMapper = objectMapper;
        this.authTokenRedisTemplate = authTokenRedisTemplate;
    }

    @Override
    public Optional<RedisAuthTokenValue> get(String s) {
        String findValueByKey = valueOperations.get(s);

        if(!StringUtils.hasText(findValueByKey)) {
            return Optional.empty();
        }

        try {
            return Optional.ofNullable(
                    objectMapper.readValue(findValueByKey, RedisAuthTokenValue.class));
        } catch (JsonProcessingException e) {
            log.error("Redis 토큰 가져오는 중 에러 발생 , key : {}, value : {}", s, findValueByKey);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(String s, RedisAuthTokenValue redisAuthTokenValue, long timeout) {
        if (timeout == 0) {
            timeout = defaultTtl;
        }
        String value;
        try {
            value = objectMapper.writeValueAsString(redisAuthTokenValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        valueOperations.set(s, value, timeout, TimeUnit.SECONDS);
    }

    @Override
    public boolean delete(String s) {
        return Boolean.TRUE.equals(authTokenRedisTemplate.delete(s));
    }

}
