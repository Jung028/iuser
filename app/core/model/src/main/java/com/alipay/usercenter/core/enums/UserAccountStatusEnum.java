package com.alipay.usercenter.core.enums;

public enum UserAccountStatusEnum {
    ACTIVE("ACTIVE", "user account is active"),
    CLOSED("CLOSED", "user account is closed")
    ;

    String code;
    String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    UserAccountStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
