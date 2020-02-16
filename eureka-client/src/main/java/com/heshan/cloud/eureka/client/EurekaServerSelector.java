package com.heshan.cloud.eureka.client;

import com.netflix.discovery.shared.resolver.ClosableResolver;
import com.netflix.discovery.shared.resolver.aws.AwsEndpoint;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * EurekaServerSelector
 *
 * @author heshan
 * @date 2020/2/16
 */
public class EurekaServerSelector {

    private ClosableResolver resolver;

    private AtomicInteger index = new AtomicInteger();

    public EurekaServerSelector(ClosableResolver resolver) {
        this.resolver = resolver;
    }

    public AwsEndpoint select() {
        List<AwsEndpoint> list = resolver.getClusterEndpoints();
        return list.get(index.get() % list.size());
    }

    public void next() {
        index.incrementAndGet();
    }
}
