package com.okcoin.house.api.service;

import com.okcoin.house.api.domain.Agency;
import com.okcoin.house.common.support.model.Pager;

public interface AgencyService {
    Pager<Agency> getList();
}
