package org.quack.QUACKServer.core.util.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @author : chanhyun9
 * @description :
 * @packageName : org.quack.QUACKServer.global.util.validation
 * @fileName : NicknameValidator
 * @date : 25. 4. 21.
 */

@Component
public class NicknameValidator implements ConstraintValidator<NicknameConstraint, String> {

    private static final Pattern REGX = Pattern.compile("^[가-힣a-zA-Z0-9]+(?: [가-힣a-zA-Z0-9]+)*$");

    @Override
    public void initialize(NicknameConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext context) {

        if (nickname == null) {
            throw new IllegalArgumentException("nickname can't be null");
        }
        if (nickname.isBlank()) {
            throw new IllegalArgumentException("닉네임을 입력하세요.");
        }
        if (nickname.length() <= 2 || nickname.length() > 15) {
            throw new IllegalArgumentException("닉네임은 2자에서 15자 까지만 가능합니다.");
        }
        if (!REGX.matcher(nickname).matches()) {
            throw new IllegalArgumentException("닉네임은 한글만 가능합니다.");
        }

        return true;
    }
}
