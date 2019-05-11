package com.okcoin.house.api.service;

import com.okcoin.house.api.domain.User;
import com.okcoin.house.common.support.model.Pager;
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

    User verify(String key);

    void updateUserStatus(String email);

    User getUserByEmail(String email);

    void deleteByEmail(String email);

    User checkLogin(String username, String password);

    void updateUserByEmail(User updateUser);

    User selectAgencyUserByUserId(Long userId, boolean type);

    Pager<UserDto> getAllAgent(Integer pageNum, Integer pageSize);

    User getUserByUserId(Long userId);
}
