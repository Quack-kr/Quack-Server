package org.quack.QUACKServer.core.external.redis.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.auth.domain.JwtTokenDto;
import org.quack.QUACKServer.core.error.constant.ErrorCode;
import org.quack.QUACKServer.core.error.exception.CommonException;
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
 * @fileName : AuthRedisRepository
 * @date : 25. 6. 1.
 */
@Repository
@Slf4j
@RequiredArgsConstructor
public class AuthRedisRepository {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> authRedisTemplate;

    @Value("${spring.datasource.data.redis.default-ttl}")
    long defaultTtl;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

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

    public void insert(String s, JwtTokenDto redisTokenDto) {
        try {
            valueOperations.set(s, objectMapper.writeValueAsString(redisTokenDto), defaultTtl, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            throw new CommonException(ErrorCode.SERVER_ERROR);
        }

    }

    public void delete(String s) {
        authRedisTemplate.delete(s);
    }

}
