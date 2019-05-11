package com.okcoin.house.web;

import com.okcoin.house.api.service.CommentService;
import com.okcoin.house.common.support.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: liupeng
 * @date: 2019/5/11 16:33
 * @description(功能描述):
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping(value = "/leaveComment")
    public String leaveComment(@RequestParam("content") String content, @RequestParam("houseId") Long houseId) {
        commentService.addHouseComment(houseId, content, UserContext.getUserHolder().getUseId());
        return "redirect:/house/detail?id=" + houseId;
    }

    @PostMapping(value = "/leaveBlogComment")
    public String leaveBlogComment(@RequestParam("content") String content, @RequestParam("blogId") Integer blogId) {
        commentService.addBlogComment(blogId, content, UserContext.getUserHolder().getUseId());
        return "redirect:/blog/detail?id=" + blogId;
    }

}