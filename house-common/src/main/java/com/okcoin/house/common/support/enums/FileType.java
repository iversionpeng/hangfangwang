package com.okcoin.house.common.support.enums;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public enum FileType {
    AVATAR_IMG_FILE(0, "/img/avatar/" + Instant.now().getEpochSecond() + "/"),
    HOUSE_IMG_FILE(1, "/img/house/" + Instant.now().getEpochSecond() + "/"),;

    private Integer type;
    private String typeName;

    FileType(Integer type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public static FileType valueOf(int type) {
        return Arrays.stream(FileType.values()).filter(x -> x.type == type).findFirst().orElseGet(null);
    }

    public Integer getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }
}
