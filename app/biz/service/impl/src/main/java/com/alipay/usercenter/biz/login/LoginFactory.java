package com.alipay.usercenter.biz.login;

import com.alipay.usercenter.common.service.facade.enums.LoginType;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;
import com.alipay.usercenter.core.util.AssertUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author adam
 * @date 21/6/2026 5:26 PM
 */
@Component
public class LoginFactory {
    private final Map<LoginType, LoginHandler> loginHandler;

    public LoginFactory(List<LoginHandler> loginHandlers) {
        this.loginHandler = loginHandlers.stream()
                .collect(Collectors.toMap(
                        LoginHandler::getType,
                        Function.identity()
                        ));
    }

    public LoginHandler getHandler(LoginType loginType) {
        AssertUtil.notNull(loginType, UserResultCode.PARAM_ILLEGAL, "loginType cannot be null");
        LoginHandler handler = loginHandler.get(loginType);
        System.out.println("HERE : " + loginType);
        AssertUtil.notNull(handler, UserResultCode.PARAM_ILLEGAL, "loginType cannot be null");
        return handler;
    }
}