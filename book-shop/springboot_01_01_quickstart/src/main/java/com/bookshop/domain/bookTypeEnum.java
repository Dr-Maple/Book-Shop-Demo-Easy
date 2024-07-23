package com.bookshop.domain;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum bookTypeEnum implements IEnum {
    文学类(0,"文学类"),
    童书(1,"童书"),
    教育类(2,"教育类"),
    考试类(3,"考试类"),
    人文社科类(4,"人文社科类"),
    经管综合类(5,"经管综合类"),
    生活类(6,"生活类"),
    艺术类(7,"艺术类"),
    科技类(8,"科技类"),
    其他(9,"其他");

    private int value;

    private String type;

    bookTypeEnum(int value, String type) {
        this.value = value;
        this.type = type;
    }

    public static int getValueByType(String type) {
        bookTypeEnum[] bookTypeEnums = values();
        for (bookTypeEnum bookTypeEnum1 : bookTypeEnums) {
            if (bookTypeEnum1.getType().equals(type)) {
                return bookTypeEnum1.getValue();
            }
        }
        return -1;
    }

    public String getType() {
        return this.type;
    }
    public Integer getValue(){
        return this.value;
    }
}
