package com.alipay.usercenter.biz.login;

import com.alipay.usercenter.biz.user.helper.ResponseBuilder;
import com.alipay.usercenter.biz.user.impl.AbstractUserBizService;
import com.alipay.usercenter.core.enums.UserActionEnum;
import com.alipay.usercenter.core.model.UserInfo;

/**
 * @author adam
 * @date 21/6/2026 5:24 PM
 */
public class UserLoginHandler extends AbstractUserBizService implements LoginHandler {

    @Override
    public LoginContextInfo loadContext(String phoneNo) {
        UserInfo userInfo = userInfoRepository.queryUserInfo(phoneNo);
        LoginContextInfo loginContextInfo = new LoginContextInfo();
        loginContextInfo.setPhoneNo(phoneNo);
        loginContextInfo.setId(userInfo.getUserId().toString());
        loginContextInfo.setHashedPassword(userInfo.getHashedPassword());
        loginContextInfo.setStatus(userInfo.getStatus());
        return loginContextInfo;
    }
}