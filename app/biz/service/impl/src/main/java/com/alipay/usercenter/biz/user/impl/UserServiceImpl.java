package com.alipay.usercenter.biz.user.impl;

import com.alipay.usercenter.biz.jwt.JwtClaims;
import com.alipay.usercenter.biz.jwt.JwtContextHolder;
import com.alipay.usercenter.biz.template.UserBizCallback;
import com.alipay.usercenter.biz.user.helper.BusinessServiceHelper;
import com.alipay.usercenter.common.service.facade.api.UserService;
import com.alipay.usercenter.biz.util.UserPasswordUtil;
import com.alipay.usercenter.common.service.facade.baseresult.UserBizResult;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;
import com.alipay.usercenter.common.service.facade.exception.UserBizException;
import com.alipay.usercenter.common.service.facade.item.OtpVerifiedClaims;
import com.alipay.usercenter.biz.user.checker.UserRequestChecker;
import com.alipay.usercenter.common.service.facade.item.UserInfoItem;
import com.alipay.usercenter.common.service.facade.request.*;
import com.alipay.usercenter.common.service.facade.result.OTPResult;
import com.alipay.usercenter.common.util.LogUtil;
import com.alipay.usercenter.common.service.facade.item.OtpChallengeItem;
import com.alipay.usercenter.core.converter.UserInfoConvertor;
import com.alipay.usercenter.core.domain.UserInfo;
import com.alipay.usercenter.core.enums.UserAccountStatusEnum;
import com.alipay.usercenter.core.enums.UserActionEnum;
import com.alipay.usercenter.core.model.UserSecurity;
import com.alipay.usercenter.core.util.AssertUtil;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.Instant;
import java.util.Date;

import static com.alipay.usercenter.common.service.facade.constant.GlobalUserConstant.LOCKOUT_TIME_5_MINUTES;

/**
 * author adam
 */
public class UserServiceImpl extends AbstractUserBizService implements UserService {

    @Override
    public UserBizResult<String> login(LoginRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.LOGIN, new UserBizCallback<>() {

            @Override
            protected UserBizResult<String> createDefaultResponse() {
                return new UserBizResult<>();
            }

            @Override
            protected void checkParams(LoginRequest request) {
                UserRequestChecker.checkLoginRequest(request);
            }

            @Override
            protected void process(LoginRequest request, UserBizResult<String> response) {
                //Check user_security cache for failed attempts/lockout.
                UserInfo userInfo = userInfoRepository.queryUserInfo(request.getPhoneNumber());
                // query user security cache from redis.
                QueryUserSecurityRequest queryUserSecurityRequest = new QueryUserSecurityRequest();
                queryUserSecurityRequest.setUserId(userInfo.getUserId());
                UserSecurity userSecurity = userSecurityCache.queryUserSecurity(queryUserSecurityRequest);

                //get current datetime
                Instant now = Instant.now();
                if (now.isBefore(userSecurity.getLockedUntil())) {
                    response.setResult("Invalid credentials");
                    LogUtil.info(LOGGER, "User {} is locked out until {}", userSecurity.getUserId(), userSecurity.getLockedUntil().toString());
                } else {
                    //Fetch user info from DB and validate password hash.
                    String hashedUserPassword = userInfo.getHashedPassword();
                    boolean isValidPassword = UserPasswordUtil.verifyPassword(request.getPassword(), hashedUserPassword);
                    if (isValidPassword) {
                        //Reset failed attempts in cache.
                        userSecurity.setFailedAttempts(0);
                        userSecurityCache.update(userSecurity);
                        //Generate JWT token containing user_id, roles, expiration.
                        String jwtToken = jwtTokenUtil.generateTokenForUserInfo(userInfo);
                        //Optionally create session entry in DB (for logout, multi-device, or revocation).
                        response.setResult(jwtToken);
                    } else {
                        //Increment failed attempts in cache.
                        int failedAttempts = userSecurity.getFailedAttempts() + 1;
                        userSecurity.setFailedAttempts(failedAttempts);
                        //If failed attempts to exceed threshold, set lockout time in cache.
                        BusinessServiceHelper.calculateNDecideLockoutTime(failedAttempts, userSecurity);
                        userSecurityCache.update(userSecurity);
                        //Return error indicating invalid credentials.
                        response.setResult("Invalid credentials");
                    }
                }
            }
            });
    }



    @Override
    public UserBizResult<OTPResult> sendOTP(OTPRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.SEND_OTP, new UserBizCallback<>() {

            @Override
            protected UserBizResult<OTPResult> createDefaultResponse() {
                return new UserBizResult<>();
            }

            @Override
            protected void checkParams(OTPRequest request) {
                UserRequestChecker.checkSendOtpRequest(request);
            }


            @Override
            protected void process(OTPRequest request, UserBizResult<OTPResult> response) {
                // create otp challenge
                OtpChallengeItem challenge = otpChallenge
                        .createAndStoreChallenge(request.getPhoneNo(), request.getOtpScene());

                // Build response (NEVER include OTP)
                OTPResult result = new OTPResult();
                result.setChallengeId(challenge.getChallengeId());
                result.setSceneCode(challenge.getSceneCode());
                result.setExpireAt(challenge.getExpireAt());
                result.setRetryLeft(challenge.getMaxRetry() - challenge.getRetryCount());

                response.setResult(result);
                response.setSuccess(true);
            }
        });
    }




    @Override
    public UserBizResult<String> verifyOTP(VerifyOtpRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.VERITY_OTP, new UserBizCallback<>() {
            @Override
            protected UserBizResult<String> createDefaultResponse() {
                return new UserBizResult<>();
            }

            @Override
            protected void checkParams(VerifyOtpRequest request) {
                UserRequestChecker.checkOTPRequest(request);
            }

            @Override
            protected void process(VerifyOtpRequest request, UserBizResult<String> response) {

                // load otp challenge item.
                OtpChallengeItem challenge =
                        otpChallenge.queryOTP(request.getChallengeId());

                AssertUtil.notNull(challenge, UserResultCode.OTP_EXPIRED, "OTP challenge not found");

                // when is the lockoutUntil set? - after max retries exceeded
                if (challenge.getLockoutUntil() != null & Instant.now().isBefore(challenge.getLockoutUntil())) {
                    AssertUtil.isTrue(false, UserResultCode.TIMEOUT_LOCK,
                            "OTP locked until " + challenge.getLockoutUntil());
                }

                // verify the otp from request equals to the hashed otp in challenge.
                boolean match = BCrypt.checkpw(
                        request.getOtp(), challenge.getOtpHash());

                // verify the challenge otp sceneCode matches otherwise assert
                AssertUtil.isTrue(request.getSceneCode().equals(challenge.getSceneCode()),
                        UserResultCode.OTP_SCENE_MISMATCH,
                        "OTP scene code doesnt match the challenge item scene code. Invalid request.");

                // if not matched, increase retry count, set lockout time to + 5 minutes
                if (!match) {
                    // if not match, increase retry count.
                    challenge.setRetryCount(challenge.getRetryCount() + 1);

                    // if retry count >= max retry, throw a BIZException.
                    if (challenge.getRetryCount() >= challenge.getMaxRetry()) {
                        // set lockout time for 5 minutes
                        challenge.setLockoutUntil(
                                Instant.now().plusSeconds(LOCKOUT_TIME_5_MINUTES)
                        );
                        throw new UserBizException(UserResultCode.TIMEOUT_LOCK.getCode(),
                                "OTP locked until " + challenge.getLockoutUntil());
                    }

                    otpChallenge.update(challenge);
                    AssertUtil.isTrue(false, UserResultCode.OTP_INVALID, "Entered OTP is invalid");
                }

                // update challenge as verified
                challenge.setVerified(true);
                otpChallenge.update(challenge);

                // create a JWT for frontend to verify success created
                String verifiedToken = otpChallenge.issueJWTToken(challenge);

                response.setResult(verifiedToken);
                response.setSuccess(true);
            }
        });
    }


    @Override
    public UserBizResult<Void> register(RegisterUserRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.REGISTER, new UserBizCallback<>() {
            @Override
            protected UserBizResult<Void> createDefaultResponse() {
                return new UserBizResult<>();
            }

            @Override
            protected void checkParams(RegisterUserRequest request) {
                UserRequestChecker.checkRegisterUserRequest(request);
            }

            @Override
            protected void process(RegisterUserRequest request, UserBizResult<Void> result) {
                // use a transaction to ensure data consistency
                userTransactionTemplate.execute(status -> {
                    // validate the JWT token first
                    OtpVerifiedClaims otpVerifiedClaims = otpChallenge.verifyVerifiedToken(
                            request.getVerifiedToken());

                    //cross-check phone number
                    AssertUtil.isTrue(otpVerifiedClaims.getPhoneNo().equals(request.getPhoneNo()),
                            UserResultCode.PHONE_NO_MISMATCH,
                            "Phone number does not match with verified OTP phone number");

                    // verify the user exists
                    UserInfo existingUser = userInfoRepository.queryUserInfo(
                            request.getPhoneNo());
                    AssertUtil.notNull(existingUser, UserResultCode.USER_NOT_FOUND,
                            "User not found for phone number: " + request.getPhoneNo());

                    // hash password
                    String hashedPassword = UserPasswordUtil.hashPassword(request.getPassword());

                    // then set the request, and update the db for user info.
                    UserInfo userInfo = new UserInfo();
                    userInfo.setGmtCreate(new Date());
                    userInfo.setGmtModified(new Date());
                    userInfo.setPhoneNo(request.getPhoneNo());
                    userInfo.setStatus(UserAccountStatusEnum.ACTIVE.getCode());
                    userInfo.setHashedPassword(hashedPassword);

                    // insert a new user info
                    userInfoRepository.insertUserInfo(userInfo);

                    result.setSuccess(true);
                    result.setResultMessage("Successfully created account");
                    return null;
                });
            }

        });
    }

    @Override
    public UserBizResult<UserInfoItem> queryUserInfo(QueryUserInfoRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.QUERY_USER_INFO, new UserBizCallback<>() {
            @Override
            protected UserBizResult<UserInfoItem> createDefaultResponse() {
                return new UserBizResult<>();
            }

            @Override
            protected void checkParams(QueryUserInfoRequest request) {
                UserRequestChecker.checkQueryUserInfoRequest(request);
            }

            @Override
            protected void process(QueryUserInfoRequest request, UserBizResult<UserInfoItem> response) {
                // get the hash otp, verify the user.
                JwtClaims claims = JwtContextHolder.get();
                AssertUtil.isTrue(claims.getUserId().equals(request.getUserId()), UserResultCode.NO_PERMISSION, "user has no permission");
                UserInfo userInfo = userInfoRepository.queryUserInfo(request.getPhoneNo());
                // convert userInfo to item
                UserInfoItem userInfoItem = UserInfoConvertor.convertItem(userInfo);
                response.setResult(userInfoItem);
            }
        });
    }
}
