package com.okcoin.house.common.autoconfig;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: liupeng
 * @Date: 2019/4/8 16:44
 * @Description(功能描述):
 */
@Configuration
@ConditionalOnClass(HttpClient.class)
public class FcHttpClientAutoConfiguration {

    @Autowired
    private FcHttpClientProperties properties;

    @Bean
    @ConditionalOnMissingBean(HttpClient.class)
    public HttpClient httpClient() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(properties.getConnectTimeOut())
                .setSocketTimeout(properties.getSocketTimeOut())
                .build();
        HttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .setUserAgent(properties.getAgent())
                .setMaxConnTotal(properties.getMaxConnTotal())
                .setMaxConnPerRoute(properties.getMaxConnPerRoute())
                .setConnectionReuseStrategy(new NoConnectionReuseStrategy())
                .build();
        return client;
    }

}
