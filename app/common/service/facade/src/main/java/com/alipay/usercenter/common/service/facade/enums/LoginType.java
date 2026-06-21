package com.alipay.usercenter.common.service.facade.enums;

/**
 * @author adam
 * @date 21/6/2026 5:28 PM
 */
public enum LoginType {
    USER("USER", "ipay login"),
    MERCHANT("MERCHANT", "imerchantmng login");

    LoginType(String code, String desc) {
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