package com.okcoin.house.dao.main;

import com.okcoin.house.api.domain.HouseUser;

/**
* Created by Mybatis Generator 2019/04/17
*/
public interface HouseUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HouseUser record);

    int insertSelective(HouseUser record);

    HouseUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HouseUser record);

    int updateByPrimaryKey(HouseUser record);
}