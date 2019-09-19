package com.xq.service.nvsservice.impl;

import com.xq.service.nvsservice.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author xq
 */
@RestController
@RequestMapping("server")
public class ServerServiceImpl {
    @Autowired
    private ServerService serverService;

    @GetMapping(value = "/findAllServer",produces = "application/json;charset=utf-8")
    public Map<String,Object> findAllServer(){
        return serverService.findAllServer();
    }

}
