package com.okcoin.house.dto;

/**
 * @author: liupeng
 * @date: 2019/4/17 23:21
 * @description(功能描述): 接受注册用户表单
 */

import com.okcoin.house.common.annotation.FCEmail;
import com.okcoin.house.common.annotation.Phone;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by peng liu 2019/04/03
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto implements Serializable {

    private Long id;
    /**
     * 姓名
     */
    @NotNull
    private String name;

    /**
     * 手机号
     */
    @Phone(required = true)
    private String phone;

    /**
     * 邮箱
     */
    @NotNull
    @FCEmail
    private String email;

    /**
     * 自我介绍
     */
    private String aboutme;

    /**
     * 经过md5加密的密码
     */
//    @Min(message = "密碼最低六位", value = 6)
    private String passwd;
    /**
     * 经过md5加密的密码
     */
    private String confirmPasswd;

    /**
     * 头像图片
     */
    private String avatar;

    /**
     * 1:普通用户 2:房产经纪人
     */
    @NotNull
    private Boolean type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 所属经济机构
     */
    private Integer agencyId;
    /**
     * 所属经济机构
     */
    private String agencyName;

    /**
     * 激活码
     */
    private Long key;

    /**
     * 上传文件
     */
    private MultipartFile avatarFile;

    private static final long serialVersionUID = 1L;
}