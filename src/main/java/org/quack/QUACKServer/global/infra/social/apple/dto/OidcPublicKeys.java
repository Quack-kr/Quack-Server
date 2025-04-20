package org.quack.QUACKServer.global.infra.social.apple.dto;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.security.dto
 * @fileName : OidcPublicKeys
 * @date : 25. 4. 15.
 */
public record OidcPublicKeys(
        List<OidcPublicKey> keys
) {
    public OidcPublicKey getKey(final String kid, final String alg) {
        return keys.stream()
                .filter(key -> key.kid().equals(kid) && key.alg().equals(alg))
                .findAny()
                .orElseThrow(IllegalArgumentException::new); // TODO : 공식 에러 예외처리.
    }
}
