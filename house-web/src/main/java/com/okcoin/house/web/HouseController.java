package com.okcoin.house.web;

import com.okcoin.house.api.domain.Community;
import com.okcoin.house.api.domain.House;
import com.okcoin.house.api.domain.User;
import com.okcoin.house.api.service.CommunityService;
import com.okcoin.house.api.service.HouseService;
import com.okcoin.house.api.service.HouseUserService;
import com.okcoin.house.api.service.UserService;
import com.okcoin.house.common.support.enums.HouseSortEnum;
import com.okcoin.house.common.support.enums.HouseStatus;
import com.okcoin.house.common.support.model.*;
import com.okcoin.house.dto.HouseDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author: liupeng
 * @date: 2019/4/19 14:02
 * @description(功能描述): 房产相关controller
 */
@Controller
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private HouseUserService houseUserService;

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

    /**
     * 1.实现分页
     * 2.支持小区搜索、类型搜索
     * 3.支持排序
     * 4.支持展示图片、价格、标题、地址等信息
     *
     * @return
     */
    @PostMapping("/list")
    public String houseList(@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "sort", required = false) String sorting,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "type", required = false) Integer type,
                            ModelMap modelMap) {
        Pager<HouseDto> result = houseService.getHouseListByQuery(pageSize, pageNum, name, type, sorting);
        List<HouseDto> lateHouse = houseService.getLateHouse();
        HouseDto build = HouseDto.builder()
                .name(name)
                .sort(StringUtils.isBlank(sorting) ? "time_desc" : sorting)
                .type(type)
                .build();
        Pagination page = new Pagination(pageSize, pageNum, result.getTotal());
        modelMap.put("recomHouses", lateHouse);
        modelMap.put("ps", result);
        modelMap.put("vo", build);
        modelMap.put("page", page);
        return "house/listing";
    }

    @GetMapping("/list")
    public String toHouseList(@RequestParam(value = "sort", required = false) String sorting,
                              @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "type", required = false) Integer type,
                              ModelMap modelMap) {
        Pager<HouseDto> result = houseService.getHouseListByQuery(10, 1, name, type, sorting);
        List<HouseDto> lateHouse = houseService.getLateHouse();
        HouseDto build = HouseDto.builder()
                .name(name)
                .sort(StringUtils.isBlank(sorting) ? "time_desc" : sorting)
                .type(type)
                .build();
        Pagination page = new Pagination(10, 1, result.getTotal());
        modelMap.put("recomHouses", lateHouse);
        modelMap.put("ps", result);
        modelMap.put("vo", build);
        modelMap.put("page", page);
        return "house/listing";
    }

    @PostMapping("/add")
    public String doAdd(HouseDto house) throws IOException {
        SecurityUser userHolder = UserContext.getUserHolder();
        User userByEmail = userService.getUserByEmail(userHolder.getEmail());
        house.setState(HouseStatus.HOUSE_STATE_UP);
        houseService.addHouse(house, userByEmail);
        return "redirect:/house/ownlist";
    }

    @GetMapping("/ownlist")
    public String ownlist(HouseDto house, ModelMap modelMap) {
        SecurityUser userHolder = UserContext.getUserHolder();
        User user = userService.getUserByEmail(userHolder.getEmail());
        house.setUserId(user.getId());
        house.setBookmarked(false);
        Pager<HouseDto> result = houseService.queryHouse(house, 1, 10);
        Pagination page = new Pagination(10, 1, result.getTotal());
        modelMap.put("ps", result);
        modelMap.put("pageType", "own");
        modelMap.put("page", page);
        return "/house/ownlist";
    }
//
//    /**
//     * 查询房屋详情
//     * 查询关联经纪人
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("house/detail")
//    public String houseDetail(Long id, ModelMap modelMap) {
//        House house = houseService.queryOneHouse(id);
//        HouseUser houseUser = houseService.getHouseUser(id);
//        recommendService.increase(id);
//        List<Comment> comments = commentService.getHouseComments(id, 8);
//        if (houseUser.getUserId() != null && !houseUser.getUserId().equals(0)) {
//            modelMap.put("agent", agencyService.getAgentDeail(houseUser.getUserId()));
//        }
//        List<House> rcHouses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
//        modelMap.put("recomHouses", rcHouses);
//        modelMap.put("house", house);
//        modelMap.put("commentList", comments);
//        return "/house/detail";
//    }
}
