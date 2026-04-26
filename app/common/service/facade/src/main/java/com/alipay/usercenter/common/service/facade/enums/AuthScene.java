package com.alipay.usercenter.common.service.facade.enums;

/**
 * @author adam
 * @date 10/3/2026 2:48 PM
 */
public enum AuthScene {
    TRANSFER_CONFIRM("TRANSFER_CONFIRM", "transfer confirmation requires password pin"),
    VIEW_BALANCE("VIEW_BALANCE", "to view balance"),
    LOGIN("LOGIN", "user login password"),
    CHANGE_PASSWORD("CHANGE_PASSWORD", "change password"),
    WITHDRAW("WITHDRAW", "atm machine require enter password pin"),


    ;

    AuthScene(String code, String desc) {
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