package com.okcoin.house.api.service;

import com.okcoin.house.api.domain.User;
import com.okcoin.house.dto.UserDto;
import org.springframework.validation.BindingResult;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: liupeng
 * @Date: 2019/4/3 22:00
 * @Description(功能描述):
 */
public interface UserService {

    List<String> userCheck(UserDto userDto, BindingResult validationResult);

    boolean insertUser(UserDto user) throws IOException, MessagingException;

    boolean verify(String key);

    void updateUserStatus(String email);

    User getUserByEmail(String email);

    void deleteByEmail(String email);
}
