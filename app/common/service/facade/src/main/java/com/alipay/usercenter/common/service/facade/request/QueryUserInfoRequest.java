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
     * get phone no
     * @return
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * set phone no
     * @param phoneNo
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
