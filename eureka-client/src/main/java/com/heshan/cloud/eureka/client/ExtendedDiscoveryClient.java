package com.heshan.cloud.eureka.client;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.discovery.AbstractDiscoveryClientOptionalArgs;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.context.ApplicationContext;

/**
 * ExtendedDiscoveryClient
 *
 * @author heshan
 * @date 2020/2/16
 */
public class ExtendedDiscoveryClient extends CloudEurekaClient {

    public ExtendedDiscoveryClient(
            ApplicationInfoManager manager,
            DelegateEurekaClientConfig config,
            AbstractDiscoveryClientOptionalArgs<?> args,
            ApplicationContext context
    ) {
        super(manager, config, args, context);
    }
}
