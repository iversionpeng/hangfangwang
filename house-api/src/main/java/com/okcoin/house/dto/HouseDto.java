package com.okcoin.house.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by peng liu 2019/04/17
*/
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HouseDto implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 房产名称
     */
    private String name;

    /**
     * 单位元
     */
    private BigDecimal price;

    /**
     * 图片地址
     */
    private String firstImg;

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
     * 房产地址
     */
    private String address;


    private static final long serialVersionUID = 1L;
}