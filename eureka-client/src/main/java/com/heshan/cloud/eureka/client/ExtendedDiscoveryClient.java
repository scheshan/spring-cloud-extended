package com.heshan.cloud.eureka.client;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.discovery.AbstractDiscoveryClientOptionalArgs;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.Applications;
import com.netflix.discovery.shared.resolver.ClosableResolver;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ExtendedDiscoveryClient
 *
 * @author heshan
 * @date 2020/2/16
 */
public class ExtendedDiscoveryClient extends CloudEurekaClient {

    private DelegateEurekaClientConfig config;

    private AtomicReference<Applications> localRegionApps;

    private RestTemplate restTemplate;

    private EurekaServerSelector selector;

    public ExtendedDiscoveryClient(
            ApplicationInfoManager manager,
            DelegateEurekaClientConfig config,
            AbstractDiscoveryClientOptionalArgs<?> args,
            ApplicationContext context
    ) {
        super(manager, config, args, context);

        this.config = config;
        restTemplate = new RestTemplate();

        try {
            init();
        } catch (Exception ex) {
            throw new RuntimeException("Failed init ExtendedDiscoveryClient", ex);
        }
    }

    public void init() throws Exception {
        Field localRegionAppsField = ReflectionUtils.findField(DiscoveryClient.class, "localRegionApps");
        ReflectionUtils.makeAccessible(localRegionAppsField);
        localRegionApps = (AtomicReference<Applications>) ReflectionUtils.getField(localRegionAppsField, this);

        Field eurekaTransportField = ReflectionUtils.findField(DiscoveryClient.class, "eurekaTransport");
        Object eurekaTransport = ReflectionUtils.getField(eurekaTransportField, this);

        Field bootstrapResolverField = ReflectionUtils.findField(eurekaTransport.getClass(), "bootstrapResolver");
        ReflectionUtils.makeAccessible(bootstrapResolverField);
        ClosableResolver resolver = (ClosableResolver) ReflectionUtils.getField(bootstrapResolverField, eurekaTransport);
        selector = new EurekaServerSelector(resolver);
    }
}
