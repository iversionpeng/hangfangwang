package com.okcoin.house.dao.main;

import com.okcoin.house.api.domain.Comment;

/**
* Created by Mybatis Generator 2019/04/17
*/
public interface CommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
}