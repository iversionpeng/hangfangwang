package com.okcoin.house.common.support.enums;

public enum HouseUserType {
    BOOKMARK(0),
    SALE(1),;
    private Integer type;

    HouseUserType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
