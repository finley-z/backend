package com.finley.enums;

/**
 * Created by 郑远锋 on 2017/2/28.
 */
public enum NoticeTypeEnum {

    FOR_ALL(1,"面向所有用户"),     //正常
    FOR_AGENT(3,"面向代理商用户"),     //锁定
    FOR_MANAGER(2,"面向系统管理员用户");     //锁定
    private int type;
    private String desc;

    NoticeTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getDesc(int status) {
        for (NoticeTypeEnum c : NoticeTypeEnum.values()) {
            if (c.getType() == status) {
                return c.desc;
            }
        }
        return "";
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

}
