package com.okcoin.house.dao.main;

import com.okcoin.house.api.domain.Agency;

import java.util.List;

/**
 * Created by Mybatis Generator 2019/04/17
 */
public interface AgencyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Agency record);

    int insertSelective(Agency record);

    Agency selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Agency record);

    int updateByPrimaryKey(Agency record);

    List<Agency> select();
}