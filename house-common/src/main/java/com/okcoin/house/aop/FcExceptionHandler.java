package com.okcoin.house.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: liupeng
 * @Date: 2019/4/10 20:53
 * @Description(功能描述):
 */
@ControllerAdvice
public class FcExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(FcExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    public String error500(HttpServletRequest request, Exception e) {
        log.error(e.getMessage(), e);
        //url
        log.error("url={}", request.getRequestURL() + (request.getQueryString() == null ? "" : "?" + request.getQueryString()));
        //method
        log.error("method={}", request.getMethod());
        //ip
        log.error("ip={}", request.getRemoteAddr());
        //class_method
        return "error/500";
    }
}
