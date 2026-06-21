package com.alipay.usercenter.common.service.integration.merchant;

import com.alipay.merchant.common.service.facade.baseresult.MerchantBizResult;
import com.alipay.merchant.common.service.facade.enums.MerchantResultCode;
import com.alipay.merchant.common.service.facade.item.MerchantInfoItem;
import com.alipay.merchant.common.service.facade.request.CreateMerchantAccountRequest;
import com.alipay.merchant.common.service.facade.result.QueryMerchantInfoRequest;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;
import com.alipay.usercenter.common.service.integration.AbstractServiceClient;
import com.alipay.usercenter.core.util.AssertUtil;

/**
 * @author adam
 * @date 21/6/2026 6:13 PM
 */
public class MerchantServiceClientImpl extends AbstractServiceClient implements MerchantServiceClient {

    @Override
    public MerchantBizResult<MerchantInfoItem> queryMerchantInfoByPhoneNo(QueryMerchantInfoRequest request) {
        AssertUtil.notNull(request, UserResultCode.PARAM_ILLEGAL, "QueryMerchantInfoRequest cannot be null");
        AssertUtil.notBlank(request.getMerchantId(), UserResultCode.PARAM_ILLEGAL, "merchant id cannot be blank");k");
        AssertUtil.notBlank(request.getPhoneNo(), UserResultCode.PARAM_ILLEGAL, "phone no cannot be blank");

        MerchantBizResult<MerchantInfoItem> result = merchantService.queryMerchantInfoByPhoneNo(request);
        AssertUtil.notNull(result, UserResultCode.PARAM_ILLEGAL, ", result is null");
        AssertUtil.notNull(result.getResult(), UserResultCode.PARAM_ILLEGAL, ", result is null");
        return result;
    }

    @Override
    public MerchantBizResult<String> createMerchantAccount(CreateMerchantAccountRequest createMerchantAccountRequest) {
        AssertUtil.notNull(createMerchantAccountRequest, UserResultCode.PARAM_ILLEGAL, "createMerchantAccountRequest cannot be null");
        AssertUtil.notNull(createMerchantAccountRequest.getMerchantName(), UserResultCode.PARAM_ILLEGAL, "merchant name cannot be null");
        AssertUtil.notNull(createMerchantAccountRequest.getMerchantCategory(), UserResultCode.PARAM_ILLEGAL, "merchant category cannot be null");
        AssertUtil.notNull(createMerchantAccountRequest.getPhoneNo(), UserResultCode.PARAM_ILLEGAL, "merchant phoneNo cannot be null");
        AssertUtil.notNull(createMerchantAccountRequest.getHashedPassword(), UserResultCode.PARAM_ILLEGAL, "merchant hashed password cannot be null");

        MerchantBizResult<String> result = merchantService.createMerchantAccount(createMerchantAccountRequest);
        AssertUtil.notNull(result, UserResultCode.PARAM_ILLEGAL, ", result is null");
        AssertUtil.notNull(result.getResult(), UserResultCode.PARAM_ILLEGAL, ", result is null");
        return result;
    }
}