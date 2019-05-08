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
public class HouseMsg implements Serializable {
    private Long id;

    /**
     * 消息
     */
    private String msg;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 经纪人id
     */
    private Long agentId;

    /**
     * 房屋id
     */
    private Long houseId;

    /**
     * 用户姓名
     */
    private String userName;

    private static final long serialVersionUID = 1L;
}