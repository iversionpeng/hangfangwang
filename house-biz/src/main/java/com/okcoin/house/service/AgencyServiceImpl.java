package com.okcoin.house.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.okcoin.house.api.domain.Agency;
import com.okcoin.house.api.service.AgencyService;
import com.okcoin.house.common.support.model.Pager;
import com.okcoin.house.dao.main.AgencyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: liupeng
 * @date: 2019/4/17 18:26
 * @description(功能描述):
 */
@Service
public class AgencyServiceImpl implements AgencyService {
    @Autowired
    private AgencyMapper agencyMapper;
    @Override
    public Pager<Agency> getList() {
        PageInfo<Agency> result = PageHelper.startPage(1, 10).doSelectPageInfo(() -> agencyMapper.select());
        List<Agency> list = result.getList();
        return new Pager<>(1, 10, result.getTotal(),list);
    }
}
