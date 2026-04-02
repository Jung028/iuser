package com.alipay.usercenter.biz.user.impl;


import com.alipay.account_center.common.service.facade.api.AccountService;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.alipay.usercenter.biz.constant.GlobalBizConstant;
import com.alipay.usercenter.biz.template.UserBizCallback;
import com.alipay.usercenter.biz.user.checker.UserRequestChecker;
import com.alipay.usercenter.biz.user.helper.ResponseBuilder;
import com.alipay.usercenter.common.service.facade.api.TopUpService;
import com.alipay.usercenter.common.service.facade.baseresult.UserBizResult;
import com.alipay.usercenter.common.service.facade.config.ExtInfo;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;
import com.alipay.usercenter.common.service.facade.item.AutoReloadConfigItem;
import com.alipay.usercenter.common.service.facade.item.UserCardDetailItem;
import com.alipay.usercenter.common.service.facade.item.UserInfoItem;
import com.alipay.usercenter.common.service.facade.request.*;
import com.alipay.usercenter.common.service.facade.result.QueryCardDetailsResult;
import com.alipay.usercenter.core.converter.AutoReloadConfigConvertor;
import com.alipay.usercenter.core.converter.UserCardDetailConvertor;
import com.alipay.usercenter.core.enums.UserActionEnum;
import com.alipay.usercenter.core.model.AutoReloadConfig;
import com.alipay.usercenter.core.model.UserCardDetail;
import com.alipay.usercenter.core.model.UserCardProvider;
import com.alipay.usercenter.core.model.UserInfo;
import com.alipay.usercenter.core.util.AssertUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentMethodAttachParams;
import com.stripe.param.PaymentMethodCreateParams;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.alipay.usercenter.biz.constant.GlobalBizConstant.*;


/**
 * @author adam
 * @date 23/3/2026 3:23 PM
 */
@Service
@SofaService(
        interfaceType = TopUpService.class,
        uniqueId = "",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "rest")
        }
)
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
                        UserCardDetail userCardDetail = userCardDetailRepository.queryDefaultCard(request.getUserId());
                        AssertUtil.notNull(userCardDetail, UserResultCode.PARAM_ILLEGAL, "user default card not exist");

                        //check auto-reload amount must be more than threshold amount.
                        AssertUtil.isTrue(request.getAutoReloadAmount() > request.getThresholdAmount(),
                                UserResultCode.PARAM_ILLEGAL, "auto reload amount should be more than threshold amount");

                        // assert threshold amount not exceeding specific amount, and the auto-reload amount
                        AssertUtil.isTrue(request.getThresholdAmount() <= 1000 && request.getThresholdAmount() >= 0,
                                UserResultCode.PARAM_ILLEGAL, "threshold amount should be between 0 and 1000");
                        AssertUtil.isTrue(request.getAutoReloadAmount() <= 1000 && request.getAutoReloadAmount() >= 0,
                                UserResultCode.PARAM_ILLEGAL, "auto reload amount should be between 0 and 1000");
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
                        AutoReloadConfig autoReloadConfig = autoReloadConfigRepository.queryAutoReloadConfig(Long.parseLong(request.getUserId()));
                        AutoReloadConfigItem item = AutoReloadConfigConvertor.convertToItem(autoReloadConfig);
                        ResponseBuilder.success(response, item, UserActionEnum.QUERY_AUTO_RELOAD_CONFIG.getCode(),
                                UserActionEnum.QUERY_AUTO_RELOAD_CONFIG.getDesc());
                    }
                });
    }


    @Override
    public UserBizResult<String> createNewCard(CreateNewCardRequest request) {
        return userServiceTemplate.execute(request, UserActionEnum.INSERT_NEW_CARD,
                new UserBizCallback<>() {
                    @Override
                    protected UserBizResult<String> createDefaultResponse() {
                        return new UserBizResult<>();
                    }

                    @Override
                    protected void checkParams(CreateNewCardRequest request) {
                        UserRequestChecker.checkCreateNewCardRequest(request);

                    }

                    @Override
                    protected void process(CreateNewCardRequest request, UserBizResult<String> response) {
                       // cvv, expiry, thats it. then the rest is to be extracted internally and inserted in database.

                        try {
                            // getOrCreateStripeCustomer
                            String stripeCustomerId = getOrCreateStripeCustomer(request.getUserId());

                            // retrieve the payment method id created by frontend after processing card details
                            PaymentMethod pm = PaymentMethod.retrieve(request.getPaymentMethodId());
                            // attach payment method to customer stripe id
                            pm.attach(PaymentMethodAttachParams.builder()
                                    .setCustomer(stripeCustomerId).build());

                            // if default card, update default payment method to the card id
                            if (request.isSaveCard()) {
                                Customer customer = Customer.retrieve(stripeCustomerId);
                                customer.update(
                                        CustomerUpdateParams.builder()
                                                .setInvoiceSettings(CustomerUpdateParams.InvoiceSettings.builder()
                                                        .setDefaultPaymentMethod(pm.getId()).build())
                                                .build()
                                );
                            }

                            UserCardDetail userCardDetail = new UserCardDetail();
                            // pm values
                            userCardDetail.setProviderCustomerId(stripeCustomerId);
                            userCardDetail.setCardType(pm.getCard().getBrand());
                            userCardDetail.setLastFour(pm.getCard().getLast4());
                            userCardDetail.setExpiryMonth(pm.getCard().getExpMonth().shortValue());
                            userCardDetail.setExpiryYear(pm.getCard().getExpYear().shortValue());

                            // default values
                            userCardDetail.setUserCardId(UUID.randomUUID().toString());
                            userCardDetail.setUserId(Long.parseLong(request.getUserId()));
                            userCardDetail.setCardNetwork(null);
                            userCardDetail.setProvider(STRIPE);
                            userCardDetail.setProviderToken(pm.getId());
                            userCardDetail.setCardholderName(request.getCardHolderName());
                            userCardDetail.setIsDefault(false);
                            userCardDetail.setStatus(ACTIVE);
                            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
                            userCardDetail.setGmtCreate(now);
                            userCardDetail.setGmtModified(now);

                            userCardDetailRepository.insertNewCard(userCardDetail);
                        } catch (StripeException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
    }

    private String createCustomer(String userId) throws StripeException {
        CustomerCreateParams params = CustomerCreateParams.builder()
                .putMetadata("userId", userId)
                .build();

        RequestOptions options = RequestOptions.builder()
                .setIdempotencyKey("create_customer_" + userId)
                .build();

        Customer customer = Customer.create(params, options);

        return customer.getId(); // cus_xxx

    }

    /**
     * return or insert new stripe customer id
     * @param userId
     * @return
     * @throws StripeException
     * @throws JsonProcessingException
     */
    private String getOrCreateStripeCustomer(String userId) throws StripeException {
        UserCardProvider userCardProvider = userCardProviderRepository.findByUserIdAndProvider(userId, STRIPE);
        if (userCardProvider == null) {
            String customerId = createCustomer(userId);

            UserCardProvider newProvider = new UserCardProvider();
            newProvider.setUserId(Long.parseLong(userId));
            newProvider.setProvider(STRIPE);
            newProvider.setProviderCustomerId(customerId);
            userCardProvider = userCardProviderRepository.insertNewCardProvider(newProvider);
        }
        return userCardProvider.getProviderCustomerId();
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
                        autoReloadConfigRepository.toggleAutoReloadConfig(request.getUserId(), request.isActive());;
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