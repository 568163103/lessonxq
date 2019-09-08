package com.xq;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 消息提供者启动类
 * @author xq
 * @since 2019/9/8
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudLidongProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudLidongProviderApplication.class, args);
    }
}
