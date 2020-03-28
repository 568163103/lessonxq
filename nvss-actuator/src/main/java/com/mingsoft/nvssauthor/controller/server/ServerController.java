package com.mingsoft.nvssauthor.controller.server;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.nvssauthor.constant.Constant;
import com.mingsoft.nvssauthor.domain.Server;
import com.mingsoft.nvssauthor.domain.ServerStatusInfo;
import com.mingsoft.nvssauthor.entiry.MicroServiceEntity;
import com.mingsoft.nvssauthor.service.ServerService;
import com.mingsoft.nvssauthor.entiry.ServerStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date 2019-11-29 16:48
 * @Version
 **/

@RestController
@RequestMapping(value = "api/v1/server")
public class ServerController {

    @Autowired
    private ServerService serverService;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 注入发送MQTT的Bean
     */
//    @Autowired
//    private IMqttSender iMqttSender;
    @PostMapping(value = "/findServerList")
    public Map<String, Object> findServerList() {
        HashMap<String, Object> result = new HashMap<>();
        List<Server> serverList = serverService.findServerList();
        result.put("data", serverList);
        result.put("code", 200);
        return result;
    }

    @PostMapping("/getServerType")
    public Map<String, Object> getServerType() {
        Map<String, Object> result = new HashMap<>();
        List<Server> serverList = serverService.findServerAndType();

        int mssCount = 0;
        int dmsCount = 0;
        int cmsCount = 0;
        int mssOnlineCount = 0;
        int dmsOnlineCount = 0;
        int cmsOnlineCount = 0;
        for (Server server : serverList) {
            if (Constant.SERVER_TYPE_MSS.equals(server.getServerTypeName())) {
                mssCount++;
                if (server.getStatus()) {
                    mssOnlineCount++;
                }
            } else if (Constant.SERVER_TYPE_DMS.equals(server.getServerTypeName())) {
                dmsCount++;
                if (server.getStatus()) {
                    dmsOnlineCount++;
                }
            } else if (Constant.SERVER_TYPE_CMS.equals(server.getServerTypeName())) {
                cmsCount++;
                if (server.getStatus()) {
                    cmsOnlineCount++;
                }
            }
        }
        ServerStatistics serverStatistics = new ServerStatistics(mssCount, dmsCount, cmsCount, mssOnlineCount, dmsOnlineCount, cmsOnlineCount);
        result.put("code", 200);
        result.put("serverStatistics", serverStatistics);
        return result;
    }


//    @PostMapping(value = "/sendServerEmq")
//    public ResponseEntity<String> sendServerEmq(@RequestParam(value = "msg") String message) {
//        iMqttSender.sendToMqtt(message);
//        return new ResponseEntity<>("OK", HttpStatus.OK);
//    }

    @PostMapping(value = "initServerStatus")
    public Map<String, Object> initServerStatus() throws ParseException {
        Map<String, Object> result = new HashMap<>();
        List<ServerStatusInfo> serverStatusInfos = serverService.initServerStatus();
        result.put("serverStatusInfos", serverStatusInfos);
        return result;

    }


    /**
     * 采集微服务状态
     */

    @PostMapping(value = "getMicroService")
    public Map<String, Object> getMicroService() {

        Map<String, Object> result = new HashMap<>();
        String param = "pod";
        String url = "http://101.200.220.47:7777/api/query/?query=" + param;
        JSONObject serverJson = restTemplate.getForEntity(url, JSONObject.class).getBody();
        if (serverJson != null) {
            MicroServiceEntity webMicroServiceEntity = new MicroServiceEntity(serverJson.getJSONObject(Constant.MicroService_WEB));
            MicroServiceEntity cmsMicroServiceEntity = new MicroServiceEntity(serverJson.getJSONObject(Constant.MicroService_CMS));
            MicroServiceEntity dmsMicroServiceEntity = new MicroServiceEntity(serverJson.getJSONObject(Constant.MicroService_DMS));
            MicroServiceEntity masterMicroServiceEntity = new MicroServiceEntity(serverJson.getJSONObject(Constant.MicroService_MASTER));
            MicroServiceEntity mssMicroServiceEntity = new MicroServiceEntity(serverJson.getJSONObject(Constant.MicroService_MSS));

            result.put("webMicroServiceEntity", webMicroServiceEntity.getTempJsonObject());
            result.put("cmsMicroServiceEntity", cmsMicroServiceEntity.getTempJsonObject());
            result.put("dmsMicroServiceEntity", dmsMicroServiceEntity.getTempJsonObject());
            result.put("masterMicroServiceEntity", masterMicroServiceEntity.getTempJsonObject());
            result.put("mssMicroServiceEntity", mssMicroServiceEntity.getTempJsonObject());

            result.put("type", "micro_service");
            result.put("code", 0);
        } else {
            result.put("code", 1);
        }
        return result;
    }


}
