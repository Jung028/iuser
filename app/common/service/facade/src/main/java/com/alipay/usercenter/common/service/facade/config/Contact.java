package com.alipay.usercenter.common.service.facade.config;

/**
 * @author adam
 * @date 19/3/2026 8:43 PM
 */
public class Contact {
    private String displayName;
    private String userId;
    private String phoneNo;
    private String initials;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }
}