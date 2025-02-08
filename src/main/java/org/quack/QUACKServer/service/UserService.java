package org.quack.QUACKServer.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.config.jwt.JwtProvider;
import org.quack.QUACKServer.domain.User;
import org.quack.QUACKServer.dto.user.LoginResponse;
import org.quack.QUACKServer.dto.user.UpdateUserInfoRequest;
import org.quack.QUACKServer.dto.user.UserInfoResponse;
import org.quack.QUACKServer.oauth.dto.KakaoUserInfo;
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

    public LoginResponse loginOrRegisterKakao(String kakaoAccessToken) {

        KakaoUserInfo kakaoUserInfo = kakaoService.getKakaoUserInfo(kakaoAccessToken);

        Optional<User> optionalUser = userRepository.findByEmailAndProviderType(
                kakaoUserInfo.getEmail(),
                "KAKAO"
        );

        User user;
        if (optionalUser.isEmpty()) {
            user = registerKakaoUser(kakaoUserInfo);
        } else {
            user = optionalUser.get();
        }

        return generateTokens(user);
    }


    private User registerKakaoUser(KakaoUserInfo kakaoUserInfo) {

        if (userRepository.existsByNickname(kakaoUserInfo.getNickname())) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }

        User newUser = User.builder()
                .providerType("KAKAO")
                .email(kakaoUserInfo.getEmail())
                .nickname(kakaoUserInfo.getNickname())
                .roleType("USER")
                .profileImage(kakaoUserInfo.getProfileImage())
                .build();

        return userRepository.save(newUser);
    }


    private LoginResponse generateTokens(User user) {
        String accessToken = jwtProvider.createAccessToken(user.getUserId(), user.getRoleType());
        String refreshToken = jwtProvider.createRefreshToken(user.getUserId(), user.getRoleType());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    public UserInfoResponse getMyPageInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        int reviewCount = user.getReviews().size();
        int savedCount = user.getSavedRestaurants().size();

        return UserInfoResponse.builder()
                .nickname(user.getNickname())
                .reviewCount(reviewCount)
                .savedRestaurantCount(savedCount)
                .build();
    }


    public void updateProfile(Long userId, UpdateUserInfoRequest updateUserInfoRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        user.updateUserProfile(updateUserInfoRequest);
    }
}
