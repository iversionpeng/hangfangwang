package com.okcoin.house.common.support.enums;

import com.okcoin.house.common.support.i18n.LocaleUtils;

public enum NoticeType {

    REGISTER_NOTICE(10000),
    CONSULT_NOTICE(10001),
    LEAVING_MSG_NOTICE(10003),
    ;

    private Integer code;

    NoticeType(int code) {
        this.code = code;
    }

    public static NoticeType valueOf(int code) {
        for (NoticeType noticeType : NoticeType.values()) {
            if (code == noticeType.code) {
                return noticeType;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return LocaleUtils.getMessage("notice.code.biz." + code);
    }

    public String getMessage(String[] args) {
        return LocaleUtils.getMessage("notice.code.biz." + code, args);
    }

    @Override
    public String toString() {
        return "[" + getCode() + "]" + getMessage();
    }


}
