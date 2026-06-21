package com.alipay.usercenter.biz.login;

/**
 * @author adam
 * @date 21/6/2026 5:45 PM
 */
public class LoginContextInfo {
    private String phoneNo;
    private String hashedPassword;
    private String id;
    private String status;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}