package com.alipay.usercenter.rpc;

import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.alipay.usercenter.common.util.TraceContext;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

/**
 * Reads the X-Trace-Id HTTP header on the SOFA RPC REST (Netty/RESTEasy) path —
 * which the servlet TraceIdFilter never sees — so calls made over plain HTTP
 * (e.g. from iagent) keep their trace id instead of the provider minting a new
 * one. Sets the logging MDC and seeds RPC baggage so the id also propagates to
 * any downstream SOFA calls. Registered via {@link RestProviderRegistrar}.
 *
 * Uses javax.ws.rs (SOFA RPC bundles RESTEasy 3 / javax), not jakarta.
 */
@Provider
public class TraceIdRestFilter implements ContainerRequestFilter {

    private static final String HEADER = "X-Trace-Id";
    private static final String TRACE_ID = "traceId";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String traceId = requestContext.getHeaderString(HEADER);
        if (traceId != null && !traceId.isEmpty()) {
            TraceContext.set(traceId);
            RpcInvokeContext.getContext().putRequestBaggage(TRACE_ID, traceId);
        }
    }
}
