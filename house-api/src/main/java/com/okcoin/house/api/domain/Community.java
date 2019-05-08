package com.okcoin.house.api.domain;

import java.io.Serializable;
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
public class Community implements Serializable {
    private Integer id;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 小区编码
     */
    private String name;

    /**
     * 城市名称
     */
    private String cityName;

    private static final long serialVersionUID = 1L;
}