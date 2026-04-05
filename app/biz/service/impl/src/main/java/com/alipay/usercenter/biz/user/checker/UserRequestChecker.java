package com.alipay.usercenter.biz.user.checker;

import com.alipay.usercenter.biz.validate.PasswordValidator;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;
import com.alipay.usercenter.common.service.facade.request.*;
import com.alipay.usercenter.core.util.AssertUtil;
import io.jsonwebtoken.lang.Assert;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import static com.alipay.usercenter.common.service.facade.enums.OTPSceneEnum.exists;

public class UserRequestChecker {

    public static void checkLoginRequest(LoginRequest request) {
        AssertUtil.notNull(request, UserResultCode.PARAM_ILLEGAL, "request cannot be null");
        AssertUtil.notBlank(request.getPassword(), UserResultCode.PARAM_ILLEGAL, "password cannot be blank");
        AssertUtil.notBlank(request.getPhoneNo(), UserResultCode.PARAM_ILLEGAL, "phone number cannot be blank");
    }

    public static void checkRegisterUserRequest(RegisterUserRequest request) {
        AssertUtil.notNull(request, UserResultCode.PARAM_ILLEGAL, "request cannot be null");
        AssertUtil.notBlank(request.getPhoneNo(), UserResultCode.PARAM_ILLEGAL, "phone number cannot be blank");
        AssertUtil.notBlank(request.getVerifiedToken(), UserResultCode.PARAM_ILLEGAL, "verified token cannot be blank");
        // Password validator
        PasswordValidator.check(request.getPassword());
        AssertUtil.notBlank(request.getConfirmPassword(), UserResultCode.PARAM_ILLEGAL, "confirm password cannot be blank");
    }


    public static void checkOTPRequest(VerifyOtpRequest request) {
        AssertUtil.notNull(request, UserResultCode.PARAM_ILLEGAL, "request cannot be null");
        AssertUtil.notBlank(request.getOtp(), UserResultCode.PARAM_ILLEGAL, "otp cannot be blank");
        AssertUtil.notBlank(request.getChallengeId(), UserResultCode.PARAM_ILLEGAL, "challenge id cannot be blank");
        AssertUtil.notBlank(request.getSceneCode().getScene(), UserResultCode.PARAM_ILLEGAL, "scene code cannot be blank");
        AssertUtil.isTrue(exists(request.getSceneCode().getScene()), UserResultCode.PARAM_ILLEGAL, "scene code not exist");
    }

    public static void checkSendOtpRequest(OTPRequest request) {
        AssertUtil.notNull(request, UserResultCode.PARAM_ILLEGAL, "request cannot be null");
        AssertUtil.notBlank(request.getPhoneNo(), UserResultCode.PARAM_ILLEGAL, "phoneNo cannot be blank");
        AssertUtil.notBlank(request.getOtpScene().getScene(), UserResultCode.PARAM_ILLEGAL, "challenge id cannot be blank");
        AssertUtil.isTrue(exists(request.getOtpScene().getScene()), UserResultCode.PARAM_ILLEGAL, "scene code not exist");
    }

    public static void checkQueryUserInfoRequest(QueryUserInfoRequest request) {
        AssertUtil.notNull(request, UserResultCode.PARAM_ILLEGAL, "request cannot be null");
        AssertUtil.notBlank(request.getPhoneNo(), UserResultCode.PARAM_ILLEGAL, "phoneNo cannot be blank");
        AssertUtil.notBlank(request.getUserId(), UserResultCode.PARAM_ILLEGAL, "User id cannot be blank");
    }

    public static void checkUserAuthRequest(VerifyUserAuthRequest request) {

    }

    public static void checkQueryCardDetailsRequest(QueryCardDetailsRequest request) {
    }

    public static void checkUpdateAutoReloadConfigRequest(UpdateAutoReloadConfigRequest request) {
        AssertUtil.notNull(request, UserResultCode.PARAM_ILLEGAL, "request cannot be null");
        AssertUtil.notBlank(request.getCardId(), UserResultCode.PARAM_ILLEGAL, "card id cannot be blank");
        AssertUtil.notBlank(request.getUserId(), UserResultCode.PARAM_ILLEGAL, "user id cannot be blank");
        AssertUtil.notBlank(request.getThresholdAmount().toString(), UserResultCode.PARAM_ILLEGAL, "threshold amount cannot be blank");
        AssertUtil.notBlank(request.getAutoReloadAmount().toString(), UserResultCode.PARAM_ILLEGAL, "auto reload amount cannot be blank");
    }

    public static void checkCreateNewCardRequest(CreateNewCardRequest request) {
        AssertUtil.notNull(request, UserResultCode.PARAM_ILLEGAL, "request cannot be null");
//        AssertUtil.notBlank(request.getUserId(), UserResultCode.PARAM_ILLEGAL, "user id cannot be blank");
//        AssertUtil.notBlank(request.getCardNo(), UserResultCode.PARAM_ILLEGAL, "card number cannot be blank");
//        AssertUtil.notBlank(request.getPaymentMethodId(), UserResultCode.PARAM_ILLEGAL, "payment method id cannot be blank");
//        AssertUtil.notBlank(request.getCardType().toString(), UserResultCode.PARAM_ILLEGAL, "card type cannot be blank");
//        AssertUtil.notBlank(request.getCardHolderName(), UserResultCode.PARAM_ILLEGAL, "cardholder name cannot be blank");
//        AssertUtil.notBlank(request.getCardIssuer().toString(), UserResultCode.PARAM_ILLEGAL, "card issuer cannot be blank");
//        AssertUtil.notBlank(request.getExpiryYear().toString(), UserResultCode.PARAM_ILLEGAL, "expiry year cannot be blank");
//        AssertUtil.notBlank(request.getExpiryMonth().toString(), UserResultCode.PARAM_ILLEGAL, "expiry month cannot be blank");
    }

    public static void checkToggleAutoReloadConfigRequest(ToggleAutoReloadConfigRequest request) {

    }

    public static void checkQueryAutoReloadConfigRequest(QueryAutoReloadConfigRequest request) {

    }

    public static void checkQueryDefaultCardRequest(QueryDefaultCardRequest request) {

    }

    public static void checkQueryUserInfoByUserIdRequest(QueryUserInfoRequest request) {
    }

    public static void checkSetPasswordPinRequest(SetPasswordPinRequest request) {
    }
}
