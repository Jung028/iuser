package com.alipay.usercenter.biz.registration;

import com.alipay.usercenter.common.service.facade.enums.RegistrationType;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;
import com.alipay.usercenter.core.util.AssertUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author adam
 * @date 21/6/2026 12:51 PM
 */
@Component
public class RegistrationFactory {

    private final Map<RegistrationType, RegistrationHandler> handlerMap;

    public RegistrationFactory(Map<RegistrationType, RegistrationHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public RegistrationHandler getHandler(RegistrationType registrationType) {
        AssertUtil.notBlank(String.valueOf(registrationType), UserResultCode.PARAM_ILLEGAL, "registration type cannot be blank");
        RegistrationHandler handler = handlerMap.get(registrationType);
        AssertUtil.notNull(handler, UserResultCode.PARAM_ILLEGAL, "registration type cannot be null");
        return handler;
    }




}