package com.alipay.usercenter.common.service.facade.request;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseRequest;

/**
 * @author adam
 * @date 24/3/2026 10:23 PM
 */
public class QueryDefaultCardRequest extends UserBaseRequest {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}