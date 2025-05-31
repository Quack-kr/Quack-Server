package org.quack.QUACKServer.global.infra.social.kakao;

import org.quack.QUACKServer.global.infra.social.kakao.dto.KakaoPublicKeys;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.infra.social.kakao
 * @fileName : KakaoHttpInterface
 * @date : 25. 5. 31.
 */
@HttpExchange
public interface KakaoHttpInterface {
    @GetExchange("/.well-known/jwks.json")
    KakaoPublicKeys getKakaoPublicKey();
}
