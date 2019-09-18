package com.xq.controller;

import com.xq.domain.Server;
import com.xq.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * {@link ServerService}
 * @author xq
 * @since 2019/09/18
 */
@RestController
@RequestMapping("server")
public class ServerController {
    @Autowired
    private ServerService serverService;
    @GetMapping(value = "/findAllServer",produces = "application/json;charset=utf-8")
    public Map<String,Object> findAllServer(){
        return serverService.findServerAll();
    }
}
