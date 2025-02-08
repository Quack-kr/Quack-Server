package org.quack.QUACKServer.controller;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.oauth.service.KakaoService;
import org.quack.QUACKServer.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final KakaoService kakaoService;
    private final UserService userService;

}
