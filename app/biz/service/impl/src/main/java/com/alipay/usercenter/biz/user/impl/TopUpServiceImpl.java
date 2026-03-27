package com.alipay.usercenter.biz.user.impl;


import com.alipay.usercenter.biz.template.UserBizCallback;
import com.alipay.usercenter.biz.user.checker.UserRequestChecker;
import com.alipay.usercenter.biz.user.helper.ResponseBuilder;
import com.alipay.usercenter.common.service.facade.api.TopUpService;
import com.alipay.usercenter.common.service.facade.baseresult.UserBizResult;
import com.alipay.usercenter.common.service.facade.item.AutoReloadConfigItem;
import com.alipay.usercenter.common.service.facade.item.UserCardDetailItem;
import com.alipay.usercenter.common.service.facade.request.*;
import com.alipay.usercenter.common.service.facade.result.QueryCardDetailsResult;
import com.alipay.usercenter.core.converter.AutoReloadConfigConvertor;
import com.alipay.usercenter.core.converter.UserCardDetailConvertor;
import com.alipay.usercenter.core.enums.UserActionEnum;
import com.alipay.usercenter.core.model.AutoReloadConfig;
import com.alipay.usercenter.core.model.UserCardDetail;

import java.util.List;


/**
 * @author adam
 * @date 23/3/2026 3:23 PM
 */
public class TopUpServiceImpl extends AbstractUserBizService implements TopUpService {

    @Override
    public UserBizResult<QueryCardDetailsResult> queryCardDetails(QueryCardDetailsRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.QUERY_CARD_DETAILS,
                new UserBizCallback<>() {


                    @Override
                    protected UserBizResult<QueryCardDetailsResult> createDefaultResponse() {
                        return new UserBizResult<>();
                    }

                    @Override
                    protected void checkParams(QueryCardDetailsRequest request) {
                        UserRequestChecker.checkQueryCardDetailsRequest(request);
                    }

                    @Override
                    protected void process(QueryCardDetailsRequest request, UserBizResult<QueryCardDetailsResult> response) {
                        List<UserCardDetail> userCardDetail = userCardDetailRepository.queryCardDetailList(request.getUserId());
                        List<UserCardDetailItem> userCardDetailItemList = UserCardDetailConvertor.convertToItem(userCardDetail);
                        QueryCardDetailsResult queryCardDetailsResult = new QueryCardDetailsResult();
                        queryCardDetailsResult.setCardDetailsList(userCardDetailItemList);
                        ResponseBuilder.success(response, queryCardDetailsResult, UserActionEnum.QUERY_CARD_DETAILS.getCode(), UserActionEnum.QUERY_CARD_DETAILS.getDesc());
                    }
                });
    }

    @Override
    public UserBizResult<String> updateAutoReloadConfig(UpdateAutoReloadConfigRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.UPDATE_AUTO_RELOAD_CONFIG,
                new UserBizCallback<>() {
                    @Override
                    protected UserBizResult<String> createDefaultResponse() {
                        return new UserBizResult<>();
                    }

                    @Override
                    protected void checkParams(UpdateAutoReloadConfigRequest request) {
                        UserRequestChecker.checkUpdateAutoReloadConfigRequest(request);
                    }

                    @Override
                    protected void process(UpdateAutoReloadConfigRequest request, UserBizResult<String> response) {
                        // update threshold amount, auto-reload amount and which card to auto-relaod

                        autoReloadConfigRepository.updateAutoReloadConfig(request);
                    }
                });
    }

    @Override
    public UserBizResult<AutoReloadConfigItem> queryAutoReloadConfig(QueryAutoReloadConfigRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.QUERY_AUTO_RELOAD_CONFIG,
                new UserBizCallback<>() {

                    @Override
                    protected UserBizResult<AutoReloadConfigItem> createDefaultResponse() {
                        return new UserBizResult<>();
                    }

                    @Override
                    protected void checkParams(QueryAutoReloadConfigRequest request) {
                        UserRequestChecker.checkQueryAutoReloadConfigRequest(request);
                    }

                    @Override
                    protected void process(QueryAutoReloadConfigRequest request, UserBizResult<AutoReloadConfigItem> response) {
                        AutoReloadConfig autoReloadConfig = autoReloadConfigRepository.queryAutoReloadConfig(request.getUserId());
                        AutoReloadConfigItem item = AutoReloadConfigConvertor.convertToItem(autoReloadConfig);
                        ResponseBuilder.success(response, item, UserActionEnum.QUERY_AUTO_RELOAD_CONFIG.getCode(),
                                UserActionEnum.QUERY_AUTO_RELOAD_CONFIG.getDesc());
                    }
                });
    }


    @Override
    public UserBizResult<String> insertNewCard(InsertNewCardRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.INSERT_NEW_CARD,
                new UserBizCallback<>() {
                    @Override
                    protected UserBizResult<String> createDefaultResponse() {
                        return new UserBizResult<>();
                    }

                    @Override
                    protected void checkParams(InsertNewCardRequest request) {
                        UserRequestChecker.checkInsertNewCardRequest(request);

                    }

                    @Override
                    protected void process(InsertNewCardRequest request, UserBizResult<String> response) {
                        userCardDetailRepository.insertNewCard(request);
                    }
                });
    }

    @Override
    public UserBizResult<String> toggleAutoReloadConfig(ToggleAutoReloadConfigRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.TOGGLE_AUTO_RELOAD,
                new UserBizCallback<>() {
                    @Override
                    protected UserBizResult<String> createDefaultResponse() {
                        return new UserBizResult<>();
                    }

                    @Override
                    protected void checkParams(ToggleAutoReloadConfigRequest request) {
                        UserRequestChecker.checkToggleAutoReloadConfigRequest(request);
                    }

                    @Override
                    protected void process(ToggleAutoReloadConfigRequest request, UserBizResult<String> response) {
                        userCardDetailRepository.toggleAutoReloadConfig(request.isAutoReload());
                    }
                });
    }

    @Override
    public UserBizResult<UserCardDetailItem> queryDefaultCard(QueryDefaultCardRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.QUERY_DEFAULT_CARD,
                new UserBizCallback<>() {
                    @Override
                    protected UserBizResult<UserCardDetailItem> createDefaultResponse() {
                        return new UserBizResult<>();
                    }

                    @Override
                    protected void checkParams(QueryDefaultCardRequest request) {
                        UserRequestChecker.checkQueryDefaultCardRequest(request);
                    }

                    @Override
                    protected void process(QueryDefaultCardRequest request, UserBizResult<UserCardDetailItem> response) {
                        // query user_card_details where userId and isDefault is true
                        UserCardDetail userCardDetail = userCardDetailRepository.queryDefaultCard(request.getUserId());
                        ResponseBuilder.success(response, UserCardDetailConvertor.convertToItem(userCardDetail),
                                UserActionEnum.QUERY_DEFAULT_CARD.getCode(),
                                UserActionEnum.QUERY_DEFAULT_CARD.getDesc());

                    }
                });
    }


}