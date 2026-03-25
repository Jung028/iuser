package com.alipay.usercenter.common.service.facade.item;

import com.alipay.usercenter.common.service.facade.config.Contact;
import com.alipay.usercenter.common.service.facade.config.ContactConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class UserInfoItem {
    private Long id;
    private String phoneNo;
    private String password;
    private Date gmtCreate;
    private Date gmtModified;
    private Long userId;
    private String status;
    private ContactConfig contactConfig;
    private String extInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getGmtCreate(Date gmtCreate) {
        return this.gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public ContactConfig getContactConfig() {
        return contactConfig;
    }

    public void setContactConfig(ContactConfig contactConfig) {
        this.contactConfig = contactConfig;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }
}
