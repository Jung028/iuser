package com.alipay.usercenter.biz.login;

import com.alipay.merchant.common.service.facade.baseresult.MerchantBizResult;
import com.alipay.merchant.common.service.facade.item.MerchantInfoItem;
import com.alipay.merchant.common.service.facade.result.QueryMerchantInfoRequest;
import com.alipay.usercenter.biz.user.impl.AbstractUserBizService;
import com.alipay.usercenter.common.service.facade.enums.LoginType;
import com.alipay.usercenter.core.model.UserInfo;
import org.springframework.stereotype.Component;

/**
 * @author adam
 * @date 21/6/2026 5:24 PM
 */
@Component
public class MerchantLoginHandler extends AbstractUserBizService implements LoginHandler {

    @Override
    public LoginType getType() {
        return LoginType.MERCHANT;
    }

    @Override
    public LoginContextInfo loadContext(String phoneNo) {
        QueryMerchantInfoRequest request = new QueryMerchantInfoRequest();
        request.setPhoneNo(phoneNo);
        MerchantBizResult<MerchantInfoItem> merchantInfo = merchantServiceClient.queryMerchantInfoByPhoneNo(request);
        LoginContextInfo loginContextInfo = new LoginContextInfo();
        loginContextInfo.setPhoneNo(phoneNo);
        loginContextInfo.setId(merchantInfo.getResult().getMerchantId());
        loginContextInfo.setHashedPassword(merchantInfo.getResult().getHashedPassword());
        loginContextInfo.setStatus(merchantInfo.getResult().getStatus());
        return loginContextInfo;
    }
}