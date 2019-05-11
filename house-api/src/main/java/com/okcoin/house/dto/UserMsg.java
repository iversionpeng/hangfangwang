package com.okcoin.house.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: liupeng
 * @date: 2019/5/11 16:46
 * @description(功能描述):
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMsg implements Serializable {
    private Long id;
    private String msg;
    private Long userId;
    private Date createTime;
    private Long agentId;
    private Long houseId;
    private String email;

    private String userName;

}
