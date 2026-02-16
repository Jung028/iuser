package com.alipay.usercenter.common.service.facade.request;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseRequest;

public class QueryUserInfoRequest extends UserBaseRequest {

    /**
     * userId
     */
    private String userId;
    /**
     * phoneNo
     */
    private String phoneNo;

    /**
     * userId
     * @return
     */
    public String getUserId() {
        return userId;
    }

    /**
     * setUserid
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * getPhoneNo
     * @return
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * setPhoneNo
     * @param phoneNo
     */
    public void setPhoneNo(String phoneNo) {}

}
