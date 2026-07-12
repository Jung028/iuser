package com.alipay.usercenter.biz.login;

import com.alipay.usercenter.common.service.facade.enums.LoginType;
import com.alipay.usercenter.core.model.UserInfo;

/**
 * @author adam
 * @date 21/6/2026 5:24 PM
 */
public interface LoginHandler {

    LoginType getType();

    LoginContextInfo loadContext(String phoneNo);
}