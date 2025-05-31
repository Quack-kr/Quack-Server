package org.quack.QUACKServer.global.security.provider;

import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.global.infra.social.apple.dto.ApplePublicKey;
import org.quack.QUACKServer.global.infra.social.apple.dto.ApplePublicKeys;
import org.quack.QUACKServer.global.infra.social.kakao.dto.KakaoPublicKey;
import org.quack.QUACKServer.global.infra.social.kakao.dto.KakaoPublicKeys;
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

    public PublicKey generatePublicKey(Map<String, String> idTokenHeaders, ApplePublicKeys applePublicKeys) {
        return getPublicKey(applePublicKeys.getKey(idTokenHeaders.get("kid"), idTokenHeaders.get("alg")));
    }

    // TODO 전략 패턴 수정 필요
    public PublicKey generatePublicKey(Map<String, String> idTokenHeaders, KakaoPublicKeys kakaoPublicKeys) {
        return getPublicKey(kakaoPublicKeys.getKey(idTokenHeaders.get("kid")));
    }

    private PublicKey getPublicKey(ApplePublicKey oidcPublicKey) {

        try {

            byte[] nBytes = Base64.getUrlDecoder().decode(oidcPublicKey.n());
            byte[] eBytes = Base64.getUrlDecoder().decode(oidcPublicKey.e());

            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(
                    new BigInteger(1, nBytes),
                    new BigInteger(1, eBytes)
            );

            return KeyFactory.getInstance(oidcPublicKey.kty()).generatePublic(rsaPublicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO 전략 패턴 수정 필요
    private PublicKey getPublicKey(KakaoPublicKey oidcPublicKey) {

        try {

            byte[] nBytes = Base64.getUrlDecoder().decode(oidcPublicKey.n());
            byte[] eBytes = Base64.getUrlDecoder().decode(oidcPublicKey.e());

            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(
                    new BigInteger(1, nBytes),
                    new BigInteger(1, eBytes)
            );

            return KeyFactory.getInstance(oidcPublicKey.kty()).generatePublic(rsaPublicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
