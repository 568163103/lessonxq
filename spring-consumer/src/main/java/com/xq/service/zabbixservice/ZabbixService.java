package com.xq.service.zabbixservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient("spring-cloud-nacos-provider")
public interface ZabbixService {

    @PostMapping(value = "alarm/alarmInfoList",produces = "application/json;charset=utf-8")
    public Map<String,Object> alarmInfoList();

    
}
