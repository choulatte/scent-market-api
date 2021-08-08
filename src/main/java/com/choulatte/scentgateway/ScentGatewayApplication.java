package com.choulatte.scentgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ScentGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScentGatewayApplication.class, args);
    }

}
