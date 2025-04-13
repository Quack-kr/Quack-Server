package org.quack.QUACKServer.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.domain.User;
import org.quack.QUACKServer.domain.common.SocialType;
import org.quack.QUACKServer.dto.restaurant.SavedRestaurantDto;
import org.quack.QUACKServer.dto.user.MyPageInfoResponse;
import org.quack.QUACKServer.dto.user.NicknameValidation;
import org.quack.QUACKServer.dto.user.RegisterResponse;
import org.quack.QUACKServer.dto.user.RegisterUserRequest;
import org.quack.QUACKServer.dto.user.UpdateUserInfoRequest;
import org.quack.QUACKServer.dto.user.InitRegisterResponse;
import org.quack.QUACKServer.dto.user.UpdateUserResponse;
import org.quack.QUACKServer.oauth.service.KakaoService;
import org.quack.QUACKServer.service.RedisService;
import org.quack.QUACKServer.service.ReviewService;
import org.quack.QUACKServer.service.SavedRestaurantService;
import org.quack.QUACKServer.service.UserService;
import org.quack.QUACKServer.validation.NicknameConstraint;
import org.springframework.data.domain.Page;
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
    private final KakaoService kakaoService;
    private final RedisService redisService;


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
        RegisterResponse response = userService.registerUser(user.getUserId(), registerUserRequest);
        if(!response.isRegister()){
            return ResponseEntity
                    .status(CONFLICT)
                    .body(response);
        }
        else return ResponseEntity
                .status(CREATED)
                .body(response);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(Principal principal) {
        User user = userService.getUserOrException(Long.valueOf(principal.getName()));

        if (user.getSocialType() == SocialType.KAKAO) {
            kakaoService.kakaoLogout(user);
        }

        // 애플, 네이버 구현 후 추가

        return ResponseEntity.status(NO_CONTENT).build();
    }

    // TODO: 해지 사유 요청으로 넘어오면, 바인딩하고 DB에 저장하는 거 추가하기.
    @GetMapping("/withdraw")
    public ResponseEntity<Void> withdraw(Principal principal) {
        User user = userService.getUserOrException(Long.valueOf(principal.getName()));
        // refresh token - Redis 삭제
        // DB에 데이터를 삭제할 것인지 정하기. 작성한 리뷰, 핵공감, 노공감 등등

        if (user.getSocialType() == SocialType.KAKAO) {
            kakaoService.kakaoWithdraw(user);
        }

        // 애플, 네이버 구현 후 추가

        redisService.deleteRefreshToken(user.getUserId());

        return ResponseEntity.status(NO_CONTENT).build();
    }


    @PatchMapping("/my-profile")
    public ResponseEntity<UpdateUserResponse> updateProfile(
            Principal principal,
            @RequestBody UpdateUserInfoRequest request
    ) {
        User user = userService.getUserOrException(Long.valueOf(principal.getName()));
        UpdateUserResponse response = userService.updateProfile(user.getUserId(), request);
        if (!response.isUpdate()) {
            return ResponseEntity.status(CONFLICT).body(response);
        }
        else return ResponseEntity.status(OK).body(response);
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
    public ResponseEntity<Page<SavedRestaurantDto>> getMySavedRestaurant(
            Principal principal,
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam("latitude") double userLatitude,
            @RequestParam("longitude") double userLongitude) {


        User user = userService.getUserOrException(Long.valueOf(principal.getName()));

        return ResponseEntity
                .status(OK)
                .body(savedRestaurantService.getSavedRestaurantsByUserId(
                        user.getUserId(),
                        pageNum,
                        pageSize,
                        userLatitude,
                        userLongitude));
    }
}
