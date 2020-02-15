package com.heshan.cloud.eureka.server;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.eureka.EurekaServerConfig;
import com.netflix.eureka.resources.ServerCodecs;
import org.springframework.beans.BeansException;
import org.springframework.cloud.netflix.eureka.server.InstanceRegistry;
import org.springframework.context.ApplicationContext;

/**
 * ExtendedInstanceRegistry
 *
 * @author heshan
 * @date 2020/2/15
 */
public class ExtendedInstanceRegistry extends InstanceRegistry {

    private ApplicationContext context;

    public ExtendedInstanceRegistry(
            EurekaServerConfig serverConfig,
            EurekaClientConfig clientConfig,
            ServerCodecs serverCodecs,
            EurekaClient eurekaClient,
            int expectedNumberOfRenewsPerMin,
            int defaultOpenForTrafficCount) {
        super(serverConfig, clientConfig, serverCodecs, eurekaClient, expectedNumberOfRenewsPerMin, defaultOpenForTrafficCount);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        super.setApplicationContext(context);
        this.context = context;
    }

    @Override
    public void register(InstanceInfo info, int leaseDuration, boolean isReplication) {
        super.register(info, leaseDuration, isReplication);

        InstanceRegistryEvent e = new InstanceRegistryEvent(this, InstanceRegistryEvent.Type.REGISTER, info);
        context.publishEvent(e);
    }

    @Override
    public void register(InstanceInfo info, boolean isReplication) {
        super.register(info, isReplication);

        InstanceRegistryEvent e = new InstanceRegistryEvent(this, InstanceRegistryEvent.Type.REGISTER, info);
        context.publishEvent(e);
    }

    @Override
    protected boolean internalCancel(String appName, String id, boolean isReplication) {
        boolean success = super.internalCancel(appName, id, isReplication);

        if (success) {
            InstanceRegistryEvent e = new InstanceRegistryEvent(this, InstanceRegistryEvent.Type.UNREGISTER, appName, id);
            context.publishEvent(e);
        }

        return success;
    }
}
