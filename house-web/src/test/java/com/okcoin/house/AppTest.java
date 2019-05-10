package com.okcoin.house;

import com.okcoin.house.common.support.enums.BizErrorCodeEnum;
import com.okcoin.house.common.support.i18n.LocaleUtils;
import com.okcoin.house.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    @Autowired
    private LocaleUtils localeUtils;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private FileService fileService;

    @Test
    public void testI18n() {
        Locale aDefault = Locale.getDefault();
        String message = localeUtils.getMessage("error.code.biz.200");
        Object[] o = {};
        Locale locale = LocaleContextHolder.getLocale();
        String message1 = messageSource.getMessage("error.code.biz.200", o, locale);
        String message2 = BizErrorCodeEnum.PHONE_NUMBER_ERROR.getMessage();
        return;
    }

    @Test
    public void testLocale() {
        List<Locale> localesFromHeader = io.undertow.util.LocaleUtils.getLocalesFromHeader("zh-CN");
        Locale locale = localesFromHeader.get(0);
        return;
    }

}
