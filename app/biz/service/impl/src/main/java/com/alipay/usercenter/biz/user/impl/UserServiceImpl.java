package com.alipay.usercenter.biz.user.impl;

import com.alipay.account_center.common.service.facade.baseresult.AccountBizResult;
import com.alipay.account_center.common.service.facade.enums.AccountTypeEnum;
import com.alipay.account_center.common.service.facade.item.AccountInfoItem;
import com.alipay.account_center.common.service.facade.request.CreateAccountRequest;
import com.alipay.account_center.common.service.facade.request.QueryAccountInfoRequest;
import com.alipay.sofa.rpc.common.json.JSON;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.alipay.usercenter.biz.helper.GenerateUserId;
import com.alipay.usercenter.biz.jwt.JwtClaims;
import com.alipay.usercenter.biz.jwt.JwtContextHolder;
import com.alipay.usercenter.biz.template.UserBizCallback;
import com.alipay.usercenter.biz.user.helper.BusinessServiceHelper;
import com.alipay.usercenter.biz.user.helper.ResponseBuilder;
import com.alipay.usercenter.common.service.facade.api.UserService;
import com.alipay.usercenter.biz.util.UserPasswordUtil;
import com.alipay.usercenter.common.service.facade.baseresult.UserBizResult;
import com.alipay.usercenter.common.service.facade.enums.AuthType;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;
import com.alipay.usercenter.common.service.facade.enums.UserResultEnum;
import com.alipay.usercenter.common.service.facade.exception.UserBizException;
import com.alipay.usercenter.common.service.facade.item.OtpVerifiedClaims;
import com.alipay.usercenter.biz.user.checker.UserRequestChecker;
import com.alipay.usercenter.common.service.facade.item.UserInfoItem;
import com.alipay.usercenter.common.service.facade.request.*;
import com.alipay.usercenter.common.service.facade.result.LoginResult;
import com.alipay.usercenter.common.service.facade.result.OTPResult;
import com.alipay.usercenter.common.util.LogUtil;
import com.alipay.usercenter.common.service.facade.item.OtpChallengeItem;
import com.alipay.usercenter.core.converter.UserInfoConvertor;
import com.alipay.usercenter.core.enums.UserSecurityStatusEnum;
import com.alipay.usercenter.core.model.UserAuth;
import com.alipay.usercenter.core.model.UserInfo;
import com.alipay.usercenter.core.enums.UserAccountStatusEnum;
import com.alipay.usercenter.core.enums.UserActionEnum;
import com.alipay.usercenter.core.model.UserSecurity;
import com.alipay.usercenter.core.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.time.Instant;

import static com.alipay.usercenter.biz.util.UserPasswordUtil.hashPassword;
import static com.alipay.usercenter.common.service.facade.constant.GlobalUserConstant.*;

/**
 * author adam
 */
@SofaService(
        interfaceType = UserService.class,
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "rest")
        }
)
@Service
public class UserServiceImpl extends AbstractUserBizService implements UserService {

    @Override
    public UserBizResult<LoginResult> login(LoginRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.LOGIN, new UserBizCallback<>() {

            @Override
            protected UserBizResult<LoginResult> createDefaultResponse() {
                return new UserBizResult<>();
            }

            @Override
            protected void checkParams(LoginRequest request) {
                UserRequestChecker.checkLoginRequest(request);
            }

            @Override
            protected void process(LoginRequest request, UserBizResult<LoginResult> response) {
                //Check user_security cache for failed attempts/lockout.
                UserInfo userInfo = userInfoRepository.queryUserInfo(request.getPhoneNo());
                if (userInfo == null) {
                    ResponseBuilder.fail(response, UserActionEnum.LOGIN.getCode(),  "Phone number not registered");
                }

                // query user security cache from redis.
                QueryUserSecurityRequest queryUserSecurityRequest = new QueryUserSecurityRequest();
                queryUserSecurityRequest.setUserId(userInfo.getUserId());
                //get current datetime, before initialize lockout default time
                Instant now = Instant.now();

                UserSecurity userSecurity = userSecurityCache.queryUserSecurity(queryUserSecurityRequest);
                AssertUtil.notNull(userSecurity, UserResultEnum.SYSTEM_EXCEPTION, "User Security is null");

                if (now.isBefore((userSecurity.getLockedUntil()))) {
                    String timeLeft = calculateTimeout(now, userSecurity);
                    LogUtil.info(LOGGER, "User locked out: " + userSecurity.getUserId()
                            + ", Lockout time: " + userSecurity.getLockedUntil()
                            + ", attempts left: " + userSecurity.getFailedAttempts()
                            + ", status: " + userSecurity.getStatus());
                    ResponseBuilder.fail(response, UserActionEnum.LOGIN.getCode(), "Locked out until: " + timeLeft);
                    if (userSecurity.getStatus().equals(UserSecurityStatusEnum.PERMANENT_LOCK.getCode())) {
                        ResponseBuilder.fail(response, UserActionEnum.LOGIN.getCode(), "Locked out permanent ");
                    }
                } else {
                    //Fetch user info from DB and validate password hash.
                    String hashedUserPassword = userInfo.getHashedPassword();
                    boolean isValidPassword = UserPasswordUtil.verifyPassword(request.getPassword(), hashedUserPassword);
                    if (isValidPassword) {
                        //directly delete the security upon success login.
                        userSecurityCache.delete(userSecurity.getUserId());
                        //Generate JWT token containing user_id, roles, expiration.
                        String jwtToken = jwtUtil.generateTokenForUserInfo(userInfo);
                        LoginResult loginResult = new LoginResult();
                        loginResult.setJwtToken(jwtToken);
                        loginResult.setUserId(userInfo.getUserId().toString());
                        loginResult.setPhoneNo(userInfo.getPhoneNo());

                        // also return the account id for the queryBalance
                        QueryAccountInfoRequest queryAccountInfoRequest = new QueryAccountInfoRequest();
                        queryAccountInfoRequest.setUserId(userInfo.getUserId().toString());
                        AccountBizResult<AccountInfoItem> accountResult = accountServiceClient.queryAccountInfoByUserId(queryAccountInfoRequest);

                        loginResult.setAccountId(accountResult.getResult().getAccountId());

                        //Optionally create session entry in DB (for logout, multi-device, or revocation).
                        ResponseBuilder.success(response, loginResult, UserActionEnum.LOGIN.getCode(), "Login Success");
                    } else {
                        //Increment failed attempts in cache.
                        int failedAttempts = userSecurity.getFailedAttempts() + 1;
                        userSecurity.setFailedAttempts(failedAttempts);
                        BusinessServiceHelper.calculateNDecideLockoutTime(failedAttempts, userSecurity);
                        UserSecurity userSecurityResult = userSecurityCache.update(userSecurity);

                        // Determine response based on lock status
                        String status = userSecurityResult.getStatus();

                        if (UserSecurityStatusEnum.PERMANENT_LOCK.name().equals(status)) {
                            ResponseBuilder.fail(response, UserActionEnum.LOGIN.getCode(), "Locked out permanently");
                        } else if (UserSecurityStatusEnum.TIMEOUT_LOCK.getCode().equals(status)) {
                            // Calculate remaining lock time
                            long remainingMillis = userSecurityResult.getLockedUntil().toEpochMilli() - System.currentTimeMillis();
                            long minutesLeft = Math.max(1, remainingMillis / (60 * 1000)); // at least 1 min
                            ResponseBuilder.fail(response, UserActionEnum.LOGIN.getCode(), "Incorrect password, timeout (" + minutesLeft + " min left)");
                        } else {
                            int attemptsLeft = Math.max(0, MAX_FAILED_ATTEMPTS - failedAttempts);
                            ResponseBuilder.fail(response, UserActionEnum.LOGIN.getCode(),  "Incorrect password, attempts left: " + attemptsLeft);
                        }
                    }
                }
            }
        });
    }

    /**
     * calculate timeout countdown left to display to user
     * @param now
     * @param userSecurity
     * @return
     */
    private static String calculateTimeout(Instant now, UserSecurity userSecurity) {
        Duration durationLeft = Duration.between(now, userSecurity.getLockedUntil());
        long minutes = durationLeft.toMinutes();
        long seconds = durationLeft.minusMinutes(minutes).getSeconds();

        String timeLeft = "";
        if (minutes > 0) {
            timeLeft += minutes + " minute" + (minutes > 1 ? "s" : "");
        }
        if (seconds > 0) {
            if (!timeLeft.isEmpty()) timeLeft += " ";
            timeLeft += seconds + " second" + (seconds > 1 ? "s" : "");
        }
        return timeLeft;
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
                ResponseBuilder.success(response, result, UserActionEnum.SEND_OTP.getCode(),"Successfully sent OTP");
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
                if (challenge.getLockoutUntil() != null && Instant.now().isBefore(challenge.getLockoutUntil())) {
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
                ResponseBuilder.success(response, verifiedToken, UserActionEnum.VERITY_OTP.getCode(),"Verify OTP");
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
                    // validate the JWT token first, because when register, we verify phone no
                    OtpVerifiedClaims otpVerifiedClaims = otpChallenge.verifyVerifiedToken(
                            request.getVerifiedToken());
                    System.out.println(otpVerifiedClaims.getPhoneNo());
                    //cross-check phone number
                    AssertUtil.isTrue(otpVerifiedClaims.getPhoneNo().equals(request.getPhoneNo()),
                            UserResultCode.PHONE_NO_MISMATCH,
                            "Phone number does not match with verified OTP phone number");

                    // verify password matches
                    AssertUtil.isTrue(request.getConfirmPassword().equals(request.getPassword()),
                            UserResultCode.PASSWORD_MISMATCH, "Passwords do not match");

                    // check if there is already an existing account for this phone no
                    UserInfo existingUser = userInfoRepository.queryUserInfo(
                            request.getPhoneNo());
                    AssertUtil.isNull(existingUser, UserResultCode.EXISTING_USER,
                            "user account already exists, " + request.getPhoneNo());

                    // hash password
                    String hashedPassword = hashPassword(request.getPassword());

                    // then set the request, and update the db for user info.
                    UserInfo userInfo = new UserInfo();
                    userInfo.setUserId(GenerateUserId.nextId());
                    userInfo.setGmtCreate(new Date());
                    userInfo.setGmtModified(new Date());
                    userInfo.setPhoneNo(request.getPhoneNo());
                    userInfo.setStatus(UserAccountStatusEnum.ACTIVE.getCode());
                    userInfo.setHashedPassword(hashedPassword);

                    // insert a new user info
                    userInfoRepository.insertUserInfo(userInfo);

                    // insert a new user account
                    CreateAccountRequest createAccountRequest = new CreateAccountRequest();
                    createAccountRequest.setAccountName("Savings");
                    createAccountRequest.setAccountType(AccountTypeEnum.SAVINGS);
                    createAccountRequest.setUserId(userInfo.getUserId().toString());
                    createAccountRequest.setCurrency(DEFAULT_CURRENCY);
                    AccountBizResult<String> createAccount = accountServiceClient.createAccount(createAccountRequest);
                    AssertUtil.isTrue(createAccount.isSuccess(), UserResultCode.SYSTEM_EXCEPTION, "Failed to create account");

                    // after success creation of account, insert password into user_auth table
                    userAuthRepository.insertUserAuth(new UserAuth() {{
                        setUserId(userInfo.getUserId().toString());
                        setAuthType(AuthType.LOGIN_PASSWORD.getCode());
                        setIsActive(true);
                        setLastUsed(new Date());
                        setCredentialHash(hashedPassword);
                        setGmtCreate(new Date());
                        setGmtModified(new Date());
                    }});

                    // insertinto user_auth
                    ResponseBuilder.success(result, null, UserActionEnum.REGISTER.getCode(), "Registered account");
                    return null;
                });
            }

        });
    }

    @Override
    public UserBizResult<UserInfoItem> queryUserInfo(QueryUserInfoRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.QUERY_USER_INFO,
                new UserBizCallback<>() {
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
                // get the userId from the header.
                AssertUtil.isTrue(claims.getSubject().equals(request.getUserId()), UserResultCode.NO_PERMISSION, "user has no permission");
                UserInfo userInfo = userInfoRepository.queryUserInfo(request.getPhoneNo());
                // convert userInfo to item
                if (userInfo != null) {
                    UserInfoItem userInfoItem = UserInfoConvertor.convertToItem(userInfo);
                    System.out.println(JSON.toJSONString(userInfoItem.getContactConfig()));
                    ResponseBuilder.success(response, userInfoItem, UserActionEnum.QUERY_USER_INFO.getCode(), "Query User Info Item");
                } else {
                    ResponseBuilder.fail(response, UserActionEnum.QUERY_USER_INFO.getCode(), "Query User Info Item");
                }
            }
        });
    }

    @Override
    public UserBizResult<String> verifyUserAuth(VerifyUserAuthRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.VERIFY_USER_AUTH, new UserBizCallback<>() {
            @Override
            protected UserBizResult<String> createDefaultResponse() {
                return new UserBizResult<>();
            }

            @Override
            protected void checkParams(VerifyUserAuthRequest request) {
                UserRequestChecker.checkUserAuthRequest(request);
            }

            @Override
            protected void process(VerifyUserAuthRequest request, UserBizResult<String> response) {
                // query user_auth
                UserAuth userAuth = userAuthRepository.queryUserAuth(request.getUserId(), request.getAuthType());

                // validate
                String credentialHash = userAuth.getCredentialHash();
                boolean isValidPassword = UserPasswordUtil.verifyPassword(request.getCredential(), credentialHash);
                if (isValidPassword) {
                    ResponseBuilder.success(response, "Password verified", UserActionEnum.VERIFY_USER_AUTH.getCode(),
                            UserActionEnum.VERIFY_USER_AUTH.getDesc());
                } else {
                    ResponseBuilder.fail(response, UserActionEnum.VERIFY_USER_AUTH.getDesc(), "Password incorrect");
                }
            }
        });
    }

    @Override
    public UserBizResult<OtpVerifiedClaims> verifyVerifiedToken(VerifyVerifiedTokenRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.VERIFY_VERIFIED_TOKEN,
                new UserBizCallback<>() {
                    @Override
                    protected UserBizResult<OtpVerifiedClaims> createDefaultResponse() {
                        return new UserBizResult<>();
                    }

                    @Override
                    protected void checkParams(VerifyVerifiedTokenRequest request) {
                        AssertUtil.notNull(request.getVerifiedToken(), UserResultCode.PARAM_ILLEGAL, "verified token is null");
                    }

                    @Override
                    protected void process(VerifyVerifiedTokenRequest request, UserBizResult<OtpVerifiedClaims> response) {
                        OtpVerifiedClaims claims = otpChallenge.
                                verifyVerifiedToken(request.getVerifiedToken());
                        ResponseBuilder.success(response, claims, UserActionEnum.VERIFY_VERIFIED_TOKEN.getCode(),
                                UserActionEnum.VERIFY_VERIFIED_TOKEN.getDesc());
                    }
                });
    }

    @Override
    public UserBizResult<String> changePassword(ChangeAuthPasswordRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.QUERY_USER_INFO,
                new UserBizCallback<>() {
                    @Override
                    protected UserBizResult<String> createDefaultResponse() {
                        return new UserBizResult<>();
                    }

                    @Override
                    protected void checkParams(ChangeAuthPasswordRequest request) {

                    }

                    @Override
                    protected void process(ChangeAuthPasswordRequest request, UserBizResult<String> response) {
                        if (request.getNewPassword().isBlank() || request.getConfirmNewPassword().isEmpty()) {
                            throw new IllegalArgumentException("Password cannot be empty");
                        }
                        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
                            throw new IllegalArgumentException("Passwords don't match");
                        }
                        // Hash the new password
                        String hashedPassword = hashPassword(request.getNewPassword());
                        // Persist to DB
                        userAuthRepository.updateUserAuthPassword(request.getUserId(), hashedPassword); // pseudo code, replace with your DAO/Repository call
                    }
                });
    }

    @Override
    public UserBizResult<String> updateUserInfo(UpdateUserInfoRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.UPDATE_USER_INFO,
                new UserBizCallback<>() {

                    @Override
                    protected UserBizResult<String> createDefaultResponse() {
                        return new UserBizResult<>();
                    }

                    @Override
                    protected void checkParams(UpdateUserInfoRequest request) {

                    }

                    @Override
                    protected void process(UpdateUserInfoRequest request, UserBizResult<String> response) {

                        // 1. Fetch user by userId
                        UserInfo userInfo = userInfoRepository.queryUserInfoByUserId(request.getUserId());
                        if (userInfo == null) {
                            throw new RuntimeException("User not found for userId=" + request.getUserId());
                        }

                        // update user name
                        userInfoRepository.updateUserName(request.getUserId(), request.getUserName());
                    }
                });
    }

    @Override
    public UserBizResult<UserInfoItem> queryUserInfoByUserId(QueryUserInfoRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.QUERY_USER_INFO,
                new UserBizCallback<>() {
                    @Override
                    protected UserBizResult<UserInfoItem> createDefaultResponse() {
                        return new UserBizResult<>();
                    }

                    @Override
                    protected void checkParams(QueryUserInfoRequest request) {
                        UserRequestChecker.checkQueryUserInfoByUserIdRequest(request);
                    }

                    @Override
                    protected void process(QueryUserInfoRequest request, UserBizResult<UserInfoItem> response) {
                        UserInfo userInfo = userInfoRepository.queryUserInfoByUserId(request.getUserId());
                        // convert userInfo to item
                        if (userInfo != null) {
                            UserInfoItem userInfoItem = UserInfoConvertor.convertToItem(userInfo);
                            ResponseBuilder.success(response, userInfoItem, UserActionEnum.QUERY_USER_INFO.getCode(), "Query User Info Item by userId");
                        } else {
                            ResponseBuilder.fail(response, UserActionEnum.QUERY_USER_INFO.getCode(), "Query User Info Item by userId");
                        }
                    }
                });
    }

    @Override
    public UserBizResult<String> setPasswordPin(SetPasswordPinRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.SET_PASSWORD_PIN,
                new UserBizCallback<>() {
                    @Override
                    protected UserBizResult<String> createDefaultResponse() {
                        return new UserBizResult<>();
                    }

                    @Override
                    protected void checkParams(SetPasswordPinRequest request) {
                        UserRequestChecker.checkSetPasswordPinRequest(request);
                    }

                    @Override
                    protected void process(SetPasswordPinRequest request, UserBizResult<String> response) {
                        // verify password pin is 6 digits.
                        request.validate();

                        // ensure there isnt already an existing hash.
                        UserAuth userAuth = userAuthRepository.queryUserAuth(request.getUserId(), request.getAuthType());
                        AssertUtil.notNull(userAuth, UserResultEnum.PARAM_ILLEGAL, "User Auth not exist");

                        // hash the user password
                        String hashedPassword = UserPasswordUtil.hashPassword(request.getPasswordPin());

                        // insert a new user auth.
                        userAuthRepository.insertUserAuth(new UserAuth() {{
                            setUserId(request.getUserId());
                            setAuthType(request.getAuthType());
                            setIsActive(true);
                            setLastUsed(new Date());
                            setCredentialHash(hashedPassword);
                            setGmtCreate(new Date());
                            setGmtModified(new Date());
                        }});
                    }
                });
    }


}
