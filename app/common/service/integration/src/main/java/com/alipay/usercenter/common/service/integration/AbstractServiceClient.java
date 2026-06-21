package com.alipay.usercenter.common.service.integration;

import com.alipay.account_center.common.service.facade.api.AccountService;
import com.alipay.merchant.common.service.facade.api.MerchantService;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import org.springframework.stereotype.Service;

@Service
public class AbstractServiceClient {

    /**
     * account service
     */
    @SofaReference(interfaceType = AccountService.class,
            binding = @SofaReferenceBinding(bindingType = "rest", directUrl = "http://127.0.0.1:8341"),
            jvmFirst = true)
    protected AccountService accountService;

    /**
     * merchant service
     */
    @SofaReference(interfaceType = MerchantService.class,
            binding = @SofaReferenceBinding(bindingType = "rest", directUrl = "http://127.0.0.1:8345"),
            jvmFirst = true)
    protected MerchantService merchantService;

}
