package org.quack.QUACKServer.global.config.http;

import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.http
 * @fileName : GlobalHttpInterfaceFactory
 * @date : 25. 4. 14.
 */
public class GlobalHttpInterfaceFactory implements HttpInterfaceFactory {
    @Override
    public <S> S create(Class<S> interfaceClass, RestClient restClient) {
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(interfaceClass);
    }
}
