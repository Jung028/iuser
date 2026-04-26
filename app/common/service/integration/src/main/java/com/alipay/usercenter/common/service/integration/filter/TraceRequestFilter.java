package com.alipay.usercenter.common.service.integration.filter;

import com.alipay.usercenter.common.util.TraceContext;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class TraceRequestFilter implements ClientRequestFilter {

    private static final String HEADER_NAME = "X-Trace-Id";

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        String traceId = TraceContext.get();
        if (traceId != null) {
            requestContext.getHeaders().add(HEADER_NAME, traceId);
        }
    }
}