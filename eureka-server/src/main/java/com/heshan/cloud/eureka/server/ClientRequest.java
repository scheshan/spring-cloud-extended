package com.heshan.cloud.eureka.server;

import com.heshan.cloud.eureka.core.ExtendedResponse;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * ClientRequest
 *
 * @author heshan
 * @date 2020/2/15
 */
public class ClientRequest {

    private String remoteAddress;

    private String runId;

    private long epoch;

    private long timeout;

    private DeferredResult<ExtendedResponse> deferredResult;

    public ClientRequest(String remoteAddress, String runId, long epoch, long timeout) {
        this.remoteAddress = remoteAddress;
        this.runId = runId;
        this.epoch = epoch;
        this.timeout = timeout;

        this.deferredResult = new DeferredResult<>(timeout);
    }

    public DeferredResult<ExtendedResponse> deferredResult() {
        return this.deferredResult;
    }

    public long epoch() {
        return this.epoch;
    }

    public String runId() {
        return this.runId;
    }

    public String remoteAddress() {
        return this.remoteAddress;
    }

    public long timeout() {
        return this.timeout;
    }
}
