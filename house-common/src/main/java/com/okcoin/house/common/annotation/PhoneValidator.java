package com.okcoin.house.common.annotation;

import com.okcoin.house.common.support.enums.BizErrorCodeEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: liupeng
 * @date: 2019/4/17 22:08
 * @description(功能描述): 手机号验证
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {
    boolean required = false;

    @Override
    public void initialize(Phone phone) {
        required = phone.required();
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (!required) {
            return true;
        }
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        context.disableDefaultConstraintViolation();
        if (phone.length() != 11) {
            context.buildConstraintViolationWithTemplate(BizErrorCodeEnum.PHONE_NUMBER_ERROR.getMessage()).addConstraintViolation();
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                context.buildConstraintViolationWithTemplate(BizErrorCodeEnum.PHONE_FORMAT_ERROR.getMessage()).addConstraintViolation();
                return false;
            }
            return true;
        }
    }
}
