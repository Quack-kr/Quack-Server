package org.quack.QUACKServer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration // Spring 이 인식하도록 설정
@EnableJpaAuditing // EntityListeners 를 사용하기 위해 필요
public class JpaConfig {
}
