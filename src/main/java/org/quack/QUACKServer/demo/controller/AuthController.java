package org.quack.QUACKServer.demo.controller;

import static org.springframework.http.HttpStatus.OK;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.demo.dto.auth.AuthResponse;
import org.quack.QUACKServer.demo.dto.auth.ReissueResponse;
import org.quack.QUACKServer.domain.auth.service.KakaoAuthenticationService;
import org.quack.QUACKServer.demo.service.AuthService;
import org.quack.QUACKServer.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final KakaoAuthenticationService kakaoAuthenticationService;
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/auth/login/kakao")
    public ResponseEntity<AuthResponse> kakaoLogin(@RequestHeader("Kakao-Access-token") String kakaoAccessToken) {
        return ResponseEntity
                .status(OK)
                .body(kakaoAuthenticationService.loginUser(kakaoAccessToken));
    }

    @GetMapping("/auth/reissue")
    public ResponseEntity<ReissueResponse> reissueToken(@RequestHeader("Refresh-Token") String refreshToken) {
        return ResponseEntity
                .status(OK)
                .body(authService.reissueToken(refreshToken));
    }
}
