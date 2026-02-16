package com.alipay.usercenter.common.service.facade.request;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseRequest;
import com.alipay.usercenter.common.service.facade.enums.OTPSceneEnum;

public class VerifyOtpRequest extends UserBaseRequest {
    private String challengeId;
    private String otp;
    private OTPSceneEnum sceneCode;

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public OTPSceneEnum getSceneCode() {
        return sceneCode;
    }

    public void setSceneCode(OTPSceneEnum sceneCode) {
        this.sceneCode = sceneCode;
    }
}
