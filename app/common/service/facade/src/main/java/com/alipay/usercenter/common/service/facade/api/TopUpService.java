package com.alipay.usercenter.common.service.facade.api;

import com.alipay.usercenter.common.service.facade.baseresult.UserBizResult;
import com.alipay.usercenter.common.service.facade.item.AutoReloadConfigItem;
import com.alipay.usercenter.common.service.facade.item.UserCardDetailItem;
import com.alipay.usercenter.common.service.facade.request.*;
import com.alipay.usercenter.common.service.facade.result.QueryCardDetailsResult;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author adam
 * @date 23/3/2026 3:25 PM
 */
@Path("/topUpService")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface TopUpService {

    /**
     * query card details
     * @param request
     * @return
     */
    @POST
    @Path("/queryCardDetails")
    UserBizResult<QueryCardDetailsResult> queryCardDetails(QueryCardDetailsRequest request);

    /**
     * update auto reload configuration
     * @param request
     * @return
     */
    @POST
    @Path("/updateAutoReloadConfig")
    UserBizResult<String> updateAutoReloadConfig(UpdateAutoReloadConfigRequest request);

    /**
     * query auto reload config
     * @param request
     * @return
     */
    @POST
    @Path("/queryAutoReloadConfig")
    UserBizResult<AutoReloadConfigItem> queryAutoReloadConfig(QueryAutoReloadConfigRequest request);

    /**
     * insert new card
     * @param request
     * @return
     */
    @POST
    @Path("/createNewCard")
    UserBizResult<String> createNewCard(CreateNewCardRequest request);

    /**
     * toggle auto reload config
     * @param request
     * @return
     */
    @POST
    @Path("/toggleAutoReloadConfig")
    UserBizResult<String> toggleAutoReloadConfig(ToggleAutoReloadConfigRequest request);

    /**
     * query default card
     * @param request
     * @return
     */
    @POST
    @Path("/queryDefaultCard")
    UserBizResult<UserCardDetailItem> queryDefaultCard(QueryDefaultCardRequest request);

}