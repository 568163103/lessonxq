package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 消息提供者启动类
 *
 * @author xq
 * @since 2019/9/8
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.xq.mapper")
public class SpringCloudProviderZabbixApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudProviderZabbixApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
