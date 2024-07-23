package com.bookshop.domain;

public enum bookState {
    ON_SALE(0,"在售"),
    NOT_ON_SALE(1, "已下架");

    private Integer code;
    private String desc;

    bookState(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }
    public String getDesc() {
        return this.desc;
    }
}
