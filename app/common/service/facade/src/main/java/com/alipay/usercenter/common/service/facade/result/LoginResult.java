package com.alipay.usercenter.common.service.facade.result;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseResult;

/**
 * @author adam
 * @date 16/3/2026 12:06 AM
 */
public class LoginResult {
    private String jwtToken;
    private String userId;
    private String accountId;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}