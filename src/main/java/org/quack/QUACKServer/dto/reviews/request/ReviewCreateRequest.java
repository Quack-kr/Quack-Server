package org.quack.QUACKServer.dto.reviews.request;

import jakarta.validation.Valid;
import lombok.Getter;

import java.util.List;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.dto.reviews
 * @fileName : ReviewCreateRequest
 * @date : 25. 3. 30.
 */
public record ReviewCreateRequest(
        List<@Valid ReviewMenuCreateItem> menus,
        List<@Valid ReviewKeywordCreateItem> keywords,
        List<Long> photoIds,
        String content
) {
}
