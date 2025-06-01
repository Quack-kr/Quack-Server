package org.quack.QUACKServer.global.external.redis;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.infra.redis
 * @fileName : RedisKeyManager
 * @date : 25. 6. 1.
 */
@Component
public class RedisKeyManager {

    private final List<String> keys;

    private RedisKeyManager() {
        this.keys = new ArrayList<>();
    }
    public static RedisKeyManagerBuilder builder() {
        return new RedisKeyManagerBuilder();
    }

    public static class RedisKeyManagerBuilder {

        private final RedisKeyManager instance;

        private RedisKeyManagerBuilder() {
            this.instance = new RedisKeyManager();
        }

        public RedisKeyManagerBuilder append(String key) {
            instance.keys.add(key);
            return this;
        }

        public String build() {
            return String.join(":", instance.keys);
        }
    }
}
