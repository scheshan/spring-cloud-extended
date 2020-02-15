package com.heshan.cloud.sample.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * SampleConsumerApplication
 *
 * @author heshan
 * @date 2020/2/15
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SampleConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleConsumerApplication.class, args);
    }
}
