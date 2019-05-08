package com.okcoin.house.common.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@NotNull
@Constraint(validatedBy = {FCEmailValiator.class})
public @interface FCEmail {

    String message() default "email not inavailable";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}