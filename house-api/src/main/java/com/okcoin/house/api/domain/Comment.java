package com.okcoin.house.api.domain;

import java.io.Serializable;
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
public class Comment implements Serializable {
    private Long id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 房屋id
     */
    private Long houseId;

    /**
     * 发布时间
     */
    private Date createTime;

    /**
     * 博客id
     */
    private Integer blogId;

    /**
     * 类型1-用户评论 2-博客评论
     */
    private Boolean type;

    /**
     * 评论id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}