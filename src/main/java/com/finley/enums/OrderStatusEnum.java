package com.finley.enums;

/**
 * Created by 郑远锋 on 2017/2/28.
 */
public enum OrderStatusEnum {

    UNPAY(0,"待付款"),     //待付款
    PAYED(1,"已付款"),        //已付款
    UNDELIVER (2,"待发货"),     //待发货
    DELIVERED(3,"已发货"),        //已发货
    SUCCESS(4,"交易完成"),        //交易完成
    CANCEL(5,"交易取消");        //交易取消

    private int status;
    private String desc;

    OrderStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static String getDesc(int status) {
        for (OrderStatusEnum c : OrderStatusEnum.values()) {
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
