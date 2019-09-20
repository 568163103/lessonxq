package com.xq.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xq.domain.Server;
import com.xq.domain.ServerType;
import com.xq.service.ServerService;
import com.xq.service.ServerTypeService;
import com.xq.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link ServerService}
 *
 * @author xq
 * @since 2019/09/18
 */
@RestController
@RequestMapping("server")
public class ServerController {
    @Autowired
    private ServerService serverService;
    @Autowired
    private ServerTypeService serverTypeService;

    @GetMapping(value = "/findAllServer", produces = "application/json;charset=utf-8")
    public Map<String, Object> findAllServer() {
        Map<String, Object> result = new HashMap<>(16);
        List<Server> serverList = serverService.findServerAll();
        result.put("serverList", serverList);

        return result;
    }

    @GetMapping(value = "/findAllServerType", produces = "application/json;charset=utf-8")
    public Map<String, Object> findAllServerType() {
        Map<String, Object> result = new HashMap<>(16);
        List<ServerType> serverTypeList  = serverTypeService.findServeTypeAll();
        result.put("serverTypeList", serverTypeList);
        return result;
    }
}
