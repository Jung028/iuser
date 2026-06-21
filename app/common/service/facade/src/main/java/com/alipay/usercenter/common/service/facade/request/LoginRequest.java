package com.alipay.usercenter.common.service.facade.request;


import com.alipay.usercenter.common.service.facade.baseresult.UserBaseRequest;
import com.alipay.usercenter.common.service.facade.enums.LoginType;

public class LoginRequest extends UserBaseRequest {
    private String phoneNo;
    private String password;
    private LoginType loginType;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}
