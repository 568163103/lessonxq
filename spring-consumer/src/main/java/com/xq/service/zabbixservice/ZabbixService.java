package com.xq.service.zabbixservice;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("spring-cloud-nacos-provider")
public interface ZabbixService {
    
}
