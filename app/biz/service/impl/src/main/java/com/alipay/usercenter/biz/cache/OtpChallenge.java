package com.alipay.usercenter.biz.cache;

import com.alipay.usercenter.common.service.facade.enums.OTPSceneEnum;
import com.alipay.usercenter.common.service.facade.item.OtpChallengeItem;
import com.alipay.usercenter.common.service.facade.item.OtpVerifiedClaims;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Adam
 */
@Component
public interface OtpChallenge {

    /**
     * query OTP
     * @param challengeId
     */
    OtpChallengeItem queryOTP(String challengeId);

    /**
     * create challenge
     * @param phoneNo
     * @param scene
     */
    OtpChallengeItem createAndStoreChallenge(String phoneNo, OTPSceneEnum scene);

    /**
     * update challenge
     * @param challenge
     */
    void update(OtpChallengeItem challenge);

    /**
     * issue JWT token for OTP challenge
     * @param challenge
     */
    String issueJWTToken(OtpChallengeItem challenge);

    /**
     * verify JWT token for OTP challenge
     * @param verifiedToken
     */
    OtpVerifiedClaims verifyVerifiedToken(String verifiedToken);
}
