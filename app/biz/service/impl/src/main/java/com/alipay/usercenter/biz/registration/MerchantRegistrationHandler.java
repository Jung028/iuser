package com.alipay.usercenter.biz.registration;

import com.alipay.account_center.common.service.facade.baseresult.AccountBizResult;
import com.alipay.account_center.common.service.facade.enums.AccountTypeEnum;
import com.alipay.account_center.common.service.facade.request.CreateAccountRequest;
import com.alipay.merchant.common.service.facade.baseresult.MerchantBizResult;
import com.alipay.merchant.common.service.facade.enums.MerchantCategory;
import com.alipay.merchant.common.service.facade.item.MerchantInfoItem;
import com.alipay.merchant.common.service.facade.request.CreateMerchantAccountRequest;
import com.alipay.merchant.common.service.facade.result.QueryMerchantInfoRequest;
import com.alipay.usercenter.biz.user.impl.AbstractUserBizService;
import com.alipay.usercenter.common.service.facade.enums.AuthType;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;
import com.alipay.usercenter.common.service.facade.request.RegisterMerchantUserRequest;
import com.alipay.usercenter.common.service.facade.request.RegisterUserRequest;
import com.alipay.usercenter.core.model.UserAuth;
import com.alipay.usercenter.core.util.AssertUtil;

import java.util.Date;

import static com.alipay.usercenter.biz.util.UserPasswordUtil.hashPassword;
import static com.alipay.usercenter.common.service.facade.constant.GlobalUserConstant.DEFAULT_CURRENCY;

public class MerchantRegistrationHandler extends AbstractUserBizService implements RegistrationHandler {

    @Override
    public void validate(RegisterUserRequest request) {
        // No duplicate merchant
        QueryMerchantInfoRequest queryMerchantInfoRequest = new QueryMerchantInfoRequest();
        queryMerchantInfoRequest.setPhoneNo(request.getPhoneNo());
        MerchantBizResult<MerchantInfoItem> merchantResult = merchantServiceClient.queryMerchantInfoByPhoneNo(queryMerchantInfoRequest);
        AssertUtil.notNull(merchantResult, UserResultCode.SYSTEM_EXCEPTION, "Merchant service query failed");
        AssertUtil.isNull(merchantResult.getResult(), UserResultCode.EXISTING_USER,
                "Merchant account already exists for phone: " + request.getPhoneNo());
    }

    @Override
    public void createNewAccount(RegisterUserRequest request) {
        RegisterMerchantUserRequest merchantUserRequest = (RegisterMerchantUserRequest) request;
        String hashedPassword = hashPassword(request.getPassword());

        // Create merchant account in imerchant
        CreateMerchantAccountRequest createMerchantReq = new CreateMerchantAccountRequest();
        createMerchantReq.setMerchantCategory(MerchantCategory.valueOf(merchantUserRequest.getMerchantCategory()));
        createMerchantReq.setMerchantName(merchantUserRequest.getMerchantName());
        createMerchantReq.setPhoneNo(request.getPhoneNo());
        createMerchantReq.setHashedPassword(hashedPassword);
        MerchantBizResult<String> merchantResult = merchantServiceClient.createMerchantAccount(createMerchantReq);
        AssertUtil.isTrue(merchantResult.isSuccess(), UserResultCode.SYSTEM_EXCEPTION, "Failed to create merchant account");

        // Create wallet account
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setAccountName(merchantUserRequest.getMerchantName());
        createAccountRequest.setAccountType(AccountTypeEnum.MERCHANT_ACCOUNT);
        createAccountRequest.setUserId(merchantResult.getResult());
        createAccountRequest.setCurrency(DEFAULT_CURRENCY);
        AccountBizResult<String> createAccount = accountServiceClient.createAccount(createAccountRequest);
        AssertUtil.isTrue(createAccount.isSuccess(), UserResultCode.SYSTEM_EXCEPTION, "Failed to create account");

        // Store auth credentials
        userAuthRepository.insertUserAuth(new UserAuth() {{
            setUserId(merchantResult.getResult());
            setAuthType(AuthType.LOGIN_PASSWORD.getCode());
            setIsActive(true);
            setLastUsed(new Date());
            setCredentialHash(hashedPassword);
            setGmtCreate(new Date());
            setGmtModified(new Date());
        }});
    }
}
