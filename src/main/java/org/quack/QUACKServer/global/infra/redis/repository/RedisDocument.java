package org.quack.QUACKServer.global.infra.redis.repository;

import lombok.Getter;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.infra.redis.repository
 * @fileName : RedisDocument
 * @date : 25. 6. 1.
 */
public interface RedisDocument {

    @Getter
    enum hashKey {
        AUTH_TOKEN("AUTH");

        private final String prefix;

        hashKey(String prefix) {
            this.prefix = prefix;
        }
    }
}
