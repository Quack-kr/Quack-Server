package org.quack.QUACKServer.domain.review.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.quack.QUACKServer.domain.restaurant.dto.request.RestaurantSubtractFilterItem;
import org.quack.QUACKServer.domain.review.domain.Review;
import org.quack.QUACKServer.global.constant.ValueEnum;

public interface ReviewEnum {

    @Getter
    @AllArgsConstructor
    enum ReviewTag {
        TASTE("TASTE", "맛"),
        SIDE_MENU("SIDE_MENU", "사이드메뉴"),
        INGREDIENT("INGREDIENT", "재료 신선도"),
        NOISE("NOISE", "가게 소음도"),
        MENU("MENU", "메뉴 구성도"),
        TOILET("TOILET", "화장실 청결"),
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

        // private final Integer code;
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
    enum ReviewLikeType{

        LIKE("LIKE", "핵공감"),
        DISLIKE("DISLIKE", "비공감")
        ;

        private final String value;
        private final String description;
    }
}
