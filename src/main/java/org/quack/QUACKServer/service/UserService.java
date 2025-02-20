package org.quack.QUACKServer.service;

import static org.quack.QUACKServer.exception.errorCode.CustomUserError.CURRENT_USE_NICKNAME;
import static org.quack.QUACKServer.exception.errorCode.CustomUserError.USER_NOT_FOUND;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.config.jwt.JwtProvider;
import org.quack.QUACKServer.domain.User;
import org.quack.QUACKServer.dto.user.InitRegisterResponse;
import org.quack.QUACKServer.dto.user.NicknameValidation;
import org.quack.QUACKServer.dto.user.RegisterResponse;
import org.quack.QUACKServer.dto.user.RegisterUserRequest;
import org.quack.QUACKServer.dto.user.UpdateUserInfoRequest;
import org.quack.QUACKServer.exception.exception.CustomUserException;
import org.quack.QUACKServer.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.quack.QUACKServer.oauth.service.KakaoService;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final KakaoService kakaoService;

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
                new CustomUserException(USER_NOT_FOUND, "user with that id could not be found"));
    }

    public InitRegisterResponse initRegister(Long userid) {
        User user = getUserOrException(userid);
        String defaultNickname = generateDefaultNickname();
        return InitRegisterResponse.of(user.getSocialType(), user.getEmail(), defaultNickname);
    }


    public void updateProfile(Long userId, UpdateUserInfoRequest updateUserInfoRequest) {
        User user = getUserOrException(userId);
        user.updateUserProfile(updateUserInfoRequest);
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
            throw new CustomUserException(CURRENT_USE_NICKNAME, "user already used current nickname");
        }
        if (duplicatedNickname(nickname)) {
            return NicknameValidation.from(false);
        }

        return NicknameValidation.from(true);
    }

    public boolean duplicatedNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
