package org.quack.QUACKServer.service;


import static org.quack.QUACKServer.exception.errorCode.CustomTokenError.REFRESH_TOKEN_INVALID;
import static org.quack.QUACKServer.exception.errorCode.CustomTokenError.REFRESH_TOKEN_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.config.jwt.JwtProvider;
import org.quack.QUACKServer.config.jwt.JwtUtils;
import org.quack.QUACKServer.domain.Redis.RefreshToken;
import org.quack.QUACKServer.dto.auth.ReissueResponse;
import org.quack.QUACKServer.exception.exception.CustomTokenException;
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
        if (!storedRefreshToken.getRefreshToken().equals(requestRefreshToken)) {
            throw new CustomTokenException(REFRESH_TOKEN_INVALID, "refresh token is invalid. please log-in again.");
        }

        String newAccessToken = jwtProvider.createAccessToken(userId);
        String newRefreshToken = jwtProvider.createRefreshToken(userId);

        redisService.saveRefreshToken(userId, newRefreshToken);

        return ReissueResponse.of(newAccessToken, newRefreshToken);
    }

}
