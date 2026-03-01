package com.alipay.usercenter.biz.user.impl;


import com.alipay.usercenter.biz.cache.OtpChallenge;
import com.alipay.usercenter.biz.cache.UserSecurityCache;
import com.alipay.usercenter.biz.jwt.JwtTokenUtil;
import com.alipay.usercenter.biz.template.UserServiceTemplate;
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
     * userSecurityCache
     */
    @Autowired
    protected UserSecurityCache userSecurityCache;

    /**
     * otpChallenge
     */
    protected OtpChallenge otpChallenge;

    /**
     * userTransactionTemplate
     */
    protected TransactionTemplate userTransactionTemplate;

    /**
     * jwt token util
     */
    protected JwtTokenUtil jwtTokenUtil;

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
     * setJwtTokenUtil
     * @param jwtTokenUtil
     */
    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }
}
