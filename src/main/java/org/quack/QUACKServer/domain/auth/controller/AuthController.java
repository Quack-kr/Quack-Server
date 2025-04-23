package org.quack.QUACKServer.domain.auth.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.dto.request.SignupRequest;
import org.quack.QUACKServer.domain.auth.dto.response.CreateUserResponse;
import org.quack.QUACKServer.domain.auth.service.AuthService;
import org.quack.QUACKServer.global.common.dto.CommonResponse;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping(value = "/auth/apple/callback", consumes = "application/x-www-form-urlencoded")
//    public String callback(HttpServletRequest request) {
//
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
//        String body = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
//        return "성공";
//
//    }

    @PostMapping("/auth/signup")
    public CreateUserResponse signup(
            @Valid @NotBlank @RequestHeader("id_token") String idToken,
            @Valid @RequestBody SignupRequest request) {
         return authService.signup(request, idToken);
    }


}
