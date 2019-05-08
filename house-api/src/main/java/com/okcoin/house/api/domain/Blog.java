package com.okcoin.house.api.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
* Created by peng liu 2019/04/17
*/
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Blog implements Serializable {
    private Long id;

    /**
     * 标签
     */
    private String tags;

    /**
     * 内容
     */
    private String content;

    /**
     * 发布时间
     */
    private Date createTime;

    /**
     * 标题
     */
    private String title;

    /**
     * 分类1-准备买房 2-看房/选房 3-签约/订房 4-全款/贷款 5-缴税/过户 6-入住/交接 7-买房风险
     */
    private Integer cat;

    private static final long serialVersionUID = 1L;
}