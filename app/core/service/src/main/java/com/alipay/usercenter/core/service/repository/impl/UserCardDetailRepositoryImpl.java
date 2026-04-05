package com.alipay.usercenter.core.service.repository.impl;

import com.alipay.usercenter.common.dal.auto.custom.UserCardDetailDAO;
import com.alipay.usercenter.common.dal.auto.dataobject.UserCardDetailDO;
import com.alipay.usercenter.core.converter.UserCardDetailConvertor;
import com.alipay.usercenter.core.exception.RepositoryException;
import com.alipay.usercenter.core.model.UserCardDetail;
import com.alipay.usercenter.core.service.repository.UserCardDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author adam
 * @date 24/3/2026 11:12 PM
 */
@Repository
public class UserCardDetailRepositoryImpl implements UserCardDetailRepository {

    @Autowired
    private UserCardDetailDAO userCardDetailDAO;

    @Override
    public UserCardDetail queryDefaultCard(UUID userCardId) {
        try {
            UserCardDetailDO userCardDetailDO =
                    userCardDetailDAO.queryDefaultCard(userCardId);
            return UserCardDetailConvertor.convertToDomain(userCardDetailDO);
        } catch (RepositoryException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException("DB error during query user card detail list", e);
        }
    }

    @Override
    public List<UserCardDetail> queryCardDetailList(String userId) {
        try {
            List<UserCardDetailDO> userCardDetailDOList =
                    userCardDetailDAO.queryCardDetailList(Long.parseLong(userId));
            return UserCardDetailConvertor.convertToDomain(userCardDetailDOList);
        } catch (RepositoryException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException("DB error during query user card detail list", e);
        }
    }

    @Override
    public void insertNewCard(UserCardDetail userCardDetail) {
        try {
            UserCardDetailDO userCardDetailDO = UserCardDetailConvertor.convertToDO(userCardDetail);
            int rows = userCardDetailDAO.insertNewCard(userCardDetailDO);
            if (rows < 0) {
                throw new RepositoryException("Insert new card detail failed, no record exists");
            }
        } catch (RepositoryException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException("DB error during query user card detail list", e);
        }
    }

    @Override
    public UserCardDetail findByProviderToken(String providerToken, String stripe) {
        try {
            UserCardDetailDO userCardDetailDO =
                    userCardDetailDAO.findByProviderToken(providerToken, stripe);
            return UserCardDetailConvertor.convertToDomain(userCardDetailDO);
        } catch (RepositoryException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException("DB error during query user card detail list", e);
        }
    }

}