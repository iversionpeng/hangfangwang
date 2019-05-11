package com.okcoin.house.common.support.enums;

public enum HouseUserType {
    BOOKMARK(1),
    SALE(0),;
    private Integer type;

    HouseUserType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
