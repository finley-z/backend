package com.finley.enums;

/**
 * Created by 郑远锋 on 2017/2/28.
 */
public enum RoleStatusEnum {

    ENABLED(1,"正常"),     //正常
    UNABLED(2,"锁定");     //锁定

    private int status;
    private String desc;

    RoleStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDesc(int status) {
        for (RoleStatusEnum c : RoleStatusEnum.values()) {
            if (c.getStatus() == status) {
                return c.desc;
            }
        }
        return "";
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

}
