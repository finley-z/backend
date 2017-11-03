package com.finley.enums;

/**
 * Created by 郑远锋 on 2017/2/28.
 */
public enum AuthStatusEnum {

    ENABLED(1,"正常"),     //正常
    UNABLED(2,"锁定");     //锁定

    private int status;
    private String desc;

    AuthStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDesc(int status) {
        for (AuthStatusEnum c : AuthStatusEnum.values()) {
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
