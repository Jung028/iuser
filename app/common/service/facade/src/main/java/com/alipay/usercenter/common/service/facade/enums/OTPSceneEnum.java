package com.alipay.usercenter.common.service.facade.enums;

public enum OTPSceneEnum {
    REGISTER("REGISTER", "User registration"),
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

    // create method for go through all to find
    public static boolean exists(String scene) {
        if  (null == scene) { return false; }
        for (OTPSceneEnum sceneEnum : OTPSceneEnum.values()) {
            if (sceneEnum.getScene().equals(scene)) {
                return true;
            }
        }
        return false;
    }
}
