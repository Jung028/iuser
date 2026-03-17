package com.alipay.usercenter.common.service.integration.account;


import com.alipay.account_center.common.service.facade.baseresult.AccountBizResult;
import com.alipay.account_center.common.service.facade.item.AccountInfoItem;
import com.alipay.account_center.common.service.facade.request.CreateAccountRequest;
import com.alipay.account_center.common.service.facade.request.QueryAccountInfoRequest;
import org.springframework.stereotype.Service;

@Service
public interface AccountServiceClient {

    AccountBizResult<String> createAccount(CreateAccountRequest request);

    AccountBizResult<AccountInfoItem> queryAccountInfoByUserId(QueryAccountInfoRequest request);

}
