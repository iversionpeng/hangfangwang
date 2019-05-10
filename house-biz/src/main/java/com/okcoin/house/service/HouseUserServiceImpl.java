package com.okcoin.house.service;

import com.okcoin.house.api.domain.HouseUser;
import com.okcoin.house.api.service.HouseUserService;
import com.okcoin.house.dao.main.HouseUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: liupeng
 * @date: 2019/5/10 14:12
 * @description(功能描述):
 */
@Service
public class HouseUserServiceImpl implements HouseUserService {
    @Autowired
    private HouseUserMapper houseUserMapper;

    @Override
    public HouseUser selectHouseUser(Long userId, Long houseDtoId, Integer type) {
        return houseUserMapper.selectHouseUser(userId, houseDtoId, type);
    }

    @Override
    public HouseUser getHouseUser(Long id) {
        return houseUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public void insertHouseUser(HouseUser houseUser) {
        houseUserMapper.insert(houseUser);
    }
}
