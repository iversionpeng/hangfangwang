package com.okcoin.house.common.autoconfig;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: liupeng
 * @Date: 2019/4/8 23:06
 * @Description(功能描述):
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({FcHttpClientAutoConfiguration.class})
public @interface EnableHttpClient {
}
