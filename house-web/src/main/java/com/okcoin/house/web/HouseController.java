package com.okcoin.house.web;

import com.okcoin.house.api.domain.Community;
import com.okcoin.house.api.service.CommunityService;
import com.okcoin.house.common.support.model.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: liupeng
 * @date: 2019/4/19 14:02
 * @description(功能描述): 房产相关controller
 */
@Controller
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/toAdd")
    public String toHouseAdd(HttpServletRequest request) {
        List<Community> es = communityService.provinceList();
        request.setAttribute("provinceList", es);
        return "/house/add";
    }


    @GetMapping("/provinceType")
    @ResponseBody
    public ResponseResult getTownByProvinceCode(Integer code) {
        List<String> cityNameList = communityService.getTownByProvinceCode(code);
        return ResponseResult.success(cityNameList);
    }


    @PostMapping("/add")
    public String addHouse() {

        return "";
    }
}
