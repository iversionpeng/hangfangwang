package com.okcoin.house.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okcoin.house.api.domain.Agency;
import com.okcoin.house.api.domain.User;
import com.okcoin.house.api.service.AgencyService;
import com.okcoin.house.api.service.UserService;
import com.okcoin.house.common.support.model.Pager;
import com.okcoin.house.dao.main.AgencyMapper;
import com.okcoin.house.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.management.resources.agent;

import java.util.List;
import java.util.Objects;

/**
 * @author: liupeng
 * @date: 2019/4/17 18:26
 * @description(功能描述):
 */
@Service
public class AgencyServiceImpl implements AgencyService {
    @Autowired
    private AgencyMapper agencyMapper;

    @Autowired
    private UserService userService;

    @Override
    public Pager<Agency> getList() {
        PageInfo<Agency> result = PageHelper.startPage(1, 10).doSelectPageInfo(() -> agencyMapper.select());
        List<Agency> list = result.getList();
        return new Pager<>(1, 10, result.getTotal(), list);
    }

    @Override
    public UserDto getAgentDeail(Long userId) {
        User user = userService.selectAgencyUserByUserId(userId, true);
        UserDto.UserDtoBuilder userDto = UserDto.builder()
                .id(userId)
                .aboutme(user.getAboutme())
                .agencyId(user.getAgencyId())
                .avatar(user.getAvatar())
                .createTime(user.getCreateTime())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .type(true);
        if (Objects.nonNull(user)) {
            //将经纪人关联的经纪机构也一并查询出来
            Agency agency = agencyMapper.selectByPrimaryKey(Long.valueOf(user.getAgencyId()));
            if (Objects.nonNull(agency)) {
                userDto.agencyName(agency.getName());
            }
            return userDto.build();
        }
        return null;
    }

    @Override
    public List<Agency> getAllAgency() {
        return agencyMapper.select();
    }

    @Override
    public Agency getAgency(Integer id) {
        return agencyMapper.selectByPrimaryKey(Long.valueOf(id));
    }

    @Override
    public void add(Agency agency) {
        agencyMapper.insertSelective(agency);
    }
}
