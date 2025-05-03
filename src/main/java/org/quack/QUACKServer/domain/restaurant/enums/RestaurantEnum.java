package org.quack.QUACKServer.domain.restaurant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
    enum RestaurantSortType {
        LATEST("LATEST","저장순"),
        LIKE("LIKE", "미친 맛 순"),
        DISTANCE("DISTANCE", "거리순")
        ;

        private final String value;
        private final String description;
    }

    // 필터
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

    @Getter
    @AllArgsConstructor
    enum FavorFilterType {
        TASTE_PLAIN("TASTE_PLAIN","맛이 평범한 곳"),
        SIDE_PLAIN("SIDE_PLAIN","사이드메뉴 평범한 곳"),
        INGREDIENT_NOT_FRESH("INGREDIENT_NOT_FRESH","재료가 신선하지 않은 곳"),
        MENU_LACKING("MENU_LACKING","메뉴구성이 아쉬운 곳")
        ;
        private final String value;
        private final String description;
    }

    @Getter
    @AllArgsConstructor
    enum AtmosphereFilterType{
        NOISY("NOISY", "시끄러운 곳"),
        ATMOSPHERE_LACKING("ATMOSPHERE_LACKING", "분위기 아쉬운 곳");

        private final String value;
        private final String description;
    }

    @Getter
    @AllArgsConstructor
    enum ToiletFilterType {
        DIRTY("DIRTY", "더러운 곳"),
        UNISEX("UNISEX", "남녀공용인 곳")
        ;

        private final String value;
        private final String description;
    }


    @Getter
    @AllArgsConstructor
    enum ServiceFilterType{
        NO_PARKING("NO_PARKING","주차불가"),
        PAID_PARKING("PAID_PARKING","유료주차"),
        UNFRIENDLY("UNFRIENDLY","불친절한 곳")
        ;

        private final String value;
        private final String description;
    }

    @Getter
    @AllArgsConstructor
    enum AtcFilterType{
        BAD_VALUE("BAD_VALUE","가성비가 안좋은 곳"),
        SLOW_FOOD("SLOW_FOOD","음식이 오래걸리는 곳"),
        UNCOMFORTABLE_SEAT("UNCOMFORTABLE_SEAT","좌석이 불편한 곳"),
        UNHYGIENIC("UNHYGIENIC","비위생적인 곳"),
        LONG_WAITING("LONG_WAITING","웨이팅이 긴 곳"),
        NO_MEAT_GRILL_HELP("NO_MEAT_GRILL_HELP","고기 안구워주는 곳");

        private final String value;
        private final String description;
    }

}
