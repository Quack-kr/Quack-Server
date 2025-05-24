package org.quack.QUACKServer.domain.photos.repository;

import org.quack.QUACKServer.domain.photos.domain.Photos;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.photos.repository
 * @fileName : PhotosRepository
 * @date : 25. 5. 24.
 */
public interface PhotosRepository extends JpaRepository<Photos, Long> {
}
