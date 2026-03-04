package com.alipay.usercenter.biz.user.helper;

import com.alipay.usercenter.core.enums.UserSecurityStatusEnum;
import com.alipay.usercenter.core.model.UserSecurity;

import java.time.Instant;
import java.util.Date;

import static com.alipay.usercenter.common.service.facade.constant.GlobalUserConstant.MAX_FAILED_ATTEMPTS;

public class BusinessServiceHelper {

    /**
     * Calculate and decide lockout time based on failed login attempts.
     *
     * @param failedAttempts Number of failed login attempts
     * @param userSecurity   UserSecurity object to update lockout status
     */
    public static void calculateNDecideLockoutTime(int failedAttempts, UserSecurity userSecurity) {
        if (failedAttempts == MAX_FAILED_ATTEMPTS) { // 5th
            long lockoutDuration = 15 * 60 * 1000; // 15 min
            userSecurity.setLockedUntil(Instant.now().plusMillis(lockoutDuration));
            userSecurity.setStatus(UserSecurityStatusEnum.TIMEOUT_LOCK.getCode());
        } else if (failedAttempts == MAX_FAILED_ATTEMPTS + 1) { // 6th
            long lockoutDuration = 30 * 60 * 1000; // 30 min
            userSecurity.setLockedUntil(Instant.now().plusMillis(lockoutDuration));
            userSecurity.setStatus(UserSecurityStatusEnum.TIMEOUT_LOCK.getCode());
        } else if (failedAttempts >= MAX_FAILED_ATTEMPTS + 2) { // 7th
            userSecurity.setStatus(UserSecurityStatusEnum.PERMANENT_LOCK.name());
        }
    }

}
