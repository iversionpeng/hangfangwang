package com.okcoin.house.api.service;

import com.okcoin.house.api.domain.HouseUser;

public interface HouseUserService {
    HouseUser selectHouseUser(Long userId, Long houseDtoId, Integer integer);

    void insertHouseUser(HouseUser houseUser);

    HouseUser getHouseUser(Long id);

    void deleteHouseUser(Long id, Long userId, Integer type);
}
