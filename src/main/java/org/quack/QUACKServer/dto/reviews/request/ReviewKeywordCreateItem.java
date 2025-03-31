package org.quack.QUACKServer.dto.reviews.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.dto.reviews.request
 * @fileName : ReviewMenuCreateItem
 * @date : 25. 3. 30.
 */

public record ReviewKeywordCreateItem(@NotNull Long keywordId, @NotBlank String keywordName, String keywordType) {

}
