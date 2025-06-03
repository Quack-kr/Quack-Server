package org.quack.QUACKServer.core.external.social.kakao;

import org.quack.QUACKServer.core.external.social.kakao.dto.KakaoPublicKeys;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * @author : jung-kwanhee
 * @description :
 * @fileName : KakaoHttpInterface
 * @date : 25. 5. 31.
 */
@HttpExchange
public interface KakaoHttpInterface {
    @GetExchange("/.well-known/jwks.json")
    KakaoPublicKeys getKakaoPublicKey();
}
