package org.quack.QUACKServer.controller;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.dto.user.UpdateUserInfoRequest;
import org.quack.QUACKServer.dto.user.UserInfoResponse;
import org.quack.QUACKServer.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoResponse> getMyPageInfo(@PathVariable Long userId) {
        UserInfoResponse response = userService.getMyPageInfo(userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateProfile(
            @PathVariable Long userId,
            @RequestBody UpdateUserInfoRequest request
    ) {
        userService.updateProfile(userId, request);
        return ResponseEntity.ok().build();
    }

}
