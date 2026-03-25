package com.alipay.usercenter.common.service.facade.request;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseRequest;

/**
 * @author adam
 * @date 24/3/2026 7:22 PM
 */
public class UpdateUserInfoRequest extends UserBaseRequest {
    private String extInfo;
    private String userId;

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}