package org.quack.QUACKServer.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { NicknameValidator.class })
public @interface NicknameConstraint {

    String message() default "invalid nickname";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}