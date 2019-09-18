package com.xq.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;


@FeignClient("spring-cloud-nacos-provider")
public interface ServerService {
    @GetMapping(value = "server/findAllServer",produces = "application/json;charset=utf-8")
    public Map<String,Object> findAllServer();
}
