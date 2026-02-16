package com.alipay.usercenter.common.service.facade.request;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseRequest;
import com.alipay.usercenter.common.service.facade.enums.OTPSceneEnum;

public class OTPRequest extends UserBaseRequest {
    private String phoneNo;
    private OTPSceneEnum otpScene;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public OTPSceneEnum getOtpScene() {
        return otpScene;
    }

    public void setOtpScene(OTPSceneEnum otpScene) {
        this.otpScene = otpScene;
    }
}
