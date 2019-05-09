package com.okcoin.house.config;


import com.okcoin.house.common.support.enums.BizErrorCodeEnum;
import com.okcoin.house.common.support.model.SecurityUser;
import com.okcoin.house.common.support.model.UserContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @Auther: liupeng
 * @Date: 2019/4/8 10:45
 * @Description(功能描述):
 */
@Component
public class NeedLoginInterceptor implements HandlerInterceptor {


    //方法执行前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SecurityUser userHolder = UserContext.getUserHolder();
        if (userHolder == null) {
            String msg = URLEncoder.encode(BizErrorCodeEnum.NOT_LOGIN.getMessage(), "utf-8");
            String target = URLEncoder.encode(request.getRequestURL().toString(), "utf-8");
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                response.sendRedirect("/accounts/signin?errorMsg=" + msg + "&target=" + target);
                return false;//修复bug,未登录要返回false
            } else {
                response.sendRedirect("/accounts/signin?errorMsg=" + msg);
                return false;//修复bug,未登录要返回false
            }
        }
        return true;
    }

    //方法执行后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    //页面渲染前
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
