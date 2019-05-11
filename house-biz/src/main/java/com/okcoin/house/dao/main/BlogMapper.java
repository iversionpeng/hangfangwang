package com.okcoin.house.dao.main;

import com.okcoin.house.api.domain.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* Created by Mybatis Generator 2019/04/17
*/
public interface BlogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKey(Blog record);

     List<Blog> selectBlog(@Param("blog")Blog query);

     Long selectBlogCount(Blog query);
}