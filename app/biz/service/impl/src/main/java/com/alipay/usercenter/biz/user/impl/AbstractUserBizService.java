package com.alipay.usercenter.biz.user.impl;


import com.alipay.usercenter.biz.cache.OtpChallenge;
import com.alipay.usercenter.biz.cache.UserSecurityCache;
import com.alipay.usercenter.biz.jwt.JwtUtil;
import com.alipay.usercenter.biz.template.UserServiceTemplate;
import com.alipay.usercenter.common.service.integration.account.AccountServiceClient;
import com.alipay.usercenter.core.service.repository.*;
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
     * userCardRepository
     */
    @Autowired
    protected UserCardDetailRepository userCardDetailRepository;

    /**
     * userCardProviderRepository
     */
    @Autowired
    protected UserCardProviderRepository userCardProviderRepository;

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
     * AutoReloadConfigRepository
     */
    @Autowired
    protected AutoReloadConfigRepository autoReloadConfigRepository;

}
