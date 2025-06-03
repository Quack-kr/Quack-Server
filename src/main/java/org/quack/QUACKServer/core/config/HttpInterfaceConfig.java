package org.quack.QUACKServer.core.config;

import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.core.external.social.apple.AppleHttpInterface;
import org.quack.QUACKServer.core.external.social.kakao.KakaoHttpInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.core.config.http
 * @fileName : HttpInterfaceConfig
 * @date : 25. 6. 2.
 */
@Slf4j
@Configuration
public class HttpInterfaceConfig {

    @Value("${social.apple.base-url}")
    private String appleApiBaseUrl;

    @Value("${social.kakao.base-url}")
    private String kakaoApiBaseUrl;

    @Bean
    public WebClient kakaoWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(kakaoApiBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public WebClient appleWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(appleApiBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public KakaoHttpInterface kakaoHttpInterface(
            @Qualifier("kakaoWebClient") WebClient webClient) {
        return HttpServiceProxyFactory
                .builder()
                .exchangeAdapter(WebClientAdapter.create(webClient))
                .build()
                .createClient(KakaoHttpInterface.class);
    }

    @Bean
    public AppleHttpInterface appleHttpInterface(
            @Qualifier("appleWebClient") WebClient webClient) {
        return HttpServiceProxyFactory
                .builder()
                .exchangeAdapter(WebClientAdapter.create(webClient))
                .build()
                .createClient(AppleHttpInterface.class);
    }
}
