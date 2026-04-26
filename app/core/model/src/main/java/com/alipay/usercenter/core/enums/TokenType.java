package com.alipay.usercenter.core.enums;

public enum TokenType {
    USER("USER", "user");

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

    TokenType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
