package com.heshan.cloud.eureka.client;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClientConfig;

import java.util.List;

/**
 * EurekaServerSelector
 *
 * @author heshan
 * @date 2020/2/16
 */
public class EurekaServerSelector {

    private List<String> serverList;

    public EurekaServerSelector(EurekaClientConfig config, InstanceInfo instance) {

    }
}
