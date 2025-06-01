package org.quack.QUACKServer.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.core.common.dto.ResponseDto;
import org.quack.QUACKServer.domain.auth.dto.request.SignupRequest;
import org.quack.QUACKServer.domain.auth.dto.response.AuthResponse;
import org.quack.QUACKServer.domain.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.controller
 * @fileName : AuthController
 * @date : 25. 4. 20.
 */

@RestController
@Slf4j
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // TODO : 배포과정에서 삭제.
    @PostMapping(value = "/apple/callback", consumes = "application/x-www-form-urlencoded")
    public String callback(HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        String body = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
        return "성공";

    }

    // TODO : 배포 과정에서 삭제.
    @GetMapping("/kakao/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code) {
        // 1. 받은 code로 토큰 요청
        // 2. 토큰으로 사용자 정보 요청
        // 3. 회원가입 or 로그인 처리
        return ResponseEntity.ok("성공");
    }

    @PostMapping("/signup")
    public AuthResponse signup(
            @Valid @NotBlank @RequestHeader("id_token") String idToken,
            @Valid @RequestBody SignupRequest request) {
         return authService.signup(request, idToken);
    }

    @PostMapping("/validate-nickname")
    public ResponseDto<?> validateUserNickname(
            @Valid @RequestBody String nickname) {
        return authService.validateNickName(nickname);
    }

    @PostMapping("/withdraw")
    public ResponseDto<?> withdraw() {
        return authService.deleteCustomerUser();
    }


    @PostMapping("/refresh-token")
    public AuthResponse getRefreshToken(HttpServletRequest request) {
        return authService.refreshToken(request);
    }

}
