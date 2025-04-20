package org.quack.QUACKServer.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class AuthController {

    static class Callback {
        private String code;
        private String id_token;
        private String state;
        private Object user;
    }
    @PostMapping(value = "/auth/apple/callback", consumes = "application/x-www-form-urlencoded")
    public String callback(HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        String body = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
        return "성공";

    }
}
