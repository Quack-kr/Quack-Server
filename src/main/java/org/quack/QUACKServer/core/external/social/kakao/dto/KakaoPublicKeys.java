package org.quack.QUACKServer.core.external.social.kakao.dto;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
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
