package com.alipay.usercenter.biz.login;

import com.alipay.usercenter.common.service.facade.enums.LoginType;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;
import com.alipay.usercenter.core.util.AssertUtil;

import java.util.Map;

/**
 * @author adam
 * @date 21/6/2026 5:26 PM
 */
public class LoginFactory {
    private final Map<String, LoginHandler> loginHandler;


    public LoginFactory(Map<String, LoginHandler> loginHandler) {
        this.loginHandler = loginHandler;
    }

    public LoginHandler getHandler(LoginType loginType) {
        AssertUtil.notNull(loginType, UserResultCode.PARAM_ILLEGAL, "loginType cannot be null");
        LoginHandler handler = loginHandler.get(String.valueOf(loginType));
        AssertUtil.notNull(handler, UserResultCode.PARAM_ILLEGAL, "loginType cannot be null");
        return handler;
    }
}