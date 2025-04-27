package org.quack.QUACKServer.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.domain.auth.domain.QuackAuthContext;
import org.quack.QUACKServer.domain.auth.domain.QuackUser;
import org.quack.QUACKServer.global.common.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.user.controller
 * @fileName : UserController
 * @date : 25. 4. 24.
 */
@RestController
@Slf4j
@RequestMapping(path = "")
@RequiredArgsConstructor
public class UserController {

    @PostMapping("/user/signup")
    public CommonResponse signup() {
        QuackUser quackUser = QuackAuthContext.getQuackUserDetails();
        return CommonResponse.of("201","필터 정상적으로 탐", HttpStatus.OK, null);
//        return authService.signup(request, idToken);
    }
}
