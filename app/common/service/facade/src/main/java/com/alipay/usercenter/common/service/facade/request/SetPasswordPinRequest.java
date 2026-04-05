package com.alipay.usercenter.common.service.facade.request;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseRequest;
import com.alipay.usercenter.common.service.facade.enums.AuthScene;
import com.alipay.usercenter.common.service.facade.enums.AuthType;

/**
 * @author adam
 * @date 6/4/2026 12:14 AM
 */
public class SetPasswordPinRequest extends UserBaseRequest {
    private String passwordPin;
    private String authScene;
    private String authType;
    private String userId;

    public String getPasswordPin() {
        return passwordPin;
    }

    public void setPasswordPin(String passwordPin) {
        this.passwordPin = passwordPin;
    }

    public String getAuthScene() {
        return authScene;
    }

    public void setAuthScene(String authScene) {
        this.authScene = authScene;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void validate() {
        if (passwordPin == null || passwordPin.isEmpty()) {
            throw new IllegalArgumentException("PIN cannot be null or empty");
        }

        if (passwordPin.length() != 6) {
            throw new IllegalArgumentException("PIN must be exactly 6 digits");
        }

        if (!passwordPin.matches("\\d{6}")) {
            throw new IllegalArgumentException("PIN must contain only numeric digits");
        }
    }
}