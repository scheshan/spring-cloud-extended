package com.heshan.cloud.eureka.client;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.discovery.AbstractDiscoveryClientOptionalArgs;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClientConfig;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ExtendedClientAutoConfiguration
 *
 * @author heshan
 * @date 2020/2/15
 */
@Configuration
@RibbonClients(defaultConfiguration = ExtendedRibbonClientConfiguration.class)
public class ExtendedAutoConfiguration {

    @Bean
    public DiscoveryClient extendedDiscoveryClient(
            ApplicationInfoManager manager,
            EurekaClientConfig config,
            AbstractDiscoveryClientOptionalArgs<?> args,
            ApplicationContext context
    ) {
        DelegateEurekaClientConfig delegateConfig = new DelegateEurekaClientConfig(config);

        return new ExtendedDiscoveryClient(manager, delegateConfig, args, context);
    }
}
