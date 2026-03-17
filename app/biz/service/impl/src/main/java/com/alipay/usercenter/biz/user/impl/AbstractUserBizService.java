package com.alipay.usercenter.biz.user.impl;


import com.alipay.usercenter.biz.cache.OtpChallenge;
import com.alipay.usercenter.biz.cache.UserSecurityCache;
import com.alipay.usercenter.biz.jwt.JwtUtil;
import com.alipay.usercenter.biz.template.UserServiceTemplate;
import com.alipay.usercenter.common.service.integration.account.AccountServiceClient;
import com.alipay.usercenter.core.service.repository.UserAuthRepository;
import com.alipay.usercenter.core.service.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

public abstract class AbstractUserBizService {
    /**
     * Logger
     */
    protected static Logger LOGGER = LoggerFactory.getLogger(AbstractUserBizService.class);

    /**
     * userServiceTemplate
     */
    @Autowired
    protected UserServiceTemplate userServiceTemplate;

    /**
     * userInfoRepository
     */
    @Autowired
    protected UserInfoRepository userInfoRepository;

    /**
     * userAuthRepository
     */
    @Autowired
    protected UserAuthRepository userAuthRepository;

    /**
     * userSecurityCache
     */
    @Autowired
    protected UserSecurityCache userSecurityCache;

    /**
     * otpChallenge
     */
    @Autowired
    protected OtpChallenge otpChallenge;

    /**
     * userTransactionTemplate
     */
    @Autowired
    protected TransactionTemplate userTransactionTemplate;

    /**
     * jwt token util
     */
    @Autowired
    protected JwtUtil jwtUtil;

    /**
     * account service client
     */
    @Autowired
    protected AccountServiceClient accountServiceClient;

    /**
     * setUserServiceTemplate
     * @param userServiceTemplate
     */
    public void setUserServiceTemplate(UserServiceTemplate userServiceTemplate) {
        this.userServiceTemplate = userServiceTemplate;
    }

    /**
     * setUserInfoRepository
     * @param userInfoRepository
     */
    public void setUserInfoRepository(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    /**
     * setUserSecurityCache
     * @param userSecurityCache
     */
    public void setUserSecurityCache(UserSecurityCache userSecurityCache) {
        this.userSecurityCache = userSecurityCache;
    }

    /**
     * setOtpChallenge
     * @param otpChallenge
     */
    public void setOtpChallenge(OtpChallenge otpChallenge) {
        this.otpChallenge = otpChallenge;
    }

    /**
     * setUserTransactionTemplate
     * @param userTransactionTemplate
     */
    public void setUserTransactionTemplate(TransactionTemplate userTransactionTemplate) {
        this.userTransactionTemplate = userTransactionTemplate;
    }

    /**
     * set jwt util
     * @param jwtUtil
     */
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
}
