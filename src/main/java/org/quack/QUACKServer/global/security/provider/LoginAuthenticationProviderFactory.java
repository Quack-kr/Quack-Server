package org.quack.QUACKServer.global.security.provider;

import org.quack.QUACKServer.global.security.enums.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : jung-kwanhee
 * @description : LoginAuthenticationProvider 을 정해주는 팩토리
 * @packageName : org.quack.QUACKServer.global.config.security.handler
 * @fileName : LoginAuthenticationProviderFactory
 * @date : 25. 4. 15.
 */
@Component
public class LoginAuthenticationProviderFactory {

    private final Map<ClientType, LoginAuthenticationProvider> providerMap = new HashMap<>();

    @Autowired
    public LoginAuthenticationProviderFactory(List<LoginAuthenticationProvider> providers) {
        providers.forEach(provider -> {
            ClientType clientType = resolve(provider);
            providerMap.put(clientType, provider);
        });
    }

    public LoginAuthenticationProvider get(ClientType clientType) {
        if (!providerMap.containsKey(clientType)) {
            throw new IllegalArgumentException("알 수 없는 로그인 방식 : " +  clientType.getValue());
        }

        return providerMap.get(clientType);
    }

    private ClientType resolve(LoginAuthenticationProvider provider) {
        switch (provider) {
            case AppleLoginAuthenticationProvider appleLoginAuthenticationProvider-> {
                return ClientType.APPLE;
            }
            case null, default -> {
                throw new IllegalArgumentException("알 수 없는 로그인 방식 : " +  provider.getClass());
            }
        }
    }

}
