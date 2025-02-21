package org.quack.QUACKServer.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.User;
import org.quack.QUACKServer.dto.user.NicknameValidation;
import org.quack.QUACKServer.dto.user.RegisterResponse;
import org.quack.QUACKServer.dto.user.RegisterUserRequest;
import org.quack.QUACKServer.dto.user.UpdateUserInfoRequest;
import org.quack.QUACKServer.dto.user.InitRegisterResponse;
import org.quack.QUACKServer.service.UserService;
import org.quack.QUACKServer.validation.NicknameConstraint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/registration")
    public ResponseEntity<InitRegisterResponse> initRegister(Principal principal) {
        User user = userService.getUserOrException(Long.valueOf(principal.getName()));
        return ResponseEntity
                .status(OK)
                .body(userService.initRegister(user.getUserId()));
    }

    @GetMapping("/nickname/check")
    public ResponseEntity<NicknameValidation> checkNicknameDuplicate(Principal principal,
                                                                     @NicknameConstraint
                                                                     @RequestParam
                                                                     String nickname) {
        User user = userService.getUserOrException(Long.valueOf(principal.getName()));
        return ResponseEntity
                .status(OK)
                .body(userService.isAvaliableNickname(user.getUserId(), nickname));
    }

    @PatchMapping("/registration")
    public ResponseEntity<RegisterResponse> registerUser(Principal principal,
                                                         @Valid
                                             @RequestBody
                                             RegisterUserRequest registerUserRequest){
        User user = userService.getUserOrException(Long.valueOf(principal.getName()));
        return ResponseEntity
                .status(CREATED)
                .body(userService.registerUser(user.getUserId(), registerUserRequest));
    }


    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfile(
            Principal principal,
            @RequestBody UpdateUserInfoRequest request
    ) {
        User user = userService.getUserOrException(Long.valueOf(principal.getName()));
        userService.updateProfile(user.getUserId(), request);
        return ResponseEntity.ok().build();
    }

}
