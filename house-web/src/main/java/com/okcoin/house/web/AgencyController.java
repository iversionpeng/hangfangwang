package com.okcoin.house.web;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.okcoin.house.api.domain.Agency;
import com.okcoin.house.api.service.AgencyService;
import com.okcoin.house.api.service.HouseService;
import com.okcoin.house.api.service.UserService;
import com.okcoin.house.common.support.enums.BizErrorCodeEnum;
import com.okcoin.house.common.support.enums.NoticeType;
import com.okcoin.house.common.support.model.Pager;
import com.okcoin.house.common.support.model.Pagination;
import com.okcoin.house.common.support.model.SecurityUser;
import com.okcoin.house.common.support.model.UserContext;
import com.okcoin.house.dto.HouseDto;
import com.okcoin.house.dto.UserDto;
import com.okcoin.house.service.NoticService;
import com.okcoin.house.service.RecommendService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liupeng
 * @date: 2019/5/10 23:50
 * @description(功能描述):
 */
@Controller
@RequestMapping("/agency")
public class AgencyController {
    @Autowired
    private AgencyService agencyService;

//    @Autowired
//    private RecommendService recommendService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private UserService userService;

    @Autowired
    private NoticService noticService;

    @Autowired
    private RecommendService recommendService;

    @GetMapping("/create")
    public String agencyCreate() {
        SecurityUser userHolder = UserContext.getUserHolder();
        if (!Objects.equal(userHolder.getEmail(), "iversionpeng@163.com")) {
            HashMap<String, String> result = Maps.newHashMap();
            result.put("errorMsg", BizErrorCodeEnum.NO_PERMISSION.getMessage());
            return "redirect:/accounts/signin?" + asUrlParams(result);
        }
        return "/user/agency/create";
    }

    @PostMapping("/submit")
    public String agencySubmit(Agency agency) {
        SecurityUser user = UserContext.getUserHolder();
        HashMap<String, String> result = Maps.newHashMap();
        if (!Objects.equal(user.getEmail(), "iversionpeng@163.com")) {//只有超级管理员可以添加,拟定spring_boot@163.com为超管
            result.put("errorMsg", BizErrorCodeEnum.NO_PERMISSION.getMessage());
            return "redirect:/accounts/signin?" + asUrlParams(result);
        }
        agencyService.add(agency);
        result.put("successMsg", "success");
        return "redirect:/index?" + asUrlParams(result);
    }


    @GetMapping("/agentList")
    public String agentList(@RequestParam(value = "pageSize", required = false, defaultValue = "6") Integer pageSize,
                            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            ModelMap modelMap) {
        Pager<UserDto> re = userService.getAllAgent(pageNum, pageSize);
        Pagination pagination = new Pagination(pageSize, pageNum, re.getTotal());
        List<HouseDto> hotHouse = recommendService.getHotHouse(3);
        modelMap.put("recomHouses", hotHouse);
        modelMap.put("ps", re);
        modelMap.put("pager", pagination);
        return "/user/agent/agentList";
    }

    @GetMapping("/agentDetail")
    public String agentDetail(@Param("id") Long id, ModelMap modelMap) {
        UserDto agentDeail = agencyService.getAgentDeail(id);
        HouseDto houseDto = HouseDto.builder().userId(id).Bookmarked(false).build();
        Pager<HouseDto> houseDtoPager = houseService.queryHouse(houseDto, 1, 12);
        if (houseDtoPager != null) {
            modelMap.put("bindHouses", houseDtoPager.getResult());
        }
        List<HouseDto> lateHouse = houseService.getLateHouse();
        modelMap.put("recomHouses", lateHouse);
        modelMap.put("agent", agentDeail);
        modelMap.put("agencyName", agentDeail.getAgencyName());
        return "/user/agent/agentDetail";
    }

    @GetMapping("/list")
    public String agencyList(ModelMap modelMap) {
        List<Agency> agencies = agencyService.getAllAgency();
        List<HouseDto> lateHouse = houseService.getLateHouse();
        modelMap.put("recomHouses", lateHouse);
        modelMap.put("agencyList", agencies);
        return "/user/agency/agencyList";
    }

    @GetMapping("/agencyDetail")
    public String agencyDetail(@Param("id") Integer id, ModelMap modelMap) {
        Agency agency = agencyService.getAgency(id);
        List<HouseDto> lateHouse = houseService.getLateHouse();
        modelMap.put("recomHouses", lateHouse);
        modelMap.put("agency", agency);
        return "/user/agency/agencyDetail";
    }

    @PostMapping("/agentMsg")
    public String agentMsg(@RequestParam(value = "id") Long id,
                           @RequestParam("msg") String msg,
                           @RequestParam("name") String name,
                           @RequestParam("email") String email) throws IOException, MessagingException {
        UserDto agentDeail = agencyService.getAgentDeail(id);
        Map<String, String> param = Maps.newHashMap();
        Map<String, String> result = Maps.newHashMap();
        param.put("email", email);
        param.put("msg", msg);
        result.put("successMsg", "success");
        noticService.sendConsultNotice(NoticeType.CONSULT_NOTICE.getMessage(), name, agentDeail.getEmail(), param);
        return "redirect:/agency/agentDetail?id=" + id + "&" + asUrlParams(result);
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
