package com.okcoin.house.api.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
* Created by peng liu 2019/04/17
*/
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class House implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 房产名称
     */
    private String name;

    /**
     * 1：销售  2:出租
     */
    private Boolean type;

    /**
     * 单位元
     */
    private BigDecimal price;

    /**
     * 图片地址
     */
    private String image;

    /**
     * 面积
     */
    private Integer area;

    /**
     * 卧室数量
     */
    private Integer beds;

    /**
     * 卫生间数量
     */
    private Integer baths;

    /**
     * 评级
     */
    private Double rating;

    /**
     * 房产描述
     */
    private String remarks;

    /**
     * 属性
     */
    private String properties;

    /**
     * 户型图
     */
    private String floorPlan;

    /**
     * 标签
     */
    private String tags;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 城市名称
     */
    private Integer cityId;

    /**
     * 小区名称
     */
    private Integer communityId;

    /**
     * 房产地址
     */
    private String address;

    /**
     * 1:上架 2:下架
     */
    private Boolean state;

    private static final long serialVersionUID = 1L;
}