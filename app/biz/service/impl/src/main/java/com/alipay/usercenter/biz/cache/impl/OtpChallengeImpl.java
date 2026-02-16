package com.alipay.usercenter.biz.cache.impl;

import com.alipay.usercenter.biz.cache.OtpChallenge;
import com.alipay.usercenter.biz.jwt.JwtTokenUtil;
import com.alipay.usercenter.common.service.facade.enums.OTPSceneEnum;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;
import com.alipay.usercenter.common.service.facade.exception.UserBizException;
import com.alipay.usercenter.common.service.facade.item.OtpChallengeItem;
import com.alipay.usercenter.common.service.facade.item.OtpVerifiedClaims;
import com.alipay.usercenter.core.util.AssertUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * OtpChallenge implementation.
 *
 * @author Adam
 * @version 1.0
 * @since 2026-01-03
 */
public class OtpChallengeImpl implements OtpChallenge {

    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRE_SECONDS = 300; // 5 minutes
    private static final int MAX_RETRY = 3;
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.ES256);


    private RedisTemplate<String, OtpChallengeItem> otpChallengeItemRedisTemplate;
    private RedisTemplate<String, Boolean> otpVerifiedClaimsRedisTemplate;

    @Override
    public OtpChallengeItem queryOTP(String challengeId) {
        // fetch from Redis
        AssertUtil.notBlank(challengeId, UserResultCode.PARAM_ILLEGAL, "challengeId cannot be blank");
        String redisKey = "otp:challenge:" + challengeId;
        // retrieve the challenge item from redis
        OtpChallengeItem challengeItem = otpChallengeItemRedisTemplate.opsForValue().get(redisKey);
        if (challengeItem == null) {
            return null;
        }
        // if challenge item is expired, delete it and return null
        if(Instant.now().isAfter(challengeItem.getExpireAt())) {
            otpChallengeItemRedisTemplate.delete(redisKey);
            return null;
        }
        return challengeItem;
    }

    @Override
    public OtpChallengeItem createAndStoreChallenge(String phoneNo, OTPSceneEnum scene) {

        // generate OTP of length 6
        String otp = generateOTP(OTP_LENGTH);
        // hash using bcrypt and salt of the otp
        String otpHash = BCrypt.hashpw(otp, BCrypt.gensalt());
        // build the challenge object
        OtpChallengeItem challenge = new OtpChallengeItem();
        challenge.setChallengeId(UUID.randomUUID().toString());
        challenge.setPhoneNo(phoneNo);
        challenge.setSceneCode(scene);
        challenge.setOtpHash(otpHash);
        challenge.setHashAlgo("BCRYPT");
        challenge.setCreatedAt(Instant.now());
        challenge.setExpireAt(Instant.now().plusSeconds(OTP_EXPIRE_SECONDS));
        challenge.setVerified(false);
        challenge.setRetryCount(0);
        challenge.setMaxRetry(MAX_RETRY);

        //Store with redis cache with expiry
        update(challenge);

        //send otp via sms to phone number
        sendOtp(phoneNo, otp);

        return challenge;
    }



    @Override
    public void update(OtpChallengeItem challenge) {
        // Update the challenge in Redis
        String redisKey = "otp:challenge:" + challenge.getChallengeId();
        otpChallengeItemRedisTemplate.opsForValue()
                .set(redisKey, challenge, OTP_EXPIRE_SECONDS, TimeUnit.SECONDS);
    }


    @Override
    public String issueJWTToken(OtpChallengeItem challenge) {
        // generate JWT token (mocked here)
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        return jwtTokenUtil.generateTokenForOtpChallenge(challenge);
    }

    @Override
    public OtpVerifiedClaims verifyVerifiedToken(String verifiedToken) {
        // assert token is not blank
        AssertUtil.notBlank(verifiedToken, UserResultCode.PARAM_ILLEGAL, "Verified token cannot be blank");

        // get claims from JWT
        Claims claims;
        try {
             claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(verifiedToken)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new UserBizException(UserResultCode.OTP_VERIFIED_TOKEN_EXPIRED.getCode(), "OTP verified token has expired");
        } catch (Exception e) {
            throw new UserBizException(UserResultCode.OTP_VERIFIED_TOKEN_INVALID.getCode(), "OTP verified token is invalid");
        }
        // verify purpose of JWT
        String subject = claims.getSubject();
        if (!subject.equals("otp_verification")) {
            throw new UserBizException(UserResultCode.OTP_VERIFIED_TOKEN_INVALID.getCode(), "OTP verified token is invalid");
        }

        //extract claims challengeId, phoneNo, scene, then assert
        String challengeId = claims.get("challengeId", String.class);
        String phoneNo = claims.get("phoneNo", String.class);
        String scene = claims.get("scene", String.class);

        // verify claims are not blank
        AssertUtil.notBlank(challengeId, UserResultCode.PARAM_ILLEGAL, "challengeId in token cannot be blank");
        AssertUtil.notBlank(phoneNo, UserResultCode.PARAM_ILLEGAL, "phoneNo in token cannot be blank");
        AssertUtil.notBlank(scene, UserResultCode.PARAM_ILLEGAL, "scene in token cannot be blank");

        // replay protection
        String replayKey = "otp:verify" + challengeId;
        // set the key to true with expiry if the key is absent, else will return false and is asserted.
        // this is to prevent the same otp verify code from being used multiple times.
        Boolean firstUse = otpVerifiedClaimsRedisTemplate.opsForValue().setIfAbsent(replayKey, true, 5, TimeUnit.MINUTES);
        assert firstUse != null;
        if (firstUse.equals(Boolean.FALSE)) {
            throw new UserBizException(UserResultCode.OTP_VERIFIED_TOKEN_INVALID.getCode(), "OTP verified token has already been used");
        }

        //build and return the claims.
        OtpVerifiedClaims verifiedClaims = new OtpVerifiedClaims();
        verifiedClaims.setChallengeId(challengeId);
        verifiedClaims.setPhoneNo(phoneNo);
        verifiedClaims.setScene(scene);
        verifiedClaims.setVerifiedAt(claims.getIssuedAt().toInstant());

        return verifiedClaims;
    }



    /**
     * Simulate sending OTP via SMS
     * @param phoneNo
     * @param otp
     */
    private void sendOtp(String phoneNo, String otp) {
        //TODO: use sms service to send OTP
        System.out.println("Sending OTP " + otp + " to phone number " + phoneNo);
    }


    /**
     * Generate a 6-digit OTP
     * @return the generated OTP as a String
     */
    public static String generateOTP(int otpLength) {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
