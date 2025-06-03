package org.quack.QUACKServer.core.external.social.kakao.dto;

/**
 * @author : jung-kwanhee
 * @description :
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
