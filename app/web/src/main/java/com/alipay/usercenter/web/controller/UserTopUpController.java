package com.alipay.usercenter.web.controller;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.usercenter.common.service.facade.api.TopUpService;
import com.alipay.usercenter.common.service.facade.baseresult.UserBizResult;
import com.alipay.usercenter.common.service.facade.item.AutoReloadConfigItem;
import com.alipay.usercenter.common.service.facade.item.UserCardDetailItem;
import com.alipay.usercenter.common.service.facade.request.*;
import com.alipay.usercenter.common.service.facade.result.QueryCardDetailsResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author adam
 * @date 31/3/2026 7:35 PM
 */
@CrossOrigin(origins = "http://localhost:8083")
@RestController
@RequestMapping("/user/topup")
public class UserTopUpController {

    @SofaReference
    private TopUpService topUpService;

    @PostMapping("/queryCardDetails.json")
    public UserBizResult<QueryCardDetailsResult> queryCardDetails(@RequestBody QueryCardDetailsRequest request) {
        try {
            return topUpService.queryCardDetails(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/updateAutoReloadConfig.json")
    public UserBizResult<String> updateAutoReloadConfig(@RequestBody UpdateAutoReloadConfigRequest request) {
        try {
            return topUpService.updateAutoReloadConfig(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/queryAutoReloadConfig.json")
    public UserBizResult<AutoReloadConfigItem> queryAutoReloadConfig(@RequestBody QueryAutoReloadConfigRequest request) {
        try {
            return topUpService.queryAutoReloadConfig(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/createNewCard.json")
    public UserBizResult<String> createNewCard(@RequestBody CreateNewCardRequest request) {
        try {
            return topUpService.createNewCard(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/toggleAutoReloadConfig.json")
    public UserBizResult<String> toggleAutoReloadConfig(@RequestBody ToggleAutoReloadConfigRequest request) {
        try {
            return topUpService.toggleAutoReloadConfig(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/queryDefaultCard.json")
    public UserBizResult<UserCardDetailItem> queryDefaultCard(@RequestBody QueryDefaultCardRequest request) {
        try {
            return topUpService.queryDefaultCard(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}