package com.okcoin.house.web;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.okcoin.house.api.domain.Agency;
import com.okcoin.house.api.service.AgencyService;
import com.okcoin.house.api.service.UserService;
import com.okcoin.house.common.support.enums.BizErrorCodeEnum;
import com.okcoin.house.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liupeng
 * @date: 2019/4/17 15:24
 * @description(功能描述): 账户相关controller
 */
@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private UserService userService;

    private ThreadLocal<List<Agency>> agencyList = new ThreadLocal();

    @GetMapping("/register")
    public String register(HttpServletRequest request) {
        String errorMsg = request.getParameter("errorMsg");
        List<Agency> agencies = getAgencies();
        request.setAttribute("agencyList", agencies);
        request.setAttribute("errorMsg", errorMsg);
        return "/user/accounts/register";
    }

    @PostMapping(value = "/register")
    public String register(@Validated UserDto user, BindingResult validationResult, HttpServletRequest request) throws IOException, MessagingException {
        List<String> errorMsg;
        HashMap<String, String> map = Maps.newHashMap();
        errorMsg = userService.userCheck(user, validationResult);
        if (!CollectionUtils.isEmpty(errorMsg)) {
            errorMsg.toArray();
            StringBuilder sb = new StringBuilder();
            errorMsg.forEach(x -> sb.append(x + "</br>"));
            map.put("errorMsg", sb.toString());
            return "redirect:/accounts/register" + "?" + asUrlParams(map);
        }
        userService.insertUser(user);
        request.setAttribute("email", user.getEmail());
        return "user/accounts/registerSubmit";
    }

    @GetMapping("/verify")
    public String verify(String key) {
        HashMap<String, String> map = Maps.newHashMap();
        if (userService.verify(key)) {
            map.put("successMsg", "Account activated</br>");
            return "redirect:/index?" + asUrlParams(map);
        }
        map.put("errorMsg", BizErrorCodeEnum.KEY_INVALID.getMessage());
        return "redirect:/accounts/register" + "?" + asUrlParams(map);
    }


    private List<Agency> getAgencies() {
        List<Agency> agencies = agencyList.get();
        if (CollectionUtils.isEmpty(agencies)) {
            agencyList.set(agencyService.getList().getResult());
            agencies = agencyList.get();
        }
        return agencies;
    }

    private static String asUrlParams(Map<String, String> map) {

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
