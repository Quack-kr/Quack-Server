package org.quack.QUACKServer.photos.service;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.photos.service
 * @fileName : PhotoService
 * @date : 25. 5. 24.
 */
public interface PhotoService<T, K> {

    T upload(K k);
}
