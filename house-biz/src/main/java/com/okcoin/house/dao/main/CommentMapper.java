package com.okcoin.house.dao.main;

import com.okcoin.house.api.domain.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<Comment> selectComments(@Param("houseId") long houseId, @Param("size") int size);

    List<Comment> selectBlogComments(@Param("blogId")long blogId,@Param("size") int size);
}