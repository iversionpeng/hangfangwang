package com.okcoin.house.common.autoconfig;

import org.springframework.beans.factory.annotation.Value;
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
    @Value("${spring.oss.accessKeyId}")
    private String accessKeyId;

    /**
     * 阿里云API的密钥Access Key Secret
     */
    @Value("${spring.oss.accessKeySecret}")
    private String accessKeySecret;
    /**
     * 阿里云API的bucket名称
     */
    @Value("${spring.oss.bucketName}")
    private String bucketName;

    /**
     * 阿里云API的文件夹名称
     */
    private String AVATAR_FOLDER = "public/";
    /**
     * 阿里云API的外网域名
     */
    private String ENDPOINT = "oss-cn-beijing-internal.aliyuncs.com";

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getAVATAR_FOLDER() {
        return AVATAR_FOLDER;
    }

    public void setAVATAR_FOLDER(String AVATAR_FOLDER) {
        this.AVATAR_FOLDER = AVATAR_FOLDER;
    }

    public String getENDPOINT() {
        return ENDPOINT;
    }

    public void setENDPOINT(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }
}
