package com.okcoin.house.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.okcoin.house.api.domain.User;
import com.okcoin.house.api.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: liupeng
 * @date: 2019/4/24 18:31
 * @description(功能描述):
 */
@Service
public class NoticService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoticService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;


    @Value("${spring.mail.username}")
    private String from;


    @Value("${domain.name}")
    private String domainName;

    private final Cache<String, String> registerCache =
            CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(15, TimeUnit.MINUTES)
                    .removalListener(new RemovalListener<String, String>() {
                        @Override
                        public void onRemoval(RemovalNotification<String, String> notification) {
                            String email = notification.getValue();
                            User targetUser = userService.getUserByEmail(email);
                            if (Objects.nonNull(targetUser)) {
                                userService.deleteByEmail(email);
                            }

                        }
                    }).build();

    @Async
    public void sendRegisterNotice(String subject, String name, String email, Map<String, String> params) throws MessagingException, IOException {
        String randomKey = RandomStringUtils.randomAlphabetic(10);
        registerCache.put(randomKey, email);
        String url = "http://" + domainName + "/accounts/verify?key=" + randomKey;
        sendMail(subject, email, buildContent(url, name));
    }

    @Async
    public void sendConsultNotice(String subject, String name, String email, Map<String, String> params) throws MessagingException, IOException {
        sendMail(subject, email, buildContent(params, name));
    }

    public Boolean emailIsExist(String email) {
        registerCache.cleanUp();
        return registerCache.asMap().containsValue(email);
    }

    public void removeEmailCache(String email) {
        registerCache.invalidate(email);
    }

    public String getEmailByCache(String key) {
        return registerCache.getIfPresent(key);
    }

    private void sendMail(String subject, String email, String text) throws MessagingException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setTo(email);
        helper.setText(text, true);
        String alarmIconName = "static/favicon.ico";
        ClassPathResource img = new ClassPathResource(alarmIconName);
        if (java.util.Objects.nonNull(img)) {
            helper.addInline("icon-alarm", img);
        }
        mailSender.send(mimeMessage);
    }

    private String buildContent(String url, String name) throws IOException {

        //加载邮件html模板
        String fileName = "static/pod-scale-alarm.html";
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = fileReader.readLine()) != null) {
            buffer.append(line);
        }
        inputStream.close();
        fileReader.close();
        String contentText = "欢迎注册,请点击下方链接完成注册,有效期: 15min.<br>welcome to register,Please click the link below to complete registration.<br>" + url;
        //邮件表格header
        //绿色
        String emailHeadColor = "#fa9c10";
        String date = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
        //填充html模板中的五个参数
        String htmlText = MessageFormat.format(buffer.toString(), emailHeadColor, name, contentText, date);

        //改变表格样式
        htmlText = htmlText.replaceAll("<td>", "<td style=\"padding:6px 10px; line-height: 150%;\">");
        htmlText = htmlText.replaceAll("<tr>", "<tr style=\"border-bottom: 1px solid #eee; color:#666;\">");
        return htmlText;
    }

    private String buildContent(Map<String, String> params, String name) throws IOException {

        //加载邮件html模板
        String fileName = "static/consult-email-template.html";
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = fileReader.readLine()) != null) {
            buffer.append(line);
        }
        inputStream.close();
        fileReader.close();
        String contentText = params.get("msg");
        String contentTextVar = params.get("email");
        //邮件表格header
        //绿色
        String emailHeadColor = "#fa9c10";
        String date = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
        //填充html模板中的参数
        String htmlText = MessageFormat.format(buffer.toString(), emailHeadColor, name, contentText, contentTextVar, date);

        //改变表格样式
        htmlText = htmlText.replaceAll("<td>", "<td style=\"padding:6px 10px; line-height: 150%;\">");
        htmlText = htmlText.replaceAll("<tr>", "<tr style=\"border-bottom: 1px solid #eee; color:#666;\">");
        return htmlText;
    }

}
