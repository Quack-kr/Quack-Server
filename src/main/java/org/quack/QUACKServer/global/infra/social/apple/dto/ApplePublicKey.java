package org.quack.QUACKServer.global.infra.social.apple.dto;


/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.security.dto
 * @fileName : OidcPublicKey
 * @date : 25. 4. 15.
 */
public record ApplePublicKey(
        String kty,
        String kid,
        String use,
        String alg,
        String n,
        String e
) {

}
