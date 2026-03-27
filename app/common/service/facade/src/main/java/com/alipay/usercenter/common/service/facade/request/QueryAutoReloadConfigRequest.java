package com.alipay.usercenter.common.service.facade.request;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseRequest;

/**
 * @author adam
 * @date 26/3/2026 4:57 PM
 */
public class QueryAutoReloadConfigRequest extends UserBaseRequest {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}