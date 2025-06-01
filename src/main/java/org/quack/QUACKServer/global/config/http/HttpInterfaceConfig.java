package org.quack.QUACKServer.global.config.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.extern.slf4j.Slf4j;
import org.quack.QUACKServer.global.external.social.apple.AppleHttpInterface;
import org.quack.QUACKServer.global.external.social.kakao.KakaoHttpInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.io.IOException;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.http
 * @fileName : HttpInterfaceConfig
 * @date : 25. 4. 13.
 */
@Slf4j
@Configuration
public class HttpInterfaceConfig {

    private final String appleApiBaseUrl;
    private final String kakaoApiBaseUrl;
    private final DefaultListableBeanFactory beanFactory;
    private final GlobalHttpInterfaceFactory globalHttpInterfaceFactory;
    // private final String naverApiBaseUrl;


    public HttpInterfaceConfig(
            DefaultListableBeanFactory beanFactory,
            @Value("${social.apple.base-url}") String appleApiBaseUrl,
            @Value("${social.kakao.base-url}") String kakaoApiBaseUrl
    ) {
        this.appleApiBaseUrl = appleApiBaseUrl;
        this.kakaoApiBaseUrl = kakaoApiBaseUrl;
        this.beanFactory = beanFactory;
        this.globalHttpInterfaceFactory = new GlobalHttpInterfaceFactory();
    }


    private RestClient createRestClient(String baseUrl, String beanName) {
        RestClient newRestClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, this::handleError)
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, this::handleError)
                .requestInterceptors(interceptors -> interceptors.add(clientHttpRequestInterceptor()))
                .build();

        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(RestClient.class, () -> newRestClient)
                .getRawBeanDefinition();

        beanFactory.registerBeanDefinition(beanName, beanDefinition);
        return newRestClient;
    }

    private void handleError(HttpRequest request, ClientHttpResponse response) {
        try {
            log.error("외부 API 통신 중 에러 발생");
            log.error("응답 코드 = {}", response.getStatusCode());
            log.error("에러 응답 바디 = {}", new String(response.getBody().readAllBytes()));
        } catch (IOException e) {
             log.error("응답 바디 읽는 중 에러 발생", e);
        }
    }

    private ClientHttpRequestInterceptor clientHttpRequestInterceptor() {
        return (request, body, execution) -> {
            ObjectMapper objectMapper = snakeObjectMapper().getObjectMapper();

            log.info("Request URI = [{}]  {}", request.getMethod(), request.getURI());
            log.info("Request Headers = {}", request.getHeaders());
            if (body.length > 0) {
                log.info("Request Body = {}", objectMapper.writeValueAsString(objectMapper.readValue(body, Object.class)));
            }

            return execution.execute(request, body);
        };
    }

    private MappingJackson2HttpMessageConverter snakeObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        return converter;
    }

    @Bean
    public AppleHttpInterface appleHttpInterface() {
        Class<AppleHttpInterface> interfaceClass = AppleHttpInterface.class;
        RestClient restClient = createRestClient(appleApiBaseUrl, interfaceClass.getSimpleName());
        return globalHttpInterfaceFactory.create(interfaceClass, restClient);
    }

    @Bean
    public KakaoHttpInterface kakaoHttpInterface() {
        Class<KakaoHttpInterface> interfaceClass = KakaoHttpInterface.class;
        RestClient restClient = createRestClient(kakaoApiBaseUrl, interfaceClass.getSimpleName());
        return globalHttpInterfaceFactory.create(interfaceClass, restClient);
    }




}
