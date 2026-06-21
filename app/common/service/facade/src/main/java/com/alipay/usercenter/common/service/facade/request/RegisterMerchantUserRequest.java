package com.alipay.usercenter.common.service.facade.request;

public class RegisterMerchantUserRequest extends RegisterUserRequest {

    private String merchantName;
    private String merchantCategory;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCategory() {
        return merchantCategory;
    }

    public void setMerchantCategory(String merchantCategory) {
        this.merchantCategory = merchantCategory;
    }
}
