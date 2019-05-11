package com.okcoin.house.api.service;

import com.okcoin.house.api.domain.Agency;
import com.okcoin.house.common.support.model.Pager;
import com.okcoin.house.dto.UserDto;

import java.util.List;

public interface AgencyService {
    Pager<Agency> getList();

    UserDto getAgentDeail(Long userId);

    List<Agency> getAllAgency();

    Agency getAgency(Integer id);

    void add(Agency agency);
}
