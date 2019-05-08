package com.okcoin.house.filter;

import com.google.common.collect.Lists;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;

/**
 * @Auther: liupeng
 * @Date: 2019/4/3 16:24
 * @Description(功能描述):
 */
@Configuration
public class FilferRegisterBean {
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new LogFilter());
        ArrayList<String> list = Lists.newArrayList();
        list.add("*");
        filter.setUrlPatterns(list);
        return filter;
    }
}
