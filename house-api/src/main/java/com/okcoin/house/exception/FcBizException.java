package com.okcoin.house.exception;

import com.okcoin.house.common.support.enums.BizErrorCodeEnum;
import lombok.Getter;

/**
 * @Auther: liupeng
 * @Date: 2019/4/9 16:14
 * @Description(功能描述): 业务层异常类
 */
@Getter
public class FcBizException extends FcException {
    private int code;

    public FcBizException(BizErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage(), false);
        code = errorCodeEnum.getCode();
    }

    public FcBizException(BizErrorCodeEnum errorCodeEnum, Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
    }

    public FcBizException(int code, String msg) {
        super(msg, false);
        this.code = code;
    }
}
