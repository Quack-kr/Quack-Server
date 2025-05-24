package org.quack.QUACKServer.global.infra.s3.repository;

import java.io.IOException;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.infra.s3
 * @fileName : S3Repository
 * @date : 25. 5. 24.
 */
public interface S3Repository<K, T> {

    T upload(K k) throws IOException;

    void delete(K k);

    T convertPath(K k);

}
