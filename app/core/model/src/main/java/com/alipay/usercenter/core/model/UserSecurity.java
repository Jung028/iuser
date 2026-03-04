package com.alipay.usercenter.core.model;

import com.alipay.usercenter.core.enums.UserSecurityStatusEnum;

import java.time.Instant;

public class UserSecurity {
    private Long userId;
    private String status;
    private Integer failedAttempts;
    private Instant lockedUntil;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(Integer failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public Instant getLockedUntil() {
        return lockedUntil;
    }

    public void setLockedUntil(Instant lockedUntil) {
        this.lockedUntil = lockedUntil;
    }


    /** Default state when user logs in for the first time */
    public static UserSecurity newUser(Long userId) {
        UserSecurity u = new UserSecurity();
        u.userId = userId;
        u.status = UserSecurityStatusEnum.ENABLED.getCode();
        u.failedAttempts = 0;
        u.lockedUntil = null;
        return u;
    }

}
