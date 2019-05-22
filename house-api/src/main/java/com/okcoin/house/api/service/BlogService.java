package com.okcoin.house.api.service;

import com.okcoin.house.api.domain.Blog;
import com.okcoin.house.common.support.model.Pager;
import com.okcoin.house.dto.BlogDto;

public interface BlogService {
    Pager<BlogDto> queryBlog(Blog query, Integer pageNum, Integer pageSize);

    BlogDto queryOneBlog(Long id);
}
