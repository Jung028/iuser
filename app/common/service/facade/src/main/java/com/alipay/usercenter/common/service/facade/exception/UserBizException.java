package com.alipay.usercenter.common.service.facade.exception;

public class UserBizException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String errorMsg;

    public UserBizException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
