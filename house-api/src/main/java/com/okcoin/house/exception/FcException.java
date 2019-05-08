package com.okcoin.house.exception;

/**
 * @Auther: liupeng
 * @Date: 2019/4/9 16:11
 * @Description(功能描述): 通用异常类
 */
public class FcException extends RuntimeException {
    public FcException() {
        super();
    }

    /**
     * 信息
     */
    public FcException(final String msg, final boolean writableStackTrace) {
        super(msg, null, false, writableStackTrace);
    }

    /**
     * 信息
     * 原因
     */
    public FcException(String message, Throwable cause) {
        super(message, cause);
    }
}
