package org.quack.QUACKServer.oauth.service;

import lombok.RequiredArgsConstructor;
import org.quack.QUACKServer.oauth.dto.KakaoUserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;


@Service
@RequiredArgsConstructor
@Transactional
public class KakaoService {

    @Value("${kakao.user-info-url}")
    private String kakaoUserInfoUrl;

    @Value("${kakao.logout-url}")
    private String kakaoLogoutUrl;

    public KakaoUserInfo getKakaoUserInfo(String kakaoAccessToken) {
        RestClient restCLient = RestClient.create();

        KakaoUserInfo userInfo = restCLient.get()
                //TODO: 엔드포인트 변경 및 구조 변경
                .uri("https://kapi.kakao.com")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + kakaoAccessToken)
                .retrieve()
                .body(KakaoUserInfo.class);

        if (userInfo == null) {
            throw new RuntimeException("카카오 유저정보를 가져오지 못했습니다.");
        }
        return userInfo;
    }

}

/*
 TODO:
 1. 카카오 로그아웃
 2. 카카오 회원탈퇴
 */