package com.alipay.usercenter.common.service.facade.api;

import com.alipay.usercenter.common.service.facade.baseresult.UserBizResult;
import com.alipay.usercenter.common.service.facade.item.AutoReloadConfigItem;
import com.alipay.usercenter.common.service.facade.item.UserCardDetailItem;
import com.alipay.usercenter.common.service.facade.request.*;
import com.alipay.usercenter.common.service.facade.result.QueryCardDetailsResult;

/**
 * @author adam
 * @date 23/3/2026 3:25 PM
 */
public interface TopUpService {

    UserBizResult<QueryCardDetailsResult> queryCardDetails(QueryCardDetailsRequest request);

    UserBizResult<String> updateAutoReloadConfig(UpdateAutoReloadConfigRequest request);

    UserBizResult<AutoReloadConfigItem> queryAutoReloadConfig(QueryAutoReloadConfigRequest request);

    UserBizResult<String> insertNewCard(InsertNewCardRequest request);

    UserBizResult<String> toggleAutoReloadConfig(ToggleAutoReloadConfigRequest request);

    UserBizResult<UserCardDetailItem> queryDefaultCard(QueryDefaultCardRequest request);

}