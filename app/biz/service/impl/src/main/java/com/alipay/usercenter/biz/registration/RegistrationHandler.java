package com.alipay.usercenter.biz.registration;

import com.alipay.usercenter.common.service.facade.request.RegisterUserRequest;

/**
 * @author adam
 * @date 21/6/2026 12:50 PM
 */
public interface RegistrationHandler {

    void validate(RegisterUserRequest request);

    void createNewAccount(RegisterUserRequest request);
}