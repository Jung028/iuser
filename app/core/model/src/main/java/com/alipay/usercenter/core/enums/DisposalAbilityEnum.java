package com.alipay.usercenter.core.enums;

public enum DisposalAbilityEnum {
    RISK_EVENT_UPDATE("RISK_EVENT_UPDATE", "update risk event")
    ;
    private String code;
    private String description;

    DisposalAbilityEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

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

    //get by code
    public static DisposalAbilityEnum getByCode(String code) {
        for (DisposalAbilityEnum disposalAbilityEnum : DisposalAbilityEnum.values()) {
            if (disposalAbilityEnum.getCode().equals(code)) {
                return disposalAbilityEnum;
            }
        }
        return null;

    }
}
