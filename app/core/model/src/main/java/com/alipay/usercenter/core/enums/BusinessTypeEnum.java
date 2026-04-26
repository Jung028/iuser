package com.alipay.usercenter.core.enums;

public enum BusinessTypeEnum {
    CHARGEBACK("CHARGEBACK"),
    ;
    private String code;

    public String getCode() {
        return code;
    }
    public String setCode(String code) {
        return this.code = code;
    }

    BusinessTypeEnum(String code) {
        this.code = code;
    }
}
