package com.okcoin.house.api.domain;

import java.io.Serializable;

import lombok.*;

/**
* Created by peng liu 2019/04/17
*/
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Agency implements Serializable {
    private Long id;

    /**
     * 经纪机构名称
     */
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 手机
     */
    private String phone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 描述
     */
    private String aboutUs;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 网站
     */
    private String webSite;

    private static final long serialVersionUID = 1L;
}