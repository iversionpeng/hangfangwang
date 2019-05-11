package com.okcoin.house.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.okcoin.house.api.domain.Blog;
import com.okcoin.house.api.service.BlogService;
import com.okcoin.house.common.support.model.Pager;
import com.okcoin.house.dao.main.BlogMapper;
import com.okcoin.house.dto.BlogDto;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: liupeng
 * @date: 2019/5/11 19:10
 * @description(功能描述):
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Pager<BlogDto> queryBlog(Blog query, Integer pageNum, Integer pageSize) {
        PageInfo<Blog> result = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> blogMapper.selectBlog(query));
        List<Blog> blogs = result.getList();
        List<BlogDto> blogDtos = blogs.stream().map(x -> BlogDto.builder()
                .content(x.getContent())
                .createTime(x.getCreateTime())
                .id(x.getId())
                .tags(x.getTags())
                .title(x.getTitle())
                .cat(x.getCat())
                .build()).collect(Collectors.toList());
        populate(blogDtos);
        return new Pager<>(pageNum, pageNum, result.getTotal(), blogDtos);
    }

    private void populate(List<BlogDto> blogs) {
        if (!blogs.isEmpty()) {
            blogs.stream().forEach(item -> {
                String stripped = Jsoup.parse(item.getContent()).text();
                item.setDigest(stripped.substring(0, Math.min(stripped.length(), 40)));
                String tags = item.getTags();
                item.getTagList().addAll(Lists.newArrayList(Splitter.on(",").split(tags)));
            });
        }
    }

    @Override
    public Blog queryOneBlog(Long id) {
        Blog query = Blog.builder().id(id).build();
        PageInfo<Blog> result = PageHelper.startPage(1, 1).doSelectPageInfo(() -> blogMapper.selectBlog(query));
        List<Blog> blogs = result.getList();
        if (CollectionUtils.isNotEmpty(blogs)) {
            return blogs.get(0);
        }
        return null;
    }


}
