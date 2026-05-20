package com.alipay.usercenter.common.service.facade.enums;

/**
 * @author adam
 * @date 26/4/2026 11:09 AM
 */
public enum UserStatus {

    ACTIVE("ACTIVE", "user account status is active"),
    INACTIVE("INACTIVE", "user account status is inactive")
    ;
    private String code;
    private String desc;

    UserStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

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