package com.okcoin.house.dao.main;

import com.okcoin.house.api.domain.House;

import java.util.List;

/**
* Created by Mybatis Generator 2019/04/17
*/
public interface HouseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(House record);

    int insertSelective(House record);

    House selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(House record);

    int updateByPrimaryKey(House record);

    List<House> getLateHouse();
}