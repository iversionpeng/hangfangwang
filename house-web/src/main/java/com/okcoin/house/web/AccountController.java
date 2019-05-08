package com.okcoin.house.web;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.okcoin.house.api.domain.Agency;
import com.okcoin.house.api.domain.User;
import com.okcoin.house.api.service.AgencyService;
import com.okcoin.house.api.service.UserService;
import com.okcoin.house.common.support.enums.BizErrorCodeEnum;
import com.okcoin.house.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public String verify(HttpServletRequest request, @RequestParam("key") String key) {
        HashMap<String, String> map = Maps.newHashMap();
        User user = userService.verify(key);
        if (Objects.nonNull(user)) {
            map.put("successMsg", "Account activated</br>");
            HttpSession session = request.getSession(true);
            user.setPasswd("");
            session.setAttribute("loginUser", user);
            return "redirect:/index?" + asUrlParams(map);
        }
        map.put("errorMsg", BizErrorCodeEnum.KEY_INVALID.getMessage());
        return "redirect:/accounts/register" + "?" + asUrlParams(map);
    }

    @GetMapping("/signin")
    public String toSignin(String username, String errorMsg, String target, HttpServletRequest request) {
        request.setAttribute("target", target);
        request.setAttribute("username", username);
        request.setAttribute("errorMsg", errorMsg);
        return "user/accounts/signin";
    }

    @PostMapping("/signin")
    public String signin(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         String target, HttpServletRequest request) {
        HashMap<String, String> map = Maps.newHashMap();
        User user = userService.checkLogin(username, password);
        if (Objects.isNull(user)) {
            map.put("errorMsg", BizErrorCodeEnum.PWD_INCORRECT.getMessage());
            return "redirect:signin" + "?" + "target=" + target + "&username=" + username + "&" + asUrlParams(map);
        }
        HttpSession session = request.getSession(true);
        user.setPasswd("");
        session.setAttribute("loginUser", user);
        return Strings.isBlank(target) ? "redirect:/index" : "redirect:" + target;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();
        return "redirect:/index";
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
