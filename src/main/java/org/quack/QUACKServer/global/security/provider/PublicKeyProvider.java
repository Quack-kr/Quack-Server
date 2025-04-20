package org.quack.QUACKServer.global.security.provider;

import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.global.infra.social.apple.dto.OidcPublicKey;
import org.quack.QUACKServer.global.infra.social.apple.dto.OidcPublicKeys;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.security.manager
 * @fileName : PublicKeyProvider
 * @date : 25. 4. 15.
 */
@Component
@Slf4j
public class PublicKeyProvider {

    public PublicKey generatePublicKey(Map<String, String> idTokenHeaders, OidcPublicKeys oidcPublicKeys) {
        return getPublicKey(oidcPublicKeys.getKey(idTokenHeaders.get("kid"), idTokenHeaders.get("alg")));
    }

    private PublicKey getPublicKey(OidcPublicKey oidcPublicKey) {
        byte[] nBytes = Base64.getDecoder().decode(oidcPublicKey.n());
        byte[] eBytes = Base64.getDecoder().decode(oidcPublicKey.e());

        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec( new BigInteger(1, nBytes), new BigInteger(1, eBytes));

        try {
            return KeyFactory.getInstance(oidcPublicKey.kty()).generatePublic(rsaPublicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
