package com.alipay.usercenter.common.service.integration.account;

import com.alipay.account_center.common.service.facade.baseresult.AccountBizResult;
import com.alipay.account_center.common.service.facade.item.AccountInfoItem;
import com.alipay.account_center.common.service.facade.item.TransactionRecordItem;
import com.alipay.account_center.common.service.facade.request.*;

import com.alipay.usercenter.common.service.facade.enums.UserResultCode;
import com.alipay.usercenter.common.service.integration.AbstractServiceClient;
import com.alipay.usercenter.core.util.AssertUtil;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceClientImpl extends AbstractServiceClient implements AccountServiceClient {


    @Override
    public AccountBizResult<String> createAccount(CreateAccountRequest request) {
        AssertUtil.notNull(request, UserResultCode.PARAM_ILLEGAL, "Create account request cannot be null");
        AssertUtil.notBlank(request.getAccountName(), UserResultCode.PARAM_ILLEGAL, "account name cannot be blank");
        AssertUtil.notBlank(request.getAccountType().getCode(), UserResultCode.PARAM_ILLEGAL, "account type cannot be blank");
        AssertUtil.notBlank(request.getCurrency(), UserResultCode.PARAM_ILLEGAL, "currency cannot be blank");
        AssertUtil.notBlank(request.getUserId(), UserResultCode.PARAM_ILLEGAL, "userId cannot be blank");
        // set cross invoke
        AccountBizResult<String> result = accountService.createAccount(request);
        AssertUtil.notNull(result, UserResultCode.PARAM_ILLEGAL, ", result is null");
        AssertUtil.isTrue(result.isSuccess(), UserResultCode.PARAM_ILLEGAL, ", result is not success");
        return result;
    }

    @Override
    public AccountBizResult<AccountInfoItem> queryAccountInfoByUserId(QueryAccountInfoRequest request) {
        AssertUtil.notNull(request, UserResultCode.PARAM_ILLEGAL, "Create account request cannot be null");
         AssertUtil.notBlank(request.getUserId(), UserResultCode.PARAM_ILLEGAL, "userId cannot be blank");
        // set cross invoke
        AccountBizResult<AccountInfoItem> result = accountService.queryAccountInfoByUserId(request);
        System.out.println(result.getResult().getAccountId());
        System.out.println(result.getResult().getAccountRelationId());
        AssertUtil.notNull(result, UserResultCode.PARAM_ILLEGAL, ", result is null");
        AssertUtil.isTrue(result.isSuccess(), UserResultCode.PARAM_ILLEGAL, ", result is not success");
        return result;
    }

}
