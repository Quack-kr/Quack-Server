package org.quack.QUACKServer.demo.service;


import static org.quack.QUACKServer.demo.exception.errorCode.CustomTokenError.REFRESH_TOKEN_INVALID;
import static org.quack.QUACKServer.demo.exception.errorCode.CustomTokenError.REFRESH_TOKEN_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.global.jwt.JwtProvider;
import org.quack.QUACKServer.global.jwt.JwtUtils;
import org.quack.QUACKServer.demo.domain.Redis.RefreshToken;
import org.quack.QUACKServer.demo.dto.auth.ReissueResponse;
import org.quack.QUACKServer.demo.exception.exception.CustomTokenException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final JwtUtils jwtUtils;
    private final RedisService redisService;

    public ReissueResponse reissueToken(String requestRefreshToken) {
        Long userId = jwtUtils.getUserIdFromJwt(requestRefreshToken);

        RefreshToken storedRefreshToken = redisService.getRefreshToken(userId);
        if (storedRefreshToken == null) {
            throw new CustomTokenException(REFRESH_TOKEN_NOT_FOUND, "refresh token not found. please log-in again.");
        }
        if (!storedRefreshToken.refreshToken().equals(requestRefreshToken)) {
            throw new CustomTokenException(REFRESH_TOKEN_INVALID, "refresh token is invalid. please log-in again.");
        }

        String newAccessToken = jwtProvider.createAccessToken(userId);
        String newRefreshToken = jwtProvider.createRefreshToken(userId);

        redisService.saveRefreshToken(userId, newRefreshToken);

        return ReissueResponse.of(newAccessToken, newRefreshToken);
    }

}
