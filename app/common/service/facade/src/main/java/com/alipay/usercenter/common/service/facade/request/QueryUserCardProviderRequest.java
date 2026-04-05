package com.alipay.usercenter.common.service.facade.request;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseRequest;
import com.alipay.usercenter.common.service.facade.enums.Provider;

/**
 * @author adam
 * @date 3/4/2026 11:40 PM
 */
public class QueryUserCardProviderRequest extends UserBaseRequest {
    private String userId;
    private Provider provider;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}