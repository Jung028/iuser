package com.alipay.usercenter.common.service.facade.result;


public class Result {
    public boolean success;
    public boolean resultCode;
    public boolean resultMsg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isResultCode() {
        return resultCode;
    }

    public void setResultCode(boolean resultCode) {
        this.resultCode = resultCode;
    }

    public boolean isResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(boolean resultMsg) {
        this.resultMsg = resultMsg;
    }
}
