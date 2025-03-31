package org.quack.QUACKServer.dto.reviews;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.quack.QUACKServer.domain.User;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.dto.reviews
 * @fileName : ReviewsSelectUserItem
 * @date : 25. 3. 31.
 */

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewsSelectUserItem {
    private final Long userId;
    private final String userName;


    public static ReviewsSelectUserItem from(User user) {
        return ReviewsSelectUserItem.builder()
                .userId(user.getUserId())
                .userName(user.getNickname())
                .build();
    }
}
