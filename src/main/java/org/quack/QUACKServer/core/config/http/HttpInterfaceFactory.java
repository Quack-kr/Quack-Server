package org.quack.QUACKServer.core.config.http;

import org.springframework.web.client.RestClient;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.http
 * @fileName : HttpInterfaceFactory
 * @date : 25. 4. 14.
 */
public interface HttpInterfaceFactory  {
    <S> S create(Class<S> interfaceClass, RestClient restClient);
}
