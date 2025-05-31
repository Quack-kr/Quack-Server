package org.quack.QUACKServer.domain.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.util.Arrays;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.domain.auth.enums
 * @fileName : AuthEnum
 * @date : 25. 5. 25.
 */
public interface AuthEnum {
    @Getter
    @AllArgsConstructor
    enum NicknameValidateCode  {

        USER_NOT_FOUND("user-001", "유저를 찾을 수 없습니다."),
        CURRENT_USE_NICKNAME("user-002", "현재 설정된 닉네임입니다."),
        DUPLICATE_NICKNAME("user-003", "이미 사용중인 닉네임입니다."),
        INVALID_NULL_NICKNAME("user-004", "닉네임은 null일 수 없습니다."),
        INVALID_BLANK_NICKNAME("user-005", "닉네임은 blank일 수 없습니다."),
        INVALID_LENGTH_NICKNAME("user-006", "닉네임은 2자 이상 15자 미만으로 입력해야 합니다."),
        INVALID_PATTERN_NICKNAME("user-007", "닉네임은 영어, 한글, 숫자로만 사용할 수 있습니다.");

        private final String value;
        private final String description;

    }
    @Getter
    @AllArgsConstructor
    enum NicknameColorPrefix  {

        RED("RED","붉은"),
        ORANGE("ORANGE","주황"),
        YELLOW("YELLOW","노란"),
        GREEN("GREEN","초록"),
        BLUE("BLUE","푸른"),
        PURPLE("PURPLE","보라"),
        PINK("PINK","핑크");

        private final String value;
        private final String description;

        public static NicknameColorPrefix of(DayOfWeek day) {
            switch (day) {
                case MONDAY-> {
                    return RED;
                }
                case TUESDAY-> {
                    return ORANGE;
                }
                case WEDNESDAY-> {
                    return YELLOW;
                }
                case THURSDAY-> {
                    return GREEN;
                }
                case FRIDAY-> {
                    return BLUE;
                }
                case SATURDAY-> {
                    return PINK;
                }
                case SUNDAY-> {
                    return PURPLE;
                }
                case null, default -> {
                    return null;
                }
            }
        }

        public static NicknameColorPrefix getByNickname(String nickname) {
            return Arrays.stream(values())
                    .filter(e -> nickname.startsWith(e.description))
                    .findFirst()
                    .orElse(null);
        }
    }
    @Getter
    @AllArgsConstructor
    enum NicknameMenuPrefix  {

        TANGSUYUK("TANGSUYUK","탕수육"),
        GIMBAP("GIMBAP","김밥"),
        JJAJANGMYEON("JJAJANGMYEON","자장면"),
        RAMYEON("RAMYEON","라면"),
        JJAMPPONG("JJAMPPONG","짬뽕"),
        SUYUK("SUYUK","수육"),
        JOKBAL("JOKBAL","족발"),
        DONKATSU("DONKATSU","돈까스"),
        TTEOKBOKKI("TTEOKBOKKI","떡볶이"),
        SUNDAE("SUNDAE","순대"),
        DAKBAL("DAKBAL","닭발"),
        SAMGYEOPSAL("SAMGYEOPSAL", "삼겹살")
        ;
        private final String value;
        private final String description;


        public static NicknameMenuPrefix of(Integer hour) {
            switch (hour) {
                case 0 -> {
                    return TANGSUYUK;
                }
                case 1, 13 -> {
                    return GIMBAP;
                }
                case 2, 14 -> {
                    return JJAJANGMYEON;
                }
                case 3, 15-> {
                    return RAMYEON;
                }
                case 4, 16-> {
                    return JJAMPPONG;
                }
                case 5, 17 -> {
                    return SUYUK;
                }
                case 6, 18 -> {
                    return JOKBAL;
                }
                case 7, 19 -> {
                    return DONKATSU;
                }
                case 8, 20 -> {
                    return TTEOKBOKKI;
                }
                case 9, 21 -> {
                    return SUNDAE;
                }
                case 10, 22 -> {
                    return DAKBAL;
                }
                case 11, 23 -> {
                    return SAMGYEOPSAL;
                }
                case null, default -> {
                    return null;
                }
            }
        }

        public static NicknameMenuPrefix getByNickname(String nickname) {
            return Arrays.stream(values())
                    .filter(e -> nickname.startsWith(e.description))
                    .findFirst()
                    .orElse(null);
        }

    }

}
