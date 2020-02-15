package com.heshan.cloud.eureka.server;

import com.heshan.cloud.eureka.core.ExtendedResponse;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LongPollingEndpoint
 *
 * @author heshan
 * @date 2020/2/15
 */
@RequestMapping("extended")
@RestController
public class ExtendedEndpoint {

    private PeerAwareInstanceRegistry registry;

    public ExtendedEndpoint(PeerAwareInstanceRegistry registry) {
        this.registry = registry;
    }

    @GetMapping(value = "epoch", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ExtendedResponse getByEpoch() {
        ExtendedResponse response = new ExtendedResponse();
        response.setEpoch(1);
        response.setRunId("aa");
        response.setApplications(registry.getApplications().getRegisteredApplications());

        return response;
    }
}
