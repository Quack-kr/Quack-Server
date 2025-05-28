package org.quack.QUACKServer.domain.photos.repository;

import java.util.List;
import org.quack.QUACKServer.domain.photos.domain.Photos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.photos.repository
 * @fileName : PhotosRepository
 * @date : 25. 5. 24.
 */
public interface PhotosRepository extends JpaRepository<Photos, Long> {

    Optional<Photos> findFirstByTargetIdAndPhotoType(Long targetId, String photoType);
    List<Photos> findAllByTargetIdAndPhotoType(Long targetId, String photoType);

}
