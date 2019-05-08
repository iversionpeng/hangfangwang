package com.okcoin.house.common.autoconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: liupeng
 * @Date: 2019/4/8 17:25
 * @Description(功能描述):
 */
@Data
@Configuration
@ConfigurationProperties("spring.httpclient")
public class FcHttpClientProperties {
    private String agent;
    private Integer connectTimeOut;
    private Integer socketTimeOut;
    private Integer maxConnTotal;
    private Integer maxConnPerRoute;
}
