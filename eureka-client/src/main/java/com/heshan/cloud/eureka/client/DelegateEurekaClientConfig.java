package com.heshan.cloud.eureka.client;

import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.shared.transport.EurekaTransportConfig;

import javax.annotation.Nullable;
import java.util.List;

/**
 * {@link DelegateEurekaClientConfig} wraps the original {@link EurekaClientConfig} and
 * mostly do the same thing, but let {@link #shouldFetchRegistry()} return false to disable fetch,
 * and instead use our own logic.
 *
 * @author heshan
 * @date 2020/2/16
 */
public class DelegateEurekaClientConfig implements EurekaClientConfig {

    private EurekaClientConfig delegate;

    public DelegateEurekaClientConfig(EurekaClientConfig delegate) {
        this.delegate = delegate;
    }

    @Override
    public int getRegistryFetchIntervalSeconds() {
        return delegate.getRegistryFetchIntervalSeconds();
    }

    @Override
    public int getInstanceInfoReplicationIntervalSeconds() {
        return delegate.getInstanceInfoReplicationIntervalSeconds();
    }

    @Override
    public int getInitialInstanceInfoReplicationIntervalSeconds() {
        return delegate.getInitialInstanceInfoReplicationIntervalSeconds();
    }

    @Override
    public int getEurekaServiceUrlPollIntervalSeconds() {
        return delegate.getEurekaServiceUrlPollIntervalSeconds();
    }

    @Override
    public String getProxyHost() {
        return delegate.getProxyHost();
    }

    @Override
    public String getProxyPort() {
        return delegate.getProxyPort();
    }

    @Override
    public String getProxyUserName() {
        return delegate.getProxyUserName();
    }

    @Override
    public String getProxyPassword() {
        return delegate.getProxyPassword();
    }

    @Override
    public boolean shouldGZipContent() {
        return delegate.shouldGZipContent();
    }

    @Override
    public int getEurekaServerReadTimeoutSeconds() {
        return delegate.getEurekaServerReadTimeoutSeconds();
    }

    @Override
    public int getEurekaServerConnectTimeoutSeconds() {
        return delegate.getEurekaServerConnectTimeoutSeconds();
    }

    @Override
    public String getBackupRegistryImpl() {
        return delegate.getBackupRegistryImpl();
    }

    @Override
    public int getEurekaServerTotalConnections() {
        return delegate.getEurekaServerTotalConnections();
    }

    @Override
    public int getEurekaServerTotalConnectionsPerHost() {
        return delegate.getEurekaServerTotalConnectionsPerHost();
    }

    @Override
    public String getEurekaServerURLContext() {
        return delegate.getEurekaServerURLContext();
    }

    @Override
    public String getEurekaServerPort() {
        return delegate.getEurekaServerPort();
    }

    @Override
    public String getEurekaServerDNSName() {
        return delegate.getEurekaServerDNSName();
    }

    @Override
    public boolean shouldUseDnsForFetchingServiceUrls() {
        return delegate.shouldUseDnsForFetchingServiceUrls();
    }

    @Override
    public boolean shouldRegisterWithEureka() {
        return delegate.shouldRegisterWithEureka();
    }

    @Override
    public boolean shouldPreferSameZoneEureka() {
        return delegate.shouldPreferSameZoneEureka();
    }

    @Override
    public boolean allowRedirects() {
        return delegate.allowRedirects();
    }

    @Override
    public boolean shouldLogDeltaDiff() {
        return delegate.shouldLogDeltaDiff();
    }

    @Override
    public boolean shouldDisableDelta() {
        return delegate.shouldDisableDelta();
    }

    @Nullable
    @Override
    public String fetchRegistryForRemoteRegions() {
        return delegate.fetchRegistryForRemoteRegions();
    }

    @Override
    public String getRegion() {
        return delegate.getRegion();
    }

    @Override
    public String[] getAvailabilityZones(String region) {
        return delegate.getAvailabilityZones(region);
    }

    @Override
    public List<String> getEurekaServerServiceUrls(String myZone) {
        return delegate.getEurekaServerServiceUrls(myZone);
    }

    @Override
    public boolean shouldFilterOnlyUpInstances() {
        return delegate.shouldFilterOnlyUpInstances();
    }

    @Override
    public int getEurekaConnectionIdleTimeoutSeconds() {
        return delegate.getEurekaConnectionIdleTimeoutSeconds();
    }

    @Override
    public boolean shouldFetchRegistry() {
        return false;
    }

    @Nullable
    @Override
    public String getRegistryRefreshSingleVipAddress() {
        return delegate.getRegistryRefreshSingleVipAddress();
    }

    @Override
    public int getHeartbeatExecutorThreadPoolSize() {
        return delegate.getHeartbeatExecutorThreadPoolSize();
    }

    @Override
    public int getHeartbeatExecutorExponentialBackOffBound() {
        return delegate.getHeartbeatExecutorExponentialBackOffBound();
    }

    @Override
    public int getCacheRefreshExecutorThreadPoolSize() {
        return delegate.getCacheRefreshExecutorThreadPoolSize();
    }

    @Override
    public int getCacheRefreshExecutorExponentialBackOffBound() {
        return delegate.getCacheRefreshExecutorExponentialBackOffBound();
    }

    @Override
    public String getDollarReplacement() {
        return delegate.getDollarReplacement();
    }

    @Override
    public String getEscapeCharReplacement() {
        return delegate.getEscapeCharReplacement();
    }

    @Override
    public boolean shouldOnDemandUpdateStatusChange() {
        return delegate.shouldOnDemandUpdateStatusChange();
    }

    @Override
    public String getEncoderName() {
        return delegate.getEncoderName();
    }

    @Override
    public String getDecoderName() {
        return delegate.getDecoderName();
    }

    @Override
    public String getClientDataAccept() {
        return delegate.getClientDataAccept();
    }

    @Override
    public String getExperimental(String name) {
        return delegate.getExperimental(name);
    }

    @Override
    public EurekaTransportConfig getTransportConfig() {
        return delegate.getTransportConfig();
    }

    public EurekaClientConfig delegate() {
        return delegate;
    }
}
