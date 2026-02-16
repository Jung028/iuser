package com.alipay.usercenter.core.enums;


public enum UserActionEnum {
    SEND_OTP("SEND_OTP", UserActionTypeEnum.SEND_OTP),
    LOGIN("LOGIN", UserActionTypeEnum.LOGIN),
    REGISTER("REGISTER", UserActionTypeEnum.REGISTER ),
    VERITY_OTP("VERIFY_OTP", UserActionTypeEnum.VERIFY_OTP),
    QUERY_USER_INFO("QUERY_USER_INFO", UserActionTypeEnum.QUERY_USER_INFO);

    private String code;

    private IdigitalriskActionTypeEnum actionTypeEnum;

    UserActionEnum(String disposalRiskEvent, UserActionTypeEnum userActionTypeEnum) {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public IdigitalriskActionTypeEnum getActionTypeEnum() {
        return actionTypeEnum;
    }

    public void setActionTypeEnum(IdigitalriskActionTypeEnum actionTypeEnum) {
        this.actionTypeEnum = actionTypeEnum;
    }
}
