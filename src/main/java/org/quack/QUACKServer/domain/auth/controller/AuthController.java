package org.quack.QUACKServer.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.dto.request.SignupRequest;
import org.quack.QUACKServer.domain.auth.dto.response.AuthResponse;
import org.quack.QUACKServer.domain.auth.service.AuthService;
import org.quack.QUACKServer.global.common.dto.CommonResponse;
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
@RequestMapping(path = "")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // TODO : 배포과정에서 삭제.
    @PostMapping(value = "/auth/apple/callback", consumes = "application/x-www-form-urlencoded")
    public String callback(HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        String body = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
        return "성공";

    }

    @PostMapping("/auth/signup")
    public AuthResponse signup(
            @Valid @NotBlank @RequestHeader("id-token") String idToken,
            @Valid @RequestBody SignupRequest request) {
         return authService.signup(request, idToken);
    }

    @PostMapping("/auth/validate-nickname")
    public CommonResponse validateUserNickname(
            @Valid @RequestParam("nickname") String nickname) {
        return authService.validateNickName(nickname);
    }


}
