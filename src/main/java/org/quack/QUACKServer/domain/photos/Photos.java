package org.quack.QUACKServer.domain.photos;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.photos
 * @fileName : Photos
 * @date : 25. 5. 18.
 */

@Entity
@Table(name = "photos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photos {

    @Id
    @Column(name = "photos_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photosId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "photo_type")
    private String photoType;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
