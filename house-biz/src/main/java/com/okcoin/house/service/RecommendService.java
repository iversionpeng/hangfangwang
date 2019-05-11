package com.okcoin.house.service;

import com.github.pagehelper.page.PageParams;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.okcoin.house.api.domain.House;
import com.okcoin.house.api.service.HouseService;
import com.okcoin.house.common.support.model.Pager;
import com.okcoin.house.dto.HouseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: liupeng
 * @date: 2019/5/11 13:48
 * @description(功能描述): 热点房源
 */
@Service
public class RecommendService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private HouseService houseService;

    private static final String HOT_HOUSE_KEY = "hot_house";

    public void increase(Long id) {
        BoundZSetOperations<String, String> redis = redisTemplate.boundZSetOps(HOT_HOUSE_KEY);
        redis.incrementScore("" + id, 1.0);
//        redisZSet.removeRange(HOT_HOUSE_KEY, 0, -11);
    }

    public List<Long> getHotIds() {
        BoundZSetOperations<String, String> redis = redisTemplate.boundZSetOps(HOT_HOUSE_KEY);
        Set<String> range = redis.reverseRange(0, -1);
        return range.stream().map(Long::parseLong).collect(Collectors.toList());
    }

    public List<HouseDto> getHotHouse(Integer size) {
        List<Long> list = getHotIds();
        list = list.subList(0, Math.min(list.size(), size));
        if (list.isEmpty()) {
            return Lists.newArrayList();
        }
        HouseDto query = HouseDto.builder().ids(list).build();
        Pager<HouseDto> result = houseService.queryHouse(query, 1, size);
        final List<Long> order = list;
        Ordering<HouseDto> houseSort = Ordering.natural().onResultOf(hs ->
                order.indexOf(hs.getId()));
        return houseSort.sortedCopy(result.getResult());
    }

    public void remove(Long id) {
        BoundZSetOperations<String, String> redis = redisTemplate.boundZSetOps(HOT_HOUSE_KEY);
        redis.remove(""+id);
    }
}
