package com.alipay.usercenter.common.service.facade.enums;

public enum OTPSceneEnum {
    REGISTER("REGISTER", "User registeration"),
    LOGIN("LOGIN", "User login"),
    RESET_PASSWORD("RESET_PASSWORD", "Reset password"),
    TRANSFER_OVER_LIMIT("TRANSFER_OVER_LIMIT", "Transfer over limit")
    ;

    private String scene;
    private String description;

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    OTPSceneEnum(String scene, String description) {
        this.scene = scene;
        this.description = description;
    }
}
