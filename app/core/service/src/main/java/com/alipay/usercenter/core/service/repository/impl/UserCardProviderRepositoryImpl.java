package com.alipay.usercenter.core.service.repository.impl;

import com.alipay.usercenter.common.dal.auto.custom.UserCardProviderDAO;
import com.alipay.usercenter.common.dal.auto.dataobject.UserCardDetailDO;
import com.alipay.usercenter.common.dal.auto.dataobject.UserCardProviderDO;
import com.alipay.usercenter.common.dal.auto.dataobject.UserInfoDO;
import com.alipay.usercenter.core.converter.UserCardDetailConvertor;
import com.alipay.usercenter.core.converter.UserCardProviderConvertor;
import com.alipay.usercenter.core.converter.UserInfoConvertor;
import com.alipay.usercenter.core.exception.RepositoryException;
import com.alipay.usercenter.core.model.UserCardDetail;
import com.alipay.usercenter.core.model.UserCardProvider;
import com.alipay.usercenter.core.model.UserInfo;
import com.alipay.usercenter.core.service.repository.AbstractUserRepository;
import com.alipay.usercenter.core.service.repository.UserCardDetailRepository;
import com.alipay.usercenter.core.service.repository.UserCardProviderRepository;
import com.alipay.usercenter.core.service.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserCardProviderRepositoryImpl extends AbstractUserRepository implements UserCardProviderRepository {

    @Autowired
    private UserCardProviderDAO userCardProviderDAO;

    @Override
    public UserCardProvider findByUserIdAndProvider(String userId, String provider) {
        try {
            UserCardProviderDO userCardProviderDO =
                    userCardProviderDAO.findByUserIdAndProvider(Long.parseLong(userId), provider);
            return UserCardProviderConvertor.convertToDomain(userCardProviderDO);
        } catch (RepositoryException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException("DB error during query user card detail list", e);
        }
    }

    @Override
    public UserCardProvider insertNewCardProvider(UserCardProvider userCardProvider) {
        return null;
    }


}
