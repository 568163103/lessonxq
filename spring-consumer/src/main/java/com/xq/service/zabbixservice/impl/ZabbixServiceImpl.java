package com.xq.service.zabbixservice.impl;

import com.xq.service.zabbixservice.ZabbixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("alarm")
public class ZabbixServiceImpl {
    @Autowired
    private ZabbixService zabbixService;

    @PostMapping(value = "/alarmInfoList", produces = "application/json;charset=utf-8")
    public Map<String, Object> alarmInfoList() {
        return zabbixService.alarmInfoList();
    }
}
