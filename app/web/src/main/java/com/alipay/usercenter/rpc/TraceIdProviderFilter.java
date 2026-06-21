package com.alipay.usercenter.rpc;

import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.ext.Extension;
import com.alipay.sofa.rpc.filter.AutoActive;
import com.alipay.sofa.rpc.filter.Filter;
import com.alipay.sofa.rpc.filter.FilterInvoker;
import com.alipay.usercenter.common.util.TraceContext;

import java.util.UUID;

/**
 * Provider-side SOFA RPC filter that restores the caller's traceId into the
 * logging MDC for RPC (bolt/rest) calls, so logback's %X{traceId} is populated
 * for cross-service requests. Reads "traceId" from RPC baggage set by the caller.
 */
@Extension("traceIdProvider")
@AutoActive(providerSide = true)
public class TraceIdProviderFilter extends Filter {

    private static final String TRACE_ID = "traceId";

    @Override
    public SofaResponse invoke(FilterInvoker invoker, SofaRequest request) throws SofaRpcException {
        try {
            // SOFA→SOFA carries the id in baggage. For HTTP→REST entries (e.g. iagent),
            // TraceIdRestFilter has already put it in MDC from the X-Trace-Id header —
            // fall back to that before minting a fresh id, so we don't clobber it.
            String traceId = RpcInvokeContext.getContext().getRequestBaggage(TRACE_ID);
            if (traceId == null || traceId.isEmpty()) {
                traceId = TraceContext.get();
            }
            if (traceId == null || traceId.isEmpty()) {
                traceId = UUID.randomUUID().toString();
            }
            TraceContext.set(traceId);
            return invoker.invoke(request);
        } finally {
            TraceContext.clear();
        }
    }
}
