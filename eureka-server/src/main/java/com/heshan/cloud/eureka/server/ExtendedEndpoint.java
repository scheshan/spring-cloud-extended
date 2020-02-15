package com.heshan.cloud.eureka.server;

import com.heshan.cloud.eureka.core.ExtendedResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;

/**
 * LongPollingEndpoint
 *
 * @author heshan
 * @date 2020/2/15
 */
@RequestMapping("extended")
@RestController
public class ExtendedEndpoint {

    private ClientRequestManager manager;

    private static final long DEFAULT_EPOCH = 0;

    private static final long DEFAULT_TIMEOUT = 60_1000;

    public ExtendedEndpoint(ClientRequestManager manager) {
        this.manager = manager;
    }

    @GetMapping(value = "epoch", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<ExtendedResponse> getByEpoch(
            HttpServletRequest servletRequest,
            @RequestParam(value = "epoch", required = false) Long epoch,
            @RequestParam(value = "runId", required = false) String runId,
            @RequestParam(value = "timeout", required = false) Long timeout
    ) {
        if (epoch == null) {
            epoch = DEFAULT_EPOCH;
        }
        if (timeout == null) {
            timeout = DEFAULT_TIMEOUT;
        } else {
            timeout = timeout * 1000;
        }

        String remoteAddress = getRemoteAddress(servletRequest);
        ClientRequest request = new ClientRequest(remoteAddress, runId, epoch, timeout);

        manager.add(request);
        return request.deferredResult();
    }

    private String getRemoteAddress(HttpServletRequest request) {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }
}
