package com.okcoin.house.web;

import com.github.pagehelper.page.PageParams;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.okcoin.house.api.domain.*;
import com.okcoin.house.api.service.*;
import com.okcoin.house.common.support.enums.BizErrorCodeEnum;
import com.okcoin.house.common.support.enums.HouseStatus;
import com.okcoin.house.common.support.enums.HouseUserType;
import com.okcoin.house.common.support.model.*;
import com.okcoin.house.dto.CommentDto;
import com.okcoin.house.dto.HouseDto;
import com.okcoin.house.dto.UserMsg;
import com.okcoin.house.service.RecommendService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

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

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private RecommendService recommend;

    @Autowired
    private CommentService commentService;

    @GetMapping("/toAdd")
    public String toHouseAdd(HttpServletRequest request) {
        SecurityUser userHolder = UserContext.getUserHolder();
        if (!userHolder.getType()) {
            String target = request.getParameter("target");
            HashMap<String, String> result = Maps.newHashMap();
            result.put("errorMsg", BizErrorCodeEnum.NO_PERMISSION.getMessage());
            return "redirect:/index?" + asUrlParams(result);
        }
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
        //小区搜索

        //房产名模糊搜索
        //列表规则排序
        Pager<HouseDto> result = houseService.getHouseListByQuery(pageSize, pageNum, name, type, sorting);
        HouseDto build = HouseDto.builder()
                .name(name)
                .sort(StringUtils.isBlank(sorting) ? "time_desc" : sorting)
                .type(type)
                .build();
        Pagination page = new Pagination(pageSize, pageNum, result.getTotal());
        List<HouseDto> hotHouse = recommend.getHotHouse(3);
        modelMap.put("recomHouses", hotHouse);
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
        HouseDto build = HouseDto.builder()
                .name(name)
                .sort(StringUtils.isBlank(sorting) ? "time_desc" : sorting)
                .type(type)
                .build();
        Pagination page = new Pagination(10, 1, result.getTotal());
        List<HouseDto> hotHouse = recommend.getHotHouse(3);
        modelMap.put("recomHouses", hotHouse);
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

    /**
     * 查询房屋详情
     * 查询关联经纪人
     *
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public String houseDetail(@RequestParam("id") Long id, ModelMap modelMap) {
        House house = houseService.queryOneHouse(id);
        if (Objects.isNull(house)) {
            return "error/404";
        }
        recommend.increase(id);
        HouseUser houseUser = houseUserService.getHouseUser(id);
        List<String> imgs = Splitter.on(",").splitToList(house.getImage());
        List<String> floorImgs = Splitter.on(",").splitToList(house.getFloorPlan());
        List<String> featureList = Splitter.on(",").splitToList(house.getProperties());
        HouseDto houseDto = HouseDto.builder()
                .id(house.getId())
                .address(house.getAddress())
                .area(house.getArea())
                .baths(house.getBaths())
                .beds(house.getBeds())
                .firstImg(house.getImage().split(",")[0])
                .name(house.getName())
                .price(house.getPrice())
                .typeStr(house.getType() ? "直售" : "出租")
                .remarks(house.getRemarks())
                .imageList(imgs)
                .state(house.getState() ? 1 : 0)
                .featureList(featureList)
                .floorPlanList(floorImgs)
                .rating(house.getRating())
                .userId(houseUser.getUserId())
                .build();
        List<CommentDto> comments = commentService.getHouseComments(id, 8);
        if (houseUser.getUserId() != null && !houseUser.getUserId().equals(0)) {
            modelMap.put("agent", agencyService.getAgentDeail(houseUser.getUserId()));
        }
        List<HouseDto> hotHouse = recommend.getHotHouse(3);
        modelMap.put("recomHouses", hotHouse);
        modelMap.put("house", houseDto);
        modelMap.put("commentList", comments);
        return "/house/detail";
    }

    @PostMapping("/leaveMsg")
    public String houseMsg(UserMsg userMsg) throws IOException, MessagingException {
        Map<String, String> result = Maps.newHashMap();
        userMsg.setCreateTime(new Date());
        houseService.addUserMsg(userMsg);
        result.put("successMsg", "留言成功");
        return "redirect:/house/detail?id=" + userMsg.getHouseId() + "&" + asUrlParams(result);
    }

    //1.评分
    @ResponseBody
    @GetMapping("/rating")
    public ResponseResult houseRate(@RequestParam("rating") Double rating, @RequestParam("id") Long id) {
        SecurityUser userHolder = UserContext.getUserHolder();
        if (Objects.isNull(userHolder)) {
            return ResponseResult.failed(BizErrorCodeEnum.NOT_LOGIN);
        }
        houseService.updateRating(id, rating);
        return ResponseResult.success();
    }


    /**
     * 2.收藏
     */
    @ResponseBody
    @PostMapping("/bookmark")
    public ResponseResult bookmark(Long id) {
        SecurityUser userHolder = UserContext.getUserHolder();
        houseService.bindUser2House(id, userHolder.getUseId(), true);
        return ResponseResult.success();
    }

    /**
     * 3.删除收藏
     */
    @ResponseBody
    @PostMapping("/unbookmark")
    public ResponseResult unbookmark(@RequestParam("id") Long id) {
        SecurityUser userHolder = UserContext.getUserHolder();
        houseService.unbindUser2House(id, userHolder.getUseId(), HouseUserType.BOOKMARK);
        return ResponseResult.success();
    }

    @GetMapping(value = "/del")
    public String delsale(@RequestParam("id") Long id, @RequestParam("pageType") String pageType) {
        SecurityUser userHolder = UserContext.getUserHolder();
        houseService.unbindUser2House(id, userHolder.getUseId(), pageType.equals("own") ? HouseUserType.SALE : HouseUserType.BOOKMARK);
        return "redirect:/house/ownlist";
    }

    //4.收藏列表
    @GetMapping("/bookmarked")
    public String bookmarked(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                             ModelMap modelMap) {
        SecurityUser userHolder = UserContext.getUserHolder();
        HouseDto houseDto = HouseDto.builder().Bookmarked(true).userId(userHolder.getUseId()).build();
        Pager<HouseDto> houseDtoPager = houseService.queryHouse(houseDto, pageNum, pageSize);
        Pagination pagination = new Pagination(pageSize, pageNum, houseDtoPager.getTotal());
        modelMap.put("ps", houseDtoPager);
        modelMap.put("pageType", "book");
        modelMap.put("page", pagination);
        return "/house/ownlist";
    }

    private String asUrlParams(Map<String, String> map) {

        HashMap<String, String> newHashMap = Maps.newHashMap();
        map.forEach((k, v) -> {
            if (v != null) {
                try {
                    newHashMap.put(k, URLEncoder.encode(v, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                }
            }
        });
        return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(newHashMap);
    }
}
