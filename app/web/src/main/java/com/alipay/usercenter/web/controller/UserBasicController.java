package com.alipay.usercenter.web.controller;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.usercenter.biz.cache.UserSecurityCache;
import com.alipay.usercenter.common.service.facade.api.UserService;
import com.alipay.usercenter.common.service.facade.baseresult.UserBizResult;
import com.alipay.usercenter.common.service.facade.item.UserInfoItem;
import com.alipay.usercenter.common.service.facade.request.*;
import com.alipay.usercenter.common.service.facade.result.LoginResult;
import com.alipay.usercenter.common.service.facade.result.OTPResult;
import com.alipay.usercenter.core.model.UserSecurity;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
@CrossOrigin(origins = "http://localhost:8083")
@RestController
@RequestMapping("/user/basic")
public class UserBasicController {

    @SofaReference
    private UserService userService;

    @Resource
    private UserSecurityCache userSecurityCache;

    @PostMapping("/insert/{userId}")
    public String insert(@PathVariable Long userId) {
        UserSecurity userSecurity = UserSecurity.newUser(userId);
        userSecurity.setLockedUntil(Instant.now()); // default past time
        userSecurityCache.update(userSecurity);     // insert/update cache
        return "Inserted user " + userId;
    }

    @GetMapping("/get/{userId}")
    public UserSecurity get(@PathVariable Long userId) {
        QueryUserSecurityRequest request = new QueryUserSecurityRequest();
        request.setUserId(userId);
        return userSecurityCache.queryUserSecurity(request); // fetch from cache
    }

    @PostMapping("/login.json")
    public UserBizResult<LoginResult> login(@RequestBody LoginRequest request) {
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

    @PostMapping("/queryUserInfo.json")
    public UserBizResult<UserInfoItem> queryUserInfo(@RequestBody QueryUserInfoRequest request) {
        try {
            return userService.queryUserInfo(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/changePassword.json")
    public UserBizResult<String> changePassword(@RequestBody ChangeAuthPasswordRequest request) {
        try {
            // should this be able to change user login password as well?
            return userService.changePassword(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}