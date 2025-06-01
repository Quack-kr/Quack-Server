package org.quack.QUACKServer.core.external.redis.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.core.common.constant.ErrorCode;
import org.quack.QUACKServer.core.error.exception.CommonException;
import org.quack.QUACKServer.domain.auth.domain.JwtTokenDto;
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
public class AuthRedisRepository implements RedisRepository<String, JwtTokenDto> {

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
    public Optional<JwtTokenDto> get(String s) {
        String value = valueOperations.get(s);

        if(!StringUtils.hasText(value)) {
            return Optional.empty();
        }

        try {
            return Optional.ofNullable(
                    objectMapper.readValue(value, JwtTokenDto.class));
        } catch (JsonProcessingException e) {
            throw new CommonException(ErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public void insert(String s, JwtTokenDto redisTokenDto) {
        try {
            valueOperations.set(s, objectMapper.writeValueAsString(redisTokenDto), defaultTtl, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            throw new CommonException(ErrorCode.SERVER_ERROR);
        }

    }

    @Override
    public void delete(String s) {
        authTokenRedisTemplate.delete(s);
    }

}
