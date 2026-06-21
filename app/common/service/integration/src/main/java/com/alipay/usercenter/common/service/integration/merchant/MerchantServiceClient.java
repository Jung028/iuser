package com.alipay.usercenter.common.service.integration.merchant;

import com.alipay.merchant.common.service.facade.baseresult.MerchantBizResult;
import com.alipay.merchant.common.service.facade.item.MerchantInfoItem;
import com.alipay.merchant.common.service.facade.request.CreateMerchantAccountRequest;
import com.alipay.merchant.common.service.facade.result.QueryMerchantInfoRequest;

/**
 * @author adam
 * @date 21/6/2026 6:13 PM
 */
public interface MerchantServiceClient {

    MerchantBizResult<MerchantInfoItem> queryMerchantInfoByPhoneNo(QueryMerchantInfoRequest request);

    MerchantBizResult<String> createMerchantAccount(CreateMerchantAccountRequest createMerchantReq);
}