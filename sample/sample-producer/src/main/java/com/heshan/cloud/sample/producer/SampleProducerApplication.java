package com.heshan.cloud.sample.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * SampleProducerApplication
 *
 * @author heshan
 * @date 2020/2/15
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SampleProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleProducerApplication.class, args);
    }
}
