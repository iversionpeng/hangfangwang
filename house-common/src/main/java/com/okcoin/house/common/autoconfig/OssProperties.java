package com.okcoin.house.common.autoconfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: liupeng
 * @date: 2019/5/2 22:41
 * @description(功能描述):
 */
@Configuration
public class OssProperties {
    /**
     * 阿里云API的密钥Access Key ID
     */
    public static String accessKeyId;

    /**
     * 阿里云API的密钥Access Key Secret
     */
    public static String accessKeySecret;
    /**
     * 阿里云API的bucket名称
     */
    public static String bucketName;

    /**
     * 阿里云API的文件夹名称
     */
    public static String AVATAR_FOLDER = "public/";
    /**
     * 阿里云API的外网域名
     */
    public static String ENDPOINT = "oss-cn-beijing.aliyuncs.com";

    @Value("${spring.oss.accessKeyId}")
    public  void setAccessKeyId(String accessKeyId) {
        OssProperties.accessKeyId = accessKeyId;
    }
    @Value("${spring.oss.accessKeySecret}")
    public  void setAccessKeySecret(String accessKeySecret) {
        OssProperties.accessKeySecret = accessKeySecret;
    }
    @Value("${spring.oss.bucketName}")
    public  void setBucketName(String bucketName) {
        OssProperties.bucketName = bucketName;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }
}
