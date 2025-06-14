package org.quack.QUACKServer.service;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.Redis.RefreshToken;
import org.quack.QUACKServer.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    @Value("${jwt.refresh.expiration-time}")
    private final Long expiration;
    private final RedisRepository redisRepository;


    public RefreshToken getRefreshToken(Long userId) {
        return redisRepository.findById(userId).orElse(null);
    }

    public void saveRefreshToken(Long userId, String token) {
        RefreshToken refreshToken = RefreshToken.create(userId, token, expiration);
        redisRepository.save(refreshToken);
    }

    public void deleteRefreshToken(Long userId) {
        redisRepository.deleteById(userId);
    }

}
