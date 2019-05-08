package com.okcoin.house.dao.main;

import com.okcoin.house.api.domain.Community;

import java.util.List;

/**
* Created by Mybatis Generator 2019/04/17
*/
public interface CommunityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Community record);

    int insertSelective(Community record);

    Community selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Community record);

    int updateByPrimaryKey(Community record);

    List<Community> communityList();
}