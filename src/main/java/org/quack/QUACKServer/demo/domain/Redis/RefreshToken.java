package org.quack.QUACKServer.demo.domain.Redis;

import java.io.Serializable;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("refreshToken")
public record RefreshToken(
        @Id Long userId,
        String refreshToken,
        @TimeToLive Long expiration
) {
        public static RefreshToken create(Long userId, String refreshToken, Long expiration) {
        return new RefreshToken(userId, refreshToken, expiration);
    }
}
//@Getter
//public class RefreshToken implements Serializable {
//    @Id
//    private Long userId;
//
//    private String refreshToken;
//
//    @TimeToLive
//    private Long expiration;
//
//
//    private RefreshToken(Long userId, String refreshToken, Long expiration) {
//        this.userId = userId;
//        this.refreshToken = refreshToken;
//        this.expiration = expiration;
//    }
//
//    public static RefreshToken create(Long userId, String refreshToken, Long expiration) {
//        return new RefreshToken(userId, refreshToken, expiration);
//    }
//}
