package com.okcoin.house.api.service;

import com.okcoin.house.dto.CommentDto;

import java.util.List;

public interface CommentService {

    void addHouseComment(Long houseId, String content, Long userId);

    List<CommentDto> getHouseComments(long houseId, int size);

    List<CommentDto> getBlogComments(long blogId, int size);

    void addBlogComment(int blogId, String content, Long userId);

    void addComment(Long houseId, Integer blogId, String content, Long userId, int type);
}
