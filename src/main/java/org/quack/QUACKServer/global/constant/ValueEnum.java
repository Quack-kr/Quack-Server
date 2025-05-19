package org.quack.QUACKServer.global.constant;

import java.util.Arrays;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.constant
 * @fileName : ValueEnum
 * @date : 25. 5. 4.
 */
public interface ValueEnum {

    String NUMBER_FORMAT_EXCEPTION_MESSAGE = "정수형으로 변환할 수 없는 코드입니다 : ";
    String INVALID_VALUE_TYPE_MESSAGE = "유효하지 않은 유형의 코드입니다 : ";
    String INVALID_VALUE_ENUM_MESSAGE = "유효하지 않은 코드입니다 : ";

    static <E extends Enum<E> & ValueEnum> E of(Class<E> enumClass, Object code) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> switch (code) {
                    case String stringValue -> stringValue.equals(e.getValue() instanceof String s ? s : String.valueOf(e.getValue()));
                    case null, default -> throw new IllegalArgumentException(INVALID_VALUE_TYPE_MESSAGE + e.getValue().getClass());
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_VALUE_TYPE_MESSAGE + code));
    }

    Object getValue();

    Object getDescription();

}
