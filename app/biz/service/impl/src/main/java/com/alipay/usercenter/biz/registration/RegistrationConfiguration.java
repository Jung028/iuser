package com.alipay.usercenter.biz.registration;

import com.alipay.usercenter.common.service.facade.enums.RegistrationType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class RegistrationConfiguration {

    @Bean
    public UserRegistrationHandler userRegistrationHandler() {
        return new UserRegistrationHandler();
    }

    @Bean
    public MerchantRegistrationHandler merchantRegistrationHandler() {
        return new MerchantRegistrationHandler();
    }

    @Bean
    public RegistrationFactory registrationFactory(
            UserRegistrationHandler userRegistrationHandler,
            MerchantRegistrationHandler merchantRegistrationHandler) {
        Map<RegistrationType, RegistrationHandler> map = new EnumMap<>(RegistrationType.class);
        map.put(RegistrationType.USER, userRegistrationHandler);
        map.put(RegistrationType.MERCHANT, merchantRegistrationHandler);
        return new RegistrationFactory(map);
    }
}
