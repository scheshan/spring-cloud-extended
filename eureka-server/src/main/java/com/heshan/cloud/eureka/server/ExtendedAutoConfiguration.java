package com.heshan.cloud.eureka.server;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.eureka.EurekaServerConfig;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import com.netflix.eureka.resources.ServerCodecs;
import org.springframework.cloud.netflix.eureka.server.InstanceRegistryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * ExtendedAutoConfiguration
 *
 * @author heshan
 * @date 2020/2/15
 */
@Configuration
public class ExtendedAutoConfiguration {

    @Bean
    public ExtendedEndpoint extendedEndpoint(ClientRequestManager clientRequestManager) {
        return new ExtendedEndpoint(clientRequestManager);
    }

    @Bean
    @Primary
    public PeerAwareInstanceRegistry registry(InstanceRegistryProperties instanceRegistryProperties, EurekaServerConfig serverConfig, EurekaClientConfig clientConfig, ServerCodecs serverCodecs, EurekaClient eurekaClient) {
        return new ExtendedInstanceRegistry(serverConfig, clientConfig, serverCodecs, eurekaClient, instanceRegistryProperties.getExpectedNumberOfRenewsPerMin(), instanceRegistryProperties.getDefaultOpenForTrafficCount());
    }

    @Bean
    public ClientRequestManager clientRequestManager(PeerAwareInstanceRegistry registry) {
        return new ClientRequestManager(registry);
    }
}
