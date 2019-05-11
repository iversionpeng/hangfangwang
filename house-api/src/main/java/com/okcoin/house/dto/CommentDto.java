package com.okcoin.house.dto;

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
public class CommentDto implements Serializable {
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

    private String Avatar;
    private String UserName;

    private static final long serialVersionUID = 1L;
}