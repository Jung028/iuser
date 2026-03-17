package com.alipay.usercenter.common.service.facade.request;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseRequest;

public class VerifyVerifiedTokenRequest extends UserBaseRequest {
    private String verifiedToken;

    public String getVerifiedToken() {
        return verifiedToken;
    }

    public void setVerifiedToken(String verifiedToken) {
        this.verifiedToken = verifiedToken;
    }
}
