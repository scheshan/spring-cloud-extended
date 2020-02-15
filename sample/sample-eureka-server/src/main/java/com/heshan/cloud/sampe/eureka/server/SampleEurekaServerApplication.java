package com.heshan.cloud.sampe.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * SampleServerApplication
 *
 * @author heshan
 * @date 2020/2/15
 */
@SpringBootApplication
@EnableEurekaServer
public class SampleEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleEurekaServerApplication.class, args);
    }
}
