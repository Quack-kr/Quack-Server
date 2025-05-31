package org.quack.QUACKServer.global.infra.social.kakao.dto;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.infra.social.kakao.dto
 * @fileName : KakaoPublicKey
 * @date : 25. 5. 31.
 */
public record KakaoPublicKey(
        String kty,
        String kid,
        String use,
        String n,
        String e
) {
}
