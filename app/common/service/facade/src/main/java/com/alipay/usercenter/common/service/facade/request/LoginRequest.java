package com.alipay.usercenter.common.service.facade.request;


import com.alipay.usercenter.common.service.facade.baseresult.UserBaseRequest;

public class LoginRequest extends UserBaseRequest {
    private String phoneNumber;
    private String password;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
