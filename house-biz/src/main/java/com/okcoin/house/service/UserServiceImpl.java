package com.okcoin.house.service;

import com.okcoin.house.api.domain.User;
import com.okcoin.house.api.service.UserService;
import com.okcoin.house.common.autoconfig.OssProperties;
import com.okcoin.house.common.support.enums.BizErrorCodeEnum;
import com.okcoin.house.common.support.enums.FileType;
import com.okcoin.house.common.support.enums.NoticeType;
import com.okcoin.house.common.util.Md5Util;
import com.okcoin.house.dao.main.UserMapper;
import com.okcoin.house.dto.UserDto;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Auther: liupeng
 * @Date: 2019/4/3 22:03
 * @Description(功能描述):
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private NoticService noticService;

    @Override
    public List<String> userCheck(UserDto userDto, BindingResult validationResult) {

        List<String> errorMsg;

        List<ObjectError> allErrors = validationResult.getAllErrors();
        errorMsg = allErrors.stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());

        if (!userDto.getPasswd().equals(userDto.getConfirmPasswd())) {
            errorMsg.add(BizErrorCodeEnum.PWD_NOT_SAME.getMessage());
        }
        if (!CollectionUtils.isEmpty(errorMsg)) {
            return errorMsg;
        }
        Boolean exist = noticService.emailIsExist(userDto.getEmail());
        if (exist) {
            errorMsg.add(BizErrorCodeEnum.EMAIL_CACHE_EXIST.getMessage());
            return errorMsg;
        }
        User user = getUserByEmail(userDto.getEmail());
        if (Objects.nonNull(user)) {
            errorMsg.add(BizErrorCodeEnum.EMAIL_IS_EXIST.getMessage());
        }
        return errorMsg;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean insertUser(UserDto user) throws IOException, MessagingException {
        String md5Pwd = Md5Util.md5Password(user.getPasswd());
        MultipartFile avatarFile = user.getAvatarFile();
        String md5Key = fileService.uploadObject2OSS(avatarFile, user.getEmail() + "/" + FileType.IMG_FILE.getTypeName());
//        String url = fileService.getUrl(md5Key);
        String picUrl = "https://" + OssProperties.bucketName + "." + OssProperties.ENDPOINT +
                "/" + OssProperties.AVATAR_FOLDER + user.getEmail() + "/" + FileType.IMG_FILE.getTypeName() + avatarFile.getOriginalFilename();

//        List<String> filePath = fileService.getFilePath(Lists.newArrayList(avatarFile));
//        String avator = CollectionUtils.isEmpty(filePath) ? "" : filePath.get(0);
        userMapper.insertSelective(User.builder()
                .passwd(md5Pwd)
                .name(user.getName())
                .enable(false)
                .email(user.getEmail())
                .agencyId(Objects.isNull(user.getAgencyId()) ? 0 : user.getAgencyId())
                .aboutme(Strings.isBlank(user.getAboutme()) ? "" : user.getAboutme())
                .avatar(picUrl)
                .createTime(new Date())
                .phone(user.getPhone())
                .type(user.getType())
                .build());
        noticService.sendMsg(NoticeType.REGISTER_NOTICE.getMessage(), user.getName(), user.getEmail(), null);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public User verify(String key) {
        String email = noticService.getEmailByCache(key);
        User userByEmail = getUserByEmail(email);
        if (Objects.nonNull(userByEmail)) {
            updateUserStatus(email);
            noticService.removeEmailCache(email);
            return userByEmail;
        }
        return userByEmail;
    }

    @Override
    public void updateUserStatus(String email) {
        User user = userMapper.selectByEmail(email);
        if (Objects.nonNull(user)) {
            userMapper.updateUserStatusByEmail(email);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    @Override
    public void deleteByEmail(String email) {
        userMapper.delete(email);
    }

    @Override
    public User checkLogin(String username, String password) {
        User user = User.builder().
                email(username).
                passwd(Md5Util.md5Password(password)).
                enable(true).
                build();
        List<User> select = userMapper.select(user);
        return select.isEmpty() ? null : select.get(0);
    }


}
