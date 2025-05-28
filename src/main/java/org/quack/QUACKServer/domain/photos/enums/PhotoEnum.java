package org.quack.QUACKServer.domain.photos.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.photos.enums
 * @fileName : PhotoEnum
 * @date : 25. 5. 24.
 */
public interface PhotoEnum {
    @Getter
    @AllArgsConstructor
    enum PhotoType {

        DEFAULT_PROFILE("/profile/", "프로필 디폴트 이미지"),
        RESTAURANT("restaurant", "식당 사진"),
        MENU_BOARD("menu_board", "메뉴판 사진"),
        REVIEW("review", "리뷰 남긴 사진"),
        ;

        private final String value;
        private final String description;
    }
}
