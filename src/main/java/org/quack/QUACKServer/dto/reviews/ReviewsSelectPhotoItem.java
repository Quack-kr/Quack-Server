package org.quack.QUACKServer.dto.reviews;

import lombok.*;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.dto.reviews
 * @fileName : ReviewsSelectPhotoItem
 * @date : 25. 3. 31.
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class ReviewsSelectPhotoItem {
    private final String reviewImgPath;
    private final Long reviewImgId;
}
