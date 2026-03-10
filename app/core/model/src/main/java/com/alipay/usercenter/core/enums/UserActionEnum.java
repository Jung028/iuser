package com.alipay.usercenter.core.enums;


public enum UserActionEnum {
    SEND_OTP("SEND_OTP", "send otp"),
    LOGIN("LOGIN", "login"),
    REGISTER("REGISTER", "register"),
    VERITY_OTP("VERIFY_OTP", "verify otp"),
    QUERY_USER_INFO("QUERY_USER_INFO", "query user info"),
    VERIFY_USER_AUTH("VERIFY_USER_AUTH", "verify user credentials");

    UserActionEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;

    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
