package com.alipay.usercenter.common.service.facade.api;

import com.alipay.usercenter.common.service.facade.baseresult.UserBizResult;
import com.alipay.usercenter.common.service.facade.item.UserInfoItem;
import com.alipay.usercenter.common.service.facade.request.*;
import com.alipay.usercenter.common.service.facade.result.OTPResult;

public interface UserService {

    /**
     * user login
     *
     * @param request
     * @return
     */
    UserBizResult<String> login(LoginRequest request);

    /**
     * send OTP
     *
     * @param request
     * @return
     */
    UserBizResult<OTPResult> sendOTP(OTPRequest request);

    /**
     * verify OTP
     *
     * @param request
     * @return
     */
    UserBizResult<String> verifyOTP(VerifyOtpRequest request);

    /**
     * register user
     *
     * @param request
     * @return
     */
    UserBizResult<Void> register(RegisterUserRequest request);

    /**
     * query user information
     *
     * @param request
     * @return
     */
    UserBizResult<UserInfoItem> queryUserInfo(QueryUserInfoRequest request);
}
