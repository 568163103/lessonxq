package com.xq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date
 * @Version
 **/
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.xq.mapper")
public class WxPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(WxPayApplication.class, args);
    }
}
