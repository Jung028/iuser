package com.alipay.usercenter.biz.registration;

import com.alipay.account_center.common.service.facade.baseresult.AccountBizResult;
import com.alipay.account_center.common.service.facade.enums.AccountTypeEnum;
import com.alipay.account_center.common.service.facade.request.CreateAccountRequest;
import com.alipay.usercenter.biz.helper.GenerateUserId;
import com.alipay.usercenter.biz.user.impl.AbstractUserBizService;
import com.alipay.usercenter.common.service.facade.enums.AuthType;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;
import com.alipay.usercenter.common.service.facade.request.RegisterUserRequest;
import com.alipay.usercenter.core.enums.UserAccountStatusEnum;
import com.alipay.usercenter.core.model.UserAuth;
import com.alipay.usercenter.core.model.UserInfo;
import com.alipay.usercenter.core.util.AssertUtil;

import java.util.Date;

import static com.alipay.usercenter.biz.util.UserPasswordUtil.hashPassword;
import static com.alipay.usercenter.common.service.facade.constant.GlobalUserConstant.DEFAULT_CURRENCY;

/**
 * @author adam
 * @date 21/6/2026 1:12 PM
 */
public class UserRegistrationHandler extends AbstractUserBizService implements RegistrationHandler {
    @Override
    public void validate(RegisterUserRequest request) {
        // check if there is already an existing account for this phone no
        UserInfo existingUser = userInfoRepository.queryUserInfo(
                request.getPhoneNo());
        AssertUtil.isNull(existingUser, UserResultCode.EXISTING_USER,
                "user account already exists, " + request.getPhoneNo());
    }

    @Override
    public void createNewAccount(RegisterUserRequest request) {

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
    }
}