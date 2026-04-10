package com.alipay.usercenter.common.service.facade.request;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseRequest;
import com.alipay.usercenter.common.service.facade.enums.AuthScene;
import com.alipay.usercenter.common.service.facade.enums.AuthType;

/**
 * @author adam
 * @date 10/3/2026 2:23 PM
 */
public class VerifyUserAuthRequest extends UserBaseRequest {
    private String userId;
    private String authType;
    private String credential; // password

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }
}