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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getThresholdAmount() {
        return thresholdAmount;
    }

    public void setThresholdAmount(Integer thresholdAmount) {
        this.thresholdAmount = thresholdAmount;
    }

    public Integer getAutoReloadAmount() {
        return autoReloadAmount;
    }

    public void setAutoReloadAmount(Integer autoReloadAmount) {
        this.autoReloadAmount = autoReloadAmount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }
}