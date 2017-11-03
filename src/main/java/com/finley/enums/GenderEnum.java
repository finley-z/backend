package com.finley.enums;

/**
 * Created by 郑远锋 on 2017/2/28.
 */
public enum  GenderEnum {

    MAN(1,"男"),     //男
    WOMAN(2,"女");   //女

    private int type;
    private String name;

    GenderEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getName(int index) {
        for (GenderEnum c : GenderEnum.values()) {
            if (c.getType() == index) {
                return c.name;
            }
        }
        return "";
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
