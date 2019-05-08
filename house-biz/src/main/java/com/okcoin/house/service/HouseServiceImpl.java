package com.okcoin.house.service;

import com.okcoin.house.api.domain.House;
import com.okcoin.house.api.service.HouseService;
import com.okcoin.house.dao.main.HouseMapper;
import com.okcoin.house.dto.HouseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: liupeng
 * @date: 2019/4/19 11:04
 * @description(功能描述):
 */
@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Override
    public List<HouseDto> getLateHouse() {
        List<HouseDto> collect = houseMapper.getLateHouse().stream()
                .map(x -> HouseDto.builder()
                        .id(x.getId())
                        .address(x.getAddress())
                        .area(x.getArea())
                        .baths(x.getBaths())
                        .beds(x.getBeds())
                        .firstImg(x.getImage().split(";")[0])
                        .name(x.getName())
                        .price(x.getPrice())
                        .build())
                .collect(Collectors.toList());
        return collect;
    }
}
