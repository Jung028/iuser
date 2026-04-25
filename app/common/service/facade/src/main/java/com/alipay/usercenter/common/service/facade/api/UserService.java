package com.alipay.usercenter.common.service.facade.api;

import com.alipay.usercenter.common.service.facade.baseresult.UserBizResult;
import com.alipay.usercenter.common.service.facade.item.OtpVerifiedClaims;
import com.alipay.usercenter.common.service.facade.item.UserInfoItem;
import com.alipay.usercenter.common.service.facade.request.*;
import com.alipay.usercenter.common.service.facade.result.LoginResult;
import com.alipay.usercenter.common.service.facade.result.OTPResult;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/userService")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserService {

    /**
     * user login
     *
     * @param request
     * @return
     */
    @POST
    @Path("/login")
    UserBizResult<LoginResult> login(LoginRequest request);

    /**
     * send OTP
     *
     * @param request
     * @return
     */
    @POST
    @Path("/sendOTP")
    UserBizResult<OTPResult> sendOTP(OTPRequest request);

    /**
     * verify OTP
     *
     * @param request
     * @return
     */
    @POST
    @Path("/verifyOTP")
    UserBizResult<String> verifyOTP(VerifyOtpRequest request);

    /**
     * register user
     *
     * @param request
     * @return
     */
    @POST
    @Path("/register")
    UserBizResult<Void> register(RegisterUserRequest request);

    /**
     * query user information
     *
     * @param request
     * @return
     */
    @POST
    @Path("/queryUserInfo")
    UserBizResult<UserInfoItem> queryUserInfo(QueryUserInfoRequest request);

    /**
     * verify user authentication details
     * @param request
     * @return
     */
    @POST
    @Path("/verifyUserAuth")
    UserBizResult<String> verifyUserAuth(VerifyUserAuthRequest request);

    /**
     * verify verified jwtToken
     * @param request
     * @return
     */
    @POST
    @Path("/verifyVerifiedToken")
    UserBizResult<OtpVerifiedClaims> verifyVerifiedToken(VerifyVerifiedTokenRequest request);

    /**
     * change password
     * @param request
     * @return
     */
    @POST
    @Path("/changePassword")
    UserBizResult<String> changePassword(ChangeAuthPasswordRequest request);

    /**
     * update user info
     * @param request
     * @return
     */
    @POST
    @Path("/updateUserInfo")
    UserBizResult<String> updateUserInfo(UpdateUserInfoRequest request);

    /**
     *
     * @param request
     * @return
     */
    @POST
    @Path("/queryUserInfoByUserId")
    UserBizResult<UserInfoItem> queryUserInfoByUserId(QueryUserInfoRequest request);

    /**
     * set password pin
     *
     * @param request
     * @return
     */
    @POST
    @Path("/setPasswordPin")
    UserBizResult<String> setPasswordPin(SetPasswordPinRequest request);

    /**
     * generate QR code
     * @param request
     * @return
     */
    @POST
    @Path("/generateQrCode")
    UserBizResult<GenerateQrCodeResult> generateQrCode(GenerateQrCodeRequest request);
}
