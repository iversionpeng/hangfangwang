package com.okcoin.house.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * @Auther: liupeng
 * @Date: 2019/4/5 23:25
 * @Description(功能描述):
 */
@Configuration
public class I18nConfig extends WebMvcConfigurationSupport {
    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private NeedLoginInterceptor needLoginInterceptor;

    /**
     * session区域解析器
     * @return
     */
//    @Bean
//    public LocaleResolver localeResolver() {
//        SessionLocaleResolver resolver = new SessionLocaleResolver();
//        //这里通过设置China.US可以进行中英文转化
//        resolver.setDefaultLocale(Locale.US);
//
//        return resolver;
//    }


    /**
     * cookie区域解析器
     *
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver slr = new CookieLocaleResolver();
        //设置默认区域,
//        slr.setDefaultLocale(Locale.getDefault());
//        slr.setCookieMaxAge(3600);
        return slr;
    }

//    @Bean
//    public LocaleResolver localeResolver() {
//        FixedLocaleResolver slr = new FixedLocaleResolver ();
//        //设置默认区域,
//        slr.setDefaultLocale(Locale.US);
//        return slr;
//    }


    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
//         设置请求地址的参数,默认为：locale
        lci.setParamName(LocaleChangeInterceptor.DEFAULT_PARAM_NAME);
        return lci;
    }

    /**
     * excludePathPatterns 对定义url不进行拦截
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**", "/templates/**");
        registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns("/static/**", "/templates/error/**");
        registry.addInterceptor(needLoginInterceptor).addPathPatterns("/accounts/changePassword").addPathPatterns("/house/toAdd")
                .addPathPatterns("/accounts/profile").addPathPatterns("/accounts/profileSubmit")
                .addPathPatterns("/house/bookmarked").addPathPatterns("/house/del")
                .addPathPatterns("/house/ownlist").addPathPatterns("/house/add")
                .addPathPatterns("/house/toAdd").addPathPatterns("/agency/agentMsg")
                .addPathPatterns("/comment/leaveComment").addPathPatterns("/comment/leaveBlogComment");
        super.addInterceptors(registry);
    }

    /**
     * 配置静态资源映射(url->classpath 找到映射静态资源路径)
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**", "/templates/**").addResourceLocations("classpath:/static/", "classpath:/templates/");
    }
}