package com.heshan.cloud.eureka.client;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.discovery.AbstractDiscoveryClientOptionalArgs;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.Applications;
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
            Field localRegionAppsField = ReflectionUtils.findField(DiscoveryClient.class, "localRegionApps");
            ReflectionUtils.makeAccessible(localRegionAppsField);
            localRegionApps = (AtomicReference<Applications>) ReflectionUtils.getField(localRegionAppsField, this);
        } catch (Exception ex) {
            throw new RuntimeException("Cannot access localRegionApps field", ex);
        }
    }
}
