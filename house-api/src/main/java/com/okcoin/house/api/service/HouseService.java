package com.okcoin.house.api.service;

import com.okcoin.house.api.domain.House;
import com.okcoin.house.api.domain.User;
import com.okcoin.house.common.support.model.Pager;
import com.okcoin.house.dto.HouseDto;

import java.io.IOException;
import java.util.List;

public interface HouseService {

    List<HouseDto> getLateHouse();

    Pager<HouseDto> getHouseListByQuery(Integer pageSize, Integer pageNum, String name, Integer type,String sorting);

    void addHouse(HouseDto house, User userByEmail) throws IOException;

    House queryOneHouse(Long id);

    Pager<HouseDto> queryHouse(HouseDto house, Integer pageNum, Integer pageSize);
}
