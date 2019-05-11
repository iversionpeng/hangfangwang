package com.okcoin.house.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
     * 单位万元
     */
    private BigDecimal price;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 1:上架 2:下架
     */
    private Integer state;
    /**
     * 描述
     */
    private String remarks;


    /**
     * 城市名称
     */
    private Integer cityId;
    /**
     * 城镇名称
     */
    private Integer town;
    /**
     * 卧室数量
     */
    private Integer beds;
    /**
     * 1：销售  2:出租
     */
    private String typeStr;
    private Integer type;

    /**
     * 面积
     */
    private Integer area;

    /**
     * 卫生间数量
     */
    private Integer baths;
    /**
     * 评级
     */
    private Double rating;

    /**
     * 房产地址
     */
    private String address;

    /**
     * 房屋图片
     */
    private List<MultipartFile> houseFiles;
    /**
     * 户型图片
     */
    private List<MultipartFile> floorPlanFiles;
    /**
     * 房屋特点
     */
    private List<String> featureList;

    /**
     * 第一张图片地址
     */
    private String firstImg;

    private List<String> imageList;
    private List<String> floorPlanList;

    private String sort;
    private Long userId;
    private Boolean Bookmarked;
    private Integer communityId;
    private List<Long> ids;


    private static final long serialVersionUID = 1L;
}