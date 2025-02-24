package org.quack.QUACKServer.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.User;
import org.quack.QUACKServer.dto.user.MyPageInfoResponse;
import org.quack.QUACKServer.dto.user.NicknameValidation;
import org.quack.QUACKServer.dto.user.RegisterResponse;
import org.quack.QUACKServer.dto.user.RegisterUserRequest;
import org.quack.QUACKServer.dto.user.UpdateUserInfoRequest;
import org.quack.QUACKServer.dto.user.InitRegisterResponse;
import org.quack.QUACKServer.service.ReviewService;
import org.quack.QUACKServer.service.SavedRestaurantService;
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
    private final ReviewService reviewService;
    private final SavedRestaurantService savedRestaurantService;


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
                                                         RegisterUserRequest registerUserRequest) {
        User user = userService.getUserOrException(Long.valueOf(principal.getName()));
        return ResponseEntity
                .status(CREATED)
                .body(userService.registerUser(user.getUserId(), registerUserRequest));
    }


    @PatchMapping("/my-profile")
    public ResponseEntity<Void> updateProfile(
            Principal principal,
            @RequestBody UpdateUserInfoRequest request
    ) {
        User user = userService.getUserOrException(Long.valueOf(principal.getName()));
        userService.updateProfile(user.getUserId(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-profile")
    public ResponseEntity<MyPageInfoResponse> getMyPage(Principal principal) {
        // 로그인이 되어 있지 않는 상태 (둘러보기) -> 추가하기, 직접 객체 만들어서 정보 반환
        // MyPageInfoResponse에 defaultInfo() 메서드 생성해놓음. 나중에 사용 고려해보기

        User user = userService.getUserOrException(Long.valueOf(principal.getName()));
        return ResponseEntity
                .status(OK)
                .body(userService.getMyPage(user.getUserId()));
    }

    @GetMapping("/my-reviews")
    public ResponseEntity<UserReviewsResponse> getMyReviews(Principal principal) {
        // 로그인이 되어 있지 않는 상태 예외 처리하기

        // ReviewController 에 넣을지, UserController 에 넣을지 고민해보기

        User user = userService.getUserOrException(Long.valueOf(principal.getName()));
        return ResponseEntity
                .status(OK)
                .body(reviewService.getReviewsByUserId(user.getUserId()));
    }

    @GetMapping("/save/restaurant")
    public ResponseEntity<UserSavedRestaurantResponse> getMySavedRestaurant(Principal principal) {

        // RestaurantController 에 넣을지, UserController 에 넣을지 고민해보기

        User user = userService.getUserOrException(Long.valueOf(principal.getName()));
        return ResponseEntity
                .status(OK)
                .body(savedRestaurantService.getSavedRestaurantsByUserId(user.getUserId()));
    }
}
