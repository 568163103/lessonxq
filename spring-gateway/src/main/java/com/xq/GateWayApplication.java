package com.xq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * {@link EnableDiscoveryClient }
 * @author xq
 * @descriptions 网关启动类
 * @since 2019/9/8
 */
@EnableDiscoveryClient
@SpringBootApplication
public class  GateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }
}
