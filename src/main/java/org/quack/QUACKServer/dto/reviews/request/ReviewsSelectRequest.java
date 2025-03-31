package org.quack.QUACKServer.dto.reviews.request;

import lombok.Getter;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.dto.reviews.request
 * @fileName : ReviewsSelectRequest
 * @date : 25. 3. 31.
 */
public record ReviewsSelectRequest(String reviewOrderType, Boolean isPhotoExist, Integer pageNum, Integer pageSize) {
}
