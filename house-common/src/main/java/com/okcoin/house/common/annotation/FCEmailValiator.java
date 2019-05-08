package com.okcoin.house.common.annotation;

import com.okcoin.house.common.support.enums.BizErrorCodeEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: liupeng
 * @date: 2019/4/24 12:49
 * @description(功能描述): 邮箱验证
 */
public class FCEmailValiator implements ConstraintValidator<FCEmail, String> {
    @Override
    public void initialize(FCEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        context.disableDefaultConstraintViolation();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        boolean isMatch = m.matches();
        if (!isMatch) {

            context.buildConstraintViolationWithTemplate(BizErrorCodeEnum.EMAIL_FORMAT_ERROR.getMessage()).addConstraintViolation();
            return false;
        }
        return true;
    }


}
