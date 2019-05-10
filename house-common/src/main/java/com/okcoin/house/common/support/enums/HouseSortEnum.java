package com.okcoin.house.common.support.enums;

import java.util.Arrays;

public enum HouseSortEnum {
    PRICE_DESC("price_desc", "price desc"),
    PRICE_ASC("price_asc", "price asc"),
    TIME_DESC("time_desc", "create_time desc"),;
    private String type;
    private String sort;

    HouseSortEnum(String type, String sort) {
        this.type = type;
        this.sort = sort;
    }

    public static HouseSortEnum getByType(String types) {
        return Arrays.asList(HouseSortEnum.values()).stream().filter(x -> x.type.equals(types)).findFirst().orElseGet(HouseSortEnum::getTimeDesc);
    }

    public static HouseSortEnum getTimeDesc() {
        return HouseSortEnum.TIME_DESC;
    }

    public String getType() {
        return type;
    }

    public String getSort() {
        return sort;
    }
}
