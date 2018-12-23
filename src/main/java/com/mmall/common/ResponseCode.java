package com.mmall.common;

/**
 * @Author: jarry
 * @Date: 12/15/2018 14:51
 */
public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    NEED_LOGIN(10, "NEED_LOGIN"),
    ILLEAGAL_ARGUMENT(2, "ILLEAGAL ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


}
