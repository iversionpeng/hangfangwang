package com.okcoin.house.api.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by peng liu 2019/04/03
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User implements Serializable {
    private Integer id;

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
     * 经过md5加密的密码
     */
    private String passwd;

    /**
     * 头像图片
     */
    private String avatar;

    /**
     * 1:普通用户 2:房产经纪人
     */
    private Boolean type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否启用 1启用 0停用
     */
    private Boolean enable;

    /**
     * 所属经济机构
     */
    private Integer agencyId;

    private static final long serialVersionUID = 1L;
}