package com.alipay.usercenter.biz.login;

import com.alipay.usercenter.core.model.UserInfo;

/**
 * @author adam
 * @date 21/6/2026 5:24 PM
 */
public interface LoginHandler {

    LoginContextInfo loadContext(String phoneNo);
}