package com.okcoin.house.common.support.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liupeng
 * @date: 2019/5/9 11:23
 * @description(功能描述):
 */
@Data
@Builder
public class SecurityUser implements Serializable {

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 自我介绍
     */
    private String aboutme;


    /**
     * 头像图片
     */
    private String avatar;

    /**
     * 1:普通用户 2:房产经纪人
     */
    private Boolean type;

    /**
     * 所属经济机构
     */
    private Integer agencyId;
}
