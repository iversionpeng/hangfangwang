package com.okcoin.house.api.service;

import com.okcoin.house.api.domain.Community;

import java.util.List;

public interface CommunityService {
    List<Community> provinceList();

    List<String> getTownByProvinceCode(Integer id);
}
