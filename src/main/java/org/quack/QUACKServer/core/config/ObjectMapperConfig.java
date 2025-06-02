package org.quack.QUACKServer.core.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.config.app
 * @fileName : ObjectMapperConfig
 * @date : 25. 4. 20.
 */
@Configuration
@Primary
public class ObjectMapperConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer namingStrategyCustomizer() {
        return builder -> builder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

}