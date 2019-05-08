package com.okcoin.house.common.support.model;

import com.okcoin.house.common.support.enums.BizErrorCodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: liupeng
 * @Date: 2019/4/5 15:37
 * @Description(功能描述):
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private String msg;
    private String detailMsg;
    private T data;

    /**
     * success
     */
    public static ResponseResult success() {
        return new ResponseResult(null);
    }

    public static <T> ResponseResult success(T data) {
        return new ResponseResult(data);
    }

    /**
     * failed
     */
    public static ResponseResult failed(BizErrorCodeEnum errorCodeEnum) {
        return new ResponseResult(errorCodeEnum.getCode(), errorCodeEnum.getMessage());
    }

    private ResponseResult(T data) {
        this.code = 200;
        this.data = data;
        this.msg = "success";
    }

    private ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
