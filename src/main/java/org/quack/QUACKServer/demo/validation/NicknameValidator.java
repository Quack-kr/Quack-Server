package org.quack.QUACKServer.demo.validation;

import static org.quack.QUACKServer.demo.exception.errorCode.CustomUserError.INVALID_BLANK_NICKNAME;
import static org.quack.QUACKServer.demo.exception.errorCode.CustomUserError.INVALID_LENGTH_NICKNAME;
import static org.quack.QUACKServer.demo.exception.errorCode.CustomUserError.INVALID_NULL_NICKNAME;
import static org.quack.QUACKServer.demo.exception.errorCode.CustomUserError.INVALID_PATTERN_NICKNAME;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import org.quack.QUACKServer.demo.exception.exception.CustomUserException;

public class NicknameValidator implements ConstraintValidator<NicknameConstraint, String> {

    private static final Pattern REGX = Pattern.compile("^[가-힣a-zA-Z0-9]+(?: [가-힣a-zA-Z0-9]+)*$");

    @Override
    public void initialize(NicknameConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext context) {

        if (nickname == null) {
            throw new CustomUserException(INVALID_NULL_NICKNAME, "nickname can't be null");
        }
        if (nickname.isBlank()) {
            throw new CustomUserException(INVALID_BLANK_NICKNAME, "nickname can't be blank");
        }
        if (nickname.length() <= 2 || nickname.length() > 15) {
            throw new CustomUserException(INVALID_LENGTH_NICKNAME, "nickname must be between 2 and 15");
        }
        if (!REGX.matcher(nickname).matches()) {
            throw new CustomUserException(INVALID_PATTERN_NICKNAME,
                    "nickname can be used in English, Korean, numbers ");
        }

        return true;
    }
}
