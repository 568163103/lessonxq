package com.xq;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudLidongProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudLidongProviderApplication.class,args);
    }
}
