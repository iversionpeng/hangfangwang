package com.okcoin.house.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Auther: liupeng
 * @Date: 2019/4/2 16:10
 * @Description(功能描述):
 */
public class LogFilter implements Filter {
    private Logger log = LoggerFactory.getLogger(LogFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("自定义监听器init...........");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("request url = {}", request.getLocalAddr());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
