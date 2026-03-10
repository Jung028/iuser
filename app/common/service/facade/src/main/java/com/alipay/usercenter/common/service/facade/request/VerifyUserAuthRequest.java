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
    private AuthType authType;
    private AuthScene authScene;
    private String credential; // password

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public AuthScene getAuthScene() {
        return authScene;
    }

    public void setAuthScene(AuthScene authScene) {
        this.authScene = authScene;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }
}