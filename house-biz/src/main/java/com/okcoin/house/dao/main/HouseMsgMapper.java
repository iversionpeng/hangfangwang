package com.okcoin.house.dao.main;

import com.okcoin.house.api.domain.HouseMsg;
import com.okcoin.house.dto.UserMsg;

/**
* Created by Mybatis Generator 2019/04/17
*/
public interface HouseMsgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HouseMsg record);

    int insertSelective(HouseMsg record);

    HouseMsg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HouseMsg record);

    int updateByPrimaryKey(HouseMsg record);

    void insertUserMsg(UserMsg userMsg);
}