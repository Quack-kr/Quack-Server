package org.quack.QUACKServer.core.external.social.apple;

import org.quack.QUACKServer.core.external.social.apple.dto.ApplePublicKeys;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * @author : jung-kwanhee
 * @description :
 * @fileName : AppleHttpInterface
 * @date : 25. 4. 14.
 */
@HttpExchange
public interface AppleHttpInterface {

    @GetExchange("/auth/keys")
    ApplePublicKeys getApplePublicKeys();

    // @PostExchange("/auth/oauth2/v2/token")

}
