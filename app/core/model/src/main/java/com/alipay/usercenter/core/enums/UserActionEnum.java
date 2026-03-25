package com.alipay.usercenter.core.enums;


public enum UserActionEnum {
    SEND_OTP("SEND_OTP", "send otp"),
    LOGIN("LOGIN", "login"),
    REGISTER("REGISTER", "register"),
    VERITY_OTP("VERIFY_OTP", "verify otp"),
    QUERY_USER_INFO("QUERY_USER_INFO", "query user info"),
    VERIFY_USER_AUTH("VERIFY_USER_AUTH", "verify user credentials"),
    VERIFY_VERIFIED_TOKEN("VERIFY_VERIFIED_TOKEN", "verify verified token result"),
    QUERY_CARD_DETAILS("QUERY_CARD_DETAILS", "query card details"),
    UPDATE_AUTO_RELOAD_CONFIG("UPDATE_AUTO_RELOAD_CONFIG", "update auto reload config"),
    INSERT_NEW_CARD("INSERT_NEW_CARD", "insert new card"),
    TOGGLE_AUTO_RELOAD("TOGGLE_AUTO_RELOAD", "toggle auto reload"),
    UPDATE_USER_INFO("UPDATE_USER_INFO", "update user info"),
    SAVE_CARD("SAVE_CARD", "save card"),

    QUERY_DEFAULT_CARD("QUERY_DEFAULT_CARD", "query default card"),


    ;

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
