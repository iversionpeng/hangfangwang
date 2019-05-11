package com.okcoin.house.service;

import com.okcoin.house.api.domain.Comment;
import com.okcoin.house.dto.CommentDto;
import com.okcoin.house.api.domain.User;
import com.okcoin.house.api.service.CommentService;
import com.okcoin.house.api.service.UserService;
import com.okcoin.house.common.support.enums.CommonConstants;
import com.okcoin.house.dao.main.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: liupeng
 * @date: 2019/5/11 16:04
 * @description(功能描述):
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    @Override
    public void addHouseComment(Long houseId, String content, Long userId) {
        addComment(houseId, null, content, userId, 1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addComment(Long houseId, Integer blogId, String content, Long userId, int type) {
        Comment comment = new Comment();
        if (type == 1) {
            comment.setHouseId(houseId);
        } else {
            comment.setBlogId(blogId);
        }
        comment.setContent(content);
        comment.setUserId(userId);
        comment.setType(type == 1);
        comment.setCreateTime(new Date());
        commentMapper.insert(comment);
    }

    @Override
    public List<CommentDto> getHouseComments(long houseId, int size) {
        List<Comment> comments = commentMapper.selectComments(houseId, size);
        List<CommentDto> collect = comments.stream().map(x -> {
                    User user = userService.getUserByUserId(x.getUserId());
                    return CommentDto.builder()
                            .blogId(x.getBlogId())
                            .content(x.getContent())
                            .createTime(x.getCreateTime())
                            .houseId(x.getHouseId())
                            .id(x.getId())
                            .type(x.getType())
                            .userId(x.getUserId())
                            .Avatar(user.getAvatar())
                            .UserName(user.getName())
                            .build();
                }
        ).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<CommentDto> getBlogComments(long blogId, int size) {
        List<Comment> comments = commentMapper.selectBlogComments(blogId, size);
        List<CommentDto> collect = comments.stream().map(x -> {
                    User user = userService.getUserByUserId(x.getUserId());
                    return CommentDto.builder()
                            .blogId(x.getBlogId())
                            .content(x.getContent())
                            .createTime(x.getCreateTime())
                            .houseId(x.getHouseId())
                            .id(x.getId())
                            .type(x.getType())
                            .userId(x.getUserId())
                            .Avatar(user.getAvatar())
                            .UserName(user.getName())
                            .build();
                }
        ).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void addBlogComment(int blogId, String content, Long userId) {
        addComment(null, blogId, content, userId, CommonConstants.COMMENT_BLOG_TYPE);
    }


}
