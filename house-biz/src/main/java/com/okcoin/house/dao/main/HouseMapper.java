package com.okcoin.house.dao.main;

import com.okcoin.house.api.domain.House;
import com.okcoin.house.dto.HouseDto;
import com.okcoin.house.dto.UserMsg;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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

    List<House> selectByNameType(@Param("name") String name, @Param("type") Integer type);

    List<House> selectPageHouses(@Param("house") HouseDto houseDto);

    House selectByHouseId(Long id);
}