package com.okcoin.house.dao.main;

import com.okcoin.house.api.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.util.MessageSupplier;

import java.util.List;

/**
 * Created by Mybatis Generator 2019/04/03
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByEmail(@Param("email") String email);

    void delete(@Param("email") String email);

    void updateUserStatusByEmail(String email);

    List<User> select(User user);

    void updateUserByEmail(User updateUser);
}