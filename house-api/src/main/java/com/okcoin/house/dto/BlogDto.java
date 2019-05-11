package com.okcoin.house.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: liupeng
 * @date: 2019/5/11 19:24
 * @description(功能描述):
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BlogDto implements Serializable {
    private Long id;
    private String tags;
    private String content;
    private String title;
    private Date createTime;
    private String digest;
    /**
     * 分类1-准备买房 2-看房/选房 3-签约/订房 4-全款/贷款 5-缴税/过户 6-入住/交接 7-买房风险
     */
    private Integer cat;

    private List<String> tagList;
}
