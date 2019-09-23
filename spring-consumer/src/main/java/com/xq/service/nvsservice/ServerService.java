package com.xq.service.nvsservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;


@FeignClient("spring-cloud-nacos-provider")
public interface ServerService {
    @PostMapping(value = "server/findAllServer",produces = "application/json;charset=utf-8")
    public Map<String,Object> findAllServer();


    @PostMapping(value = "server/findAllServerType", produces = "application/json;charset=utf-8")
    public Map<String, Object> findAllServerType();
}
