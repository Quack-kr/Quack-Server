package org.quack.QUACKServer.global.infra.social.kakao.dto;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.infra.social.kakao.dto
 * @fileName : KakaoPublicKeys
 * @date : 25. 5. 31.
 */
public record KakaoPublicKeys(
        List<KakaoPublicKey> keys
) {
    public KakaoPublicKey getKey(final String kid) {
        return keys.stream()
                .filter(key -> key.kid().equals(kid))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
