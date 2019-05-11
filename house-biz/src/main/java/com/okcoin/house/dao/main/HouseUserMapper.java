package com.okcoin.house.dao.main;

import com.okcoin.house.api.domain.HouseUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    HouseUser selectHouseUser(@Param("userId") Long userId,@Param("houseId") Long houseDtoId,@Param("type") Integer type);

    List<HouseUser> selectHouseUserByHouseId(@Param("houseId") Long id);

    void  deleteHouseUser(@Param("houseId") Long houseId,@Param("userId")Long UserId,@Param("type")Boolean type);
}