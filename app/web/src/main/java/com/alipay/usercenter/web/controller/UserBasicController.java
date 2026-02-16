package com.alipay.usercenter.web.controller;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.usercenter.common.service.facade.api.UserService;
import com.alipay.usercenter.common.service.facade.baseresult.UserBizResult;
import com.alipay.usercenter.common.service.facade.request.LoginRequest;
import com.alipay.usercenter.common.service.facade.request.OTPRequest;
import com.alipay.usercenter.common.service.facade.request.RegisterUserRequest;
import com.alipay.usercenter.common.service.facade.request.VerifyOtpRequest;
import com.alipay.usercenter.common.service.facade.result.OTPResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/basic")
public class UserBasicController {

    @SofaReference
    private UserService userService;

    @PostMapping("/login.json")
    public UserBizResult<String> login(@RequestBody LoginRequest request) {
        try {
             return userService.login(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/sendOTP.json")
    public UserBizResult<OTPResult> sendOTP(@RequestBody OTPRequest request) {
        try {
            return userService.sendOTP(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/verifyOTP.json")
    public UserBizResult<String> verifyOTP(@RequestBody VerifyOtpRequest request) {

        try {
            return userService.verifyOTP(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/register.json")
    public UserBizResult<Void> register(@RequestBody RegisterUserRequest request) {
        try {
            return userService.register(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}