package com.okcoin.house.common.autoconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author: liupeng
 * @date: 2019/4/24 18:59
 * @description(功能描述): email自动配置
 */
@Configuration
@ConditionalOnClass(JavaMailSender.class)
public class FcMailAutoConfiguration {

    @Autowired
    private FcMailProperties fcMailProperties;

    @Bean
    @ConditionalOnMissingBean(JavaMailSender.class)
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender;
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(fcMailProperties.getHost());
        mailSender.setUsername(fcMailProperties.getUsername());
        mailSender.setPassword(fcMailProperties.getPassword());
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", fcMailProperties.getProperties().get("mail.smtp.auth"));
        properties.setProperty("mail.smtp.starttls.enable", fcMailProperties.getProperties().get("mail.smtp.starttls.enable"));
        properties.setProperty("mail.smtp.starttls.required", fcMailProperties.getProperties().get("mail.smtp.starttls.required"));
        //使用gmail发送邮件是必须设置如下参数的 主要是port不一样
//        if (fcMailProperties.getHost().indexOf("smtp.gmail.com") >= 0) {
//            properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//            properties.setProperty("mail.smtp.socketFactory.fallback", "false");
//            properties.setProperty("mail.smtp.port", "465");
//            properties.setProperty("mail.smtp.socketFactory.port", "465");
//        }
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }
}
