package com.alipay.usercenter.rpc;

import com.alipay.sofa.rpc.config.JAXRSProviderManager;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

/**
 * Registers custom JAX-RS providers with the SOFA RPC REST server. SOFA reads
 * JAXRSProviderManager.getCustomProviderInstances() when it initialises the REST
 * server (RestServer.init), so registration must happen before that — a
 * @Configuration @PostConstruct runs early enough during context startup.
 */
@Configuration
public class RestProviderRegistrar {

    @PostConstruct
    public void registerRestProviders() {
        JAXRSProviderManager.registerCustomProviderInstance(new TraceIdRestFilter());
    }
}
