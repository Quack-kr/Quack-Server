package org.quack.QUACKServer.demo.service;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.demo.domain.User;
import org.quack.QUACKServer.demo.exception.errorCode.CustomUserError;
import org.quack.QUACKServer.demo.exception.exception.CustomUserException;
import org.quack.QUACKServer.demo.repository.UserRepository;
import org.quack.QUACKServer.demo.dto.user.InitRegisterResponse;
import org.quack.QUACKServer.demo.dto.user.MyPageInfoResponse;
import org.quack.QUACKServer.demo.dto.user.NicknameValidation;
import org.quack.QUACKServer.demo.dto.user.RegisterResponse;
import org.quack.QUACKServer.demo.dto.user.RegisterUserRequest;
import org.quack.QUACKServer.demo.dto.user.UpdateUserInfoRequest;
import org.quack.QUACKServer.demo.dto.user.UpdateUserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ReviewService reviewService;
    private final SavedRestaurantService savedRestaurantService;

    private static final String[] COLORS = {
            "붉은", "주황", "노란", "푸른", "보라", "핑크"
    };

    private static final String[] MENUS = {
            "탕수육", "김밥", "자장면", "라면", "짬뽕", "수육", "족발", "돈까스", "떡볶이", "족발", "순대", "닭발"
    };


    public String generateDefaultNickname(){
        String nickname;
        while(true){
            nickname = generateRandomNickname();
            if (!userRepository.existsByNickname(nickname)) {
                break;
            }
        }
        return nickname;
    }

    private String generateRandomNickname(){
        Random random = new Random();
        String color = COLORS[random.nextInt(COLORS.length)];
        String menu = MENUS[random.nextInt(MENUS.length)];
        int num = random.nextInt(9999) + 1;

        return String.format("%s %s %04d", color, menu, num);
    }


    public User getUserOrException(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new CustomUserException(CustomUserError.USER_NOT_FOUND, "user with that id could not be found"));
    }

    public InitRegisterResponse initRegister(Long userid) {
        User user = getUserOrException(userid);
        String defaultNickname = generateDefaultNickname();
        return InitRegisterResponse.of(user.getSocialType(), user.getEmail(), defaultNickname);
    }


    public UpdateUserResponse updateProfile(Long userId, UpdateUserInfoRequest updateUserInfoRequest) {
        User user = getUserOrException(userId);
        if (duplicatedNickname(updateUserInfoRequest.nickname())) {
            return UpdateUserResponse.of("닉네임이 중복입니다.", false);
        } else {
            user.updateUserProfile(updateUserInfoRequest);
            return UpdateUserResponse.of("프로필 변경이 완료되었습니다.", true);
        }
    }

    public RegisterResponse registerUser(Long userId, RegisterUserRequest registerUserRequest) {
        User user = getUserOrException(userId);
        if (duplicatedNickname(registerUserRequest.nickname())) {
            return RegisterResponse.from("닉네임이 중복입니다.",false);
        }
        user.registerUser(registerUserRequest);
        return RegisterResponse.from("회원가입이 완료되었습니다.",true);
    }


    public NicknameValidation isAvaliableNickname(Long userId, String nickname) {
        User user = getUserOrException(userId);

        if (user.getNickname().equals(nickname)) {
            throw new CustomUserException(CustomUserError.CURRENT_USE_NICKNAME, "user already used current nickname");
        }
        if (duplicatedNickname(nickname)) {
            return NicknameValidation.from(false);
        }

        return NicknameValidation.from(true);
    }

    public boolean duplicatedNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public MyPageInfoResponse getMyPage(Long userId) {
        User user = getUserOrException(userId);

        // 추후 DTO 프로젝션, 직접 쿼리 작성으로 성능 개선
        int reviewCount = reviewService.getReviewCountByUserId(userId);
        int savedRestaurantCount = savedRestaurantService.getSavedRestaurantCountByUserId(userId);
        double empathyDecibel = reviewService.getEmpathyDecibelByUserId(userId);

        return MyPageInfoResponse.of(
                user.getNickname(),
                user.getProfileImage(),
                reviewCount,
                savedRestaurantCount,
                empathyDecibel
                );
    }
}
