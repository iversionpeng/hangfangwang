package com.okcoin.house.service;

import com.okcoin.house.api.domain.Community;
import com.okcoin.house.api.service.CommunityService;
import com.okcoin.house.common.util.ProvinceEnum;
import com.okcoin.house.dao.main.CommunityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: liupeng
 * @date: 2019/4/19 14:18
 * @description(功能描述):
 */
@Service
public class CommunityServiceImpl implements CommunityService {

    @Override
    public List<Community> provinceList() {
        return Arrays.stream(ProvinceEnum.values())
                .map(x -> Community
                        .builder()
                        .cityCode(x.getProvinceCode().toString())
                        .cityName(x.getProvinceName())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public List<String> getTownByProvinceCode(Integer code) {
        List<String> result = null;
        ProvinceEnum provinceEnum = ProvinceEnum.getProvinceEnum(code);
        result = provinceEnum == null ? null : provinceEnum.getCityList();
        return result;
    }
}
