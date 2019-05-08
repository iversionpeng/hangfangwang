package com.okcoin.house.common.support.enums;


import com.okcoin.house.common.support.i18n.LocaleUtils;

/**
 * Demo class
 *
 * @author peng liu
 * @date 2019/04/03
 */
public enum BizErrorCodeEnum implements ErrorCode {

    SERVER_ERROR(500),
    PHONE_NUMBER_ERROR(300),
    PHONE_FORMAT_ERROR(301),
    EMAIL_FORMAT_ERROR(600),
    EMAIL_IS_EXIST(601),
    EMAIL_CACHE_EXIST(602),
    PWD_NOT_SAME(700),
    PWD_INCORRECT(701),
    PARAM_BLANK(702),
    KEY_INVALID(750);

    private final int code;

    BizErrorCodeEnum(int code) {
        this.code = code;
    }

    public static BizErrorCodeEnum valueOf(int code) {
        for (BizErrorCodeEnum errorCodeEnum : BizErrorCodeEnum.values()) {
            if (code == errorCodeEnum.code) {
                return errorCodeEnum;
            }
        }
        return null;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage("error.code.biz." + code);
    }

    public String getMessage(String[] args) {
        return LocaleUtils.getMessage("error.code.biz." + code, args);
    }

    @Override
    public String toString() {
        return "[" + getCode() + "]" + getMessage();
    }
}
