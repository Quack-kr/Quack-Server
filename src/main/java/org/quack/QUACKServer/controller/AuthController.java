package org.quack.QUACKServer.controller;

import static org.springframework.http.HttpStatus.OK;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.dto.auth.AuthResponse;
import org.quack.QUACKServer.oauth.service.KakaoService;
import org.quack.QUACKServer.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final KakaoService kakaoService;
    private final UserService userService;

    @PostMapping("/auth/login/kakao")
    public ResponseEntity<AuthResponse> kakaoLogin(@RequestHeader("Kakao-Access-token") String kakaoAccessToken) {
        return ResponseEntity
                .status(OK)
                .body(kakaoService.getKakaoUserInfo(kakaoAccessToken));
    }

}
