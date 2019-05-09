package com.okcoin.house.config;

import com.google.common.base.Joiner;
import com.okcoin.house.common.support.enums.UserCommonConstants;
import com.okcoin.house.common.support.model.SecurityUser;
import com.okcoin.house.common.support.model.UserContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

/**
 * @author: liupeng
 * @date: 2019/5/9 11:11
 * @description(功能描述): 鉴权拦截
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String[]> map = request.getParameterMap();
        map.forEach((k, v) -> {
            if ("errorMsg".equals(k) || "successMsg".equals(k) || "target".equals(k)) {
                request.setAttribute(k, Joiner.on(",").join(v));
            }
        });
        HttpSession session = request.getSession(true);
        SecurityUser user = (SecurityUser) session.getAttribute(UserCommonConstants.USER_ATTRIBUTE);
        if (user != null) {
            UserContext.setUserHolder(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clearUserHolder();
    }
}
