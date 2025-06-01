package org.quack.QUACKServer.global.infra.redis.repository;

import java.util.Optional;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.infra.redis.repository
 * @fileName : RedisRepository
 * @date : 25. 6. 1.
 */
public interface RedisRepository<K, T> {

    Optional<T> get(K k);

    void insert(K k, T t, long timeout);

    boolean delete(K k);

}

