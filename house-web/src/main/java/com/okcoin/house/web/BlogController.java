package com.okcoin.house.web;

import com.okcoin.house.api.domain.Blog;
import com.okcoin.house.api.domain.House;
import com.okcoin.house.api.service.BlogService;
import com.okcoin.house.api.service.CommentService;
import com.okcoin.house.common.support.enums.CommonConstants;
import com.okcoin.house.common.support.model.Pager;
import com.okcoin.house.common.support.model.Pagination;
import com.okcoin.house.dto.BlogDto;
import com.okcoin.house.dto.CommentDto;
import com.okcoin.house.dto.HouseDto;
import com.okcoin.house.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: liupeng
 * @date: 2019/5/11 19:09
 * @description(功能描述):
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RecommendService recommendService;

    @GetMapping(value = "/list")
    public String list(@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                       @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       Blog query, ModelMap modelMap) {
        Pager<BlogDto> ps = blogService.queryBlog(query, pageNum, pageSize);
        List<HouseDto> hotHouse = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        Pagination pagination = new Pagination(pageSize, pageNum, ps.getTotal());
        modelMap.put("recomHouses", hotHouse);
        modelMap.put("ps", ps);
        modelMap.put("pager", pagination);
        return "/blog/listing";
    }

    @GetMapping(value = "/detail")
    public String blogDetail(@RequestParam("id") Long id, ModelMap modelMap) {
        BlogDto blog = blogService.queryOneBlog(id);
        List<CommentDto> blogComments = commentService.getBlogComments(id, 8);
        List<HouseDto> hotHouse = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", hotHouse);
        modelMap.put("blog", blog);
        modelMap.put("commentList", blogComments);
        return "/blog/detail";
    }
}