package com.okcoin.house.common.autoconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author: liupeng
 * @date: 2019/4/24 19:13
 * @description(功能描述): mail配置信息
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class FcMailProperties {
    private String username;
    private String host;
    private String password;
    private Map<String,String> properties;
}
