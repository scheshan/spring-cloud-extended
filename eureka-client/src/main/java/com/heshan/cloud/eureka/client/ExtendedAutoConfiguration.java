package com.heshan.cloud.eureka.client;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
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
}
