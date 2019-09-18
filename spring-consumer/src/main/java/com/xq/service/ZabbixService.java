package com.xq.service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("spring-cloud-nacos-provider")
public interface ZabbixService {
    
}
