package com.alipay.usercenter.common.service.facade.result;

import com.alipay.usercenter.common.service.facade.enums.OTPSceneEnum;

import java.time.Instant;

public class OTPResult {
    private String challengeId;
    private OTPSceneEnum sceneCode;
    private Instant expireAt;
    private int retryLeft;

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public OTPSceneEnum getSceneCode() {
        return sceneCode;
    }

    public void setSceneCode(OTPSceneEnum sceneCode) {
        this.sceneCode = sceneCode;
    }

    public Instant getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Instant expireAt) {
        this.expireAt = expireAt;
    }

    public int getRetryLeft() {
        return retryLeft;
    }

    public void setRetryLeft(int retryLeft) {
        this.retryLeft = retryLeft;
    }
}

