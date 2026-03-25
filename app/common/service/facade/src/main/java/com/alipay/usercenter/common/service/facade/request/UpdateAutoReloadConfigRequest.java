package com.alipay.usercenter.common.service.facade.request;

import com.alipay.usercenter.common.service.facade.baseresult.UserBaseRequest;

/**
 * @author adam
 * @date 23/3/2026 3:18 PM
 */
public class UpdateAutoReloadConfigRequest extends UserBaseRequest {
    private String cardId;
    private String userId;
    private Integer thresholdAmount;
    private Integer autoReloadAmount;
    private boolean isActive;
    private String gmtModified;
}