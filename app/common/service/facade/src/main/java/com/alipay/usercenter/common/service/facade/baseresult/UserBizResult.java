package com.alipay.usercenter.common.service.facade.baseresult;

public class UserBizResult<T> extends UserBaseResult {
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
