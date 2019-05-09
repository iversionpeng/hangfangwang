package com.okcoin.house.common.autoconfig;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.comm.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: liupeng
 * @date: 2019/5/2 20:34
 * @description(功能描述): 配置oss客户端连接信息
 */
@ConditionalOnClass(OSSClient.class)
@ConditionalOnBean(OssProperties.class)
@Configuration
public class FcOssClient {
    @Autowired
    private OssProperties ossProperties;

    @Bean
    @ConditionalOnMissingBean(OSSClient.class)
    public OSSClient ossClient() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ossProperties.getENDPOINT();
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
//        String accessKeyId = ossProperties.getAccessKeyId();
        String accessKeyId = ossProperties.getAccessKeyId();
        String accessKeySecret = ossProperties.getAccessKeySecret();
//        String accessKeySecret = ossProperties.getAccessKeySecret();

// 创建ClientConfiguration。ClientConfiguration是OSSClient的配置类，可配置代理、连接超时、最大连接数等参数。
        ClientConfiguration conf = new ClientConfiguration();

// 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
        conf.setMaxConnections(200);
// 设置Socket层传输数据的超时时间，默认为50000毫秒。
        conf.setSocketTimeout(10000);
// 设置建立连接的超时时间，默认为50000毫秒。
        conf.setConnectionTimeout(10000);
// 设置从连接池中获取连接的超时时间（单位：毫秒），默认不超时。
        conf.setConnectionRequestTimeout(1000);
// 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
        conf.setIdleConnectionTime(10000);
// 设置失败请求重试次数，默认为3次。
        conf.setMaxErrorRetry(5);
// 设置是否支持将自定义域名作为Endpoint，默认支持。
        conf.setSupportCname(true);
// 设置是否开启二级域名的访问方式，默认不开启。
//        conf.setSLDEnabled(true);
// 设置连接OSS所使用的协议（HTTP/HTTPS），默认为HTTP。
        conf.setProtocol(Protocol.HTTP);
// 设置用户代理，指HTTP的User-Agent头，默认为aliyun-sdk-java。
        conf.setUserAgent("aliyun-sdk-java");
// 设置代理服务器端口。
//        conf.setProxyHost("<yourProxyHost>");
// 设置代理服务器验证的用户名。
//        conf.setProxyUsername("<yourProxyUserName>");
// 设置代理服务器验证的密码。
//        conf.setProxyPassword("<yourProxyPassword>");

// 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);

// 关闭OSSClient。
        return ossClient;
    }
}
