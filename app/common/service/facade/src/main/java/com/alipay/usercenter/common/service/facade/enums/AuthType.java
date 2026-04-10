package com.alipay.usercenter.common.service.facade.enums;

/**
 * @author adam
 * @date 10/3/2026 2:48 PM
 */
public enum AuthType {
    LOGIN_PASSWORD(AuthScene.LOGIN, AuthMode.PASSWORD, "LOGIN_PASSWORD", "login password"),
    TRANSFER_PIN(AuthScene.TRANSFER_CONFIRM, AuthMode.PIN, "TRANSFER_PIN", "transfer pin");

    private AuthScene authScene;
    private AuthMode authMode;
    private String code;
    private String desc;

    public AuthScene getAuthScene() {
        return authScene;
    }

    public void setAuthScene(AuthScene authScene) {
        this.authScene = authScene;
    }

    public AuthMode getAuthMode() {
        return authMode;
    }

    public void setAuthMode(AuthMode authMode) {
        this.authMode = authMode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    AuthType(AuthScene authScene, AuthMode authMode, String code, String desc) {
        this.authScene = authScene;
        this.authMode = authMode;
        this.code = code;
        this.desc = desc;
    }
}