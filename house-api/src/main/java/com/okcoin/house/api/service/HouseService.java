package com.okcoin.house.api.service;

import com.okcoin.house.api.domain.House;
import com.okcoin.house.api.domain.User;
import com.okcoin.house.common.support.enums.HouseUserType;
import com.okcoin.house.common.support.model.Pager;
import com.okcoin.house.dto.HouseDto;
import com.okcoin.house.dto.UserMsg;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface HouseService {

    List<HouseDto> getLateHouse();

    Pager<HouseDto> getHouseListByQuery(Integer pageSize, Integer pageNum, String name, Integer type, String sorting);

    void addHouse(HouseDto house, User userByEmail) throws IOException;

    House queryOneHouse(Long id);

    Pager<HouseDto> queryHouse(HouseDto house, Integer pageNum, Integer pageSize);

    void addUserMsg(UserMsg userMsg) throws IOException, MessagingException;

    void updateRating(Long id, Double rating);

    void bindUser2House(Long houseDtoId, Long userId, boolean collect);

    void unbindUser2House(Long id, Long userId, HouseUserType type);
}
