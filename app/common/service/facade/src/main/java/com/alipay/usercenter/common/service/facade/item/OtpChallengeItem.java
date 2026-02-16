package com.alipay.usercenter.common.service.facade.item;

import com.alipay.usercenter.common.service.facade.enums.OTPSceneEnum;

import java.time.Instant;

public class OtpChallengeItem {

    private String challengeId;
    private String phoneNo;
    private OTPSceneEnum sceneCode;
    private String otpHash;
    private String hashAlgo;
    private Instant createdAt;
    private Instant expireAt;
    private Instant lockoutUntil;
    private boolean verified;
    private int retryCount;
    private int maxRetry;


    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public OTPSceneEnum getSceneCode() {
        return sceneCode;
    }

    public void setSceneCode(OTPSceneEnum sceneCode) {
        this.sceneCode = sceneCode;
    }

    public String getOtpHash() {
        return otpHash;
    }

    public void setOtpHash(String otpHash) {
        this.otpHash = otpHash;
    }

    public String getHashAlgo() {
        return hashAlgo;
    }

    public void setHashAlgo(String hashAlgo) {
        this.hashAlgo = hashAlgo;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Instant expireAt) {
        this.expireAt = expireAt;
    }

    public boolean isVerified() {
        return verified;
    }

    public Instant getLockoutUntil() {
        return lockoutUntil;
    }

    public void setLockoutUntil(Instant lockoutUntil) {
        this.lockoutUntil = lockoutUntil;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }
}
