package com.alipay.usercenter.biz.template;

import com.alipay.usercenter.biz.helper.UserResultHelper;
import com.alipay.usercenter.common.service.facade.baseresult.UserBaseRequest;
import com.alipay.usercenter.common.service.facade.baseresult.UserBaseResult;
import com.alipay.usercenter.common.service.facade.enums.UserResultCode;
import com.alipay.usercenter.common.util.LogUtil;
import com.alipay.usercenter.common.service.facade.constant.LoggerConstant;
import com.alipay.usercenter.common.util.TenantUtil;
import com.alipay.usercenter.common.util.enums.IpayTenantEnum;
import com.alipay.usercenter.core.context.IdigitalriskContextHolder;
import com.alipay.usercenter.core.enums.UserActionEnum;
import com.alipay.usercenter.common.util.EventContext;

import com.alipay.usercenter.core.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class UserServiceTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerConstant.USER_BIZ_SERVICE_LOG);

    /**
     * execute
     *
     * @param request digital risk request
     * @param action digital risk action
     * @param callback digital risk biz callback
     * @return digital risk result
     */
    public <T extends UserBaseRequest, R extends UserBaseResult> R execute(
            final T request,
            final UserActionEnum action,
            final UserBizCallback<T, R> callback) {

        R result = callback.createDefaultResponse();

        LogUtil.info(LOGGER, "service request[", request, "]");

        try {
            callback.checkParams(request);

            initContext(action, request);

            callback.process(request, result);

            UserResultHelper.fillSuccessResultCode(result);

        } catch (UserException e) {

            LogUtil.warn(LOGGER, e, "service process exception[", request, "]", ", code = "
                    , e.getResultCode(), ", msg= ", e.getMessage());

            UserResultHelper.fillExceptionResultCode(result, e.getResultCode());

        } catch (Throwable e) {
            LogUtil.error(LOGGER, e, "service process unexpected exception[", request, "]");

            UserResultHelper.fillExceptionResultCode(result, UserResultCode.SYSTEM_EXCEPTION);

        } finally {
            printDigestLog(result);

            LogUtil.info(LOGGER, "service result[" , result , "] [request =", request, "]" );

            IdigitalriskContextHolder.clear();
        }


        return result;
    }

    private <R extends UserBaseResult> void printDigestLog(R result) {
    }

    private <T extends UserBaseRequest, R extends UserBaseResult> void initContext(UserActionEnum action, T request) {
        EventContext context = TenantUtil.getCurrentEventContext();
        context.setTntInstId(IpayTenantEnum.IPAY_SG.getTntInstId());
        TenantUtil.setCurrentEventContext(context);
       // IdigitalriskContextHolder.set(action, slipExtraDAO.updateAndGetSystemDate(),request.getOperatorId(), request.getOperatorName());

    }
}
