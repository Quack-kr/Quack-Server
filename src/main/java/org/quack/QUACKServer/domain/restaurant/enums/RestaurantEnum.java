package org.quack.QUACKServer.domain.restaurant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.quack.QUACKServer.domain.restaurant.domain.RestaurantCategory;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.restaurant.enums
 * @fileName : RestaurantEnum
 * @date : 25. 4. 27.
 */
public interface RestaurantEnum {

    @Getter
    @AllArgsConstructor
    enum ParkingType {
        FREE("FREE", "무료 주차"),
        PAID("PAID", "유료 주차"),
        IMPOSSIBLE("IMPOSSIBLE", "주차 불가");

        private final String value;
        private final String description;
    }

    @Getter
    @AllArgsConstructor
    enum RestaurantKeyword {
        TASTE("TASTE", "맛"),
        SIDE_MENU("SIDE_MENU", "사이드메뉴"),
        INGREDIENT("INGREDIENT", "재료 신선도"),
        NOISE("NOISE", "가게 소음도"), // 불편한점만
        MENU("MENU", "메뉴 구성도"),
        RESTROOM("RESTROOM", "화장실 청결"),
        ATMOSPHERE("ATMOSPHERE", "분위기"),
        SERVICE("SERVICE", "서비스 만족도"),
        PARKING("PARKING", "주차 편의성"),
        COST("COST", "가격 가성비"),
        TIME("TIME", "음식 나오는 속도"),
        SEAT("SEAT", "좌석 편의도"),
        HYGIENE("HYGIENE", "위생도"),
        WAITING_TIME("WAITING_TIME", "웨이팅 시간"),
        MEAT_SERVICE("MEAT_SERVICE", "고기 구어주는 서비스"),
        MEAT_STATUS("MEAT_STATUS", "고기 상태")
        ;

        private final String value;
        private final String description;
    }

    @Getter
    @AllArgsConstructor
    enum ReviewKeywordType {

        NEGATIVE("NEGATIVE", "안좋았던점"),
        POSITIVE("POSITIVE", "좋았던점")
        ;

        private final String value;
        private final String description;
    }

    @Getter
    @AllArgsConstructor
    enum RestaurantOrderByType {
        LATEST("LATEST","최신순"),
        LIKE("LIKE", "공감순")
        ;


        private final String value;
        private final String description;
    }

    @Getter
    @AllArgsConstructor
    enum RestaurantCategoryType {
        CHICKEN("CHICKEN","치킨"),
        PIZZA("PIZZA","피자"),
        HAMBURGER("HAMBURGER","햄버거"),
        CHINESE("CHINESE","중식"),
        ASIAN("ASIAN","아시안"),
        JAPANESE("JAPANESE","일식"),
        RICE_SOUP("RICE_SOUP","국밥"),
        SNACK("SNACK","분식"),
        JOKBAL("JOKBAL","족발"),
        MEAT("MEAT","고기"),
        KOREAN("KOREAN","한식"),
        WESTERN("WESTERN","양식"),
        SALAD("SALAD","샐러드"),
        BAR("BAR","주점"),
        SEAFOOD("SEAFOOD","해산물")
        ;

        private final String value;
        private final String description;
    }
}
