package com.mingsoft.nvssauthor.controller.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.nvssauthor.constant.Constant;
import com.mingsoft.nvssauthor.domain.Host;
import com.mingsoft.nvssauthor.domain.NextCodeNum;
import com.mingsoft.nvssauthor.domain.Server;
import com.mingsoft.nvssauthor.domain.ServerStatusInfo;
import com.mingsoft.nvssauthor.entiry.MicroServiceEntity;
import com.mingsoft.nvssauthor.mapper.HostMapper;
import com.mingsoft.nvssauthor.mapper.NextCodeNumMapper;
import com.mingsoft.nvssauthor.service.ServerService;
import com.mingsoft.nvssauthor.entiry.ServerStatistics;
import com.mingsoft.nvssauthor.utils.FormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Value("${prometheus.ip}")
    private String prometheusIp;
    @Value("${prometheus.port}")
    private String prometheusPort;
    @Autowired
    private NextCodeNumMapper nextCodeNumMapper;

    @Autowired
    private HostMapper hostMapper;


    private static DecimalFormat df = new DecimalFormat("0.00");


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

    /**
     * 获取服务器类型
     * @return
     */
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

        JSONObject serverJson = restTemplate.getForEntity(getPrometheusUrl("query", param), JSONObject.class).getBody();
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

    @PostMapping("/saveHostInfo")
    public String saveHostInfo(@RequestParam(value = "wsStatus", required = false) String wsStatus) {
        String param = "node";
        List<String> keys = new ArrayList<>();
        JSONObject serverJson = restTemplate.getForEntity(getPrometheusUrl("query", param), JSONObject.class).getBody();

        List<String> onLineServerKey = JSONObject.parseArray(serverJson.getJSONArray("online_node_list").toJSONString(), String.class);
        List<String> offlineNodeList = JSONObject.parseArray(serverJson.getJSONArray("offline_node_list").toJSONString(), String.class);
        keys.addAll(onLineServerKey);
        keys.addAll(offlineNodeList);
        param = "cpu";
        JSONObject cpuJson = restTemplate.getForEntity(getPrometheusUrl("query", param), JSONObject.class).getBody();
        JSONObject cpuJSObject = cpuJson.getJSONObject("cpu_cores");

        param = "memory";
        JSONObject memoryJson = restTemplate.getForEntity(getPrometheusUrl("query", param), JSONObject.class).getBody();
        JSONObject memoryJSObject = memoryJson.getJSONObject("memory_total");

        for (String key : keys) {
            Host host = new Host();
            NextCodeNum nextCodeNum = nextCodeNumMapper.selectByPrimaryKey("mss");
            String serverId = "22000" + "20020000" + nextCodeNum.getNum();
            String tempId = null;
            if (serverId.length() <= 14) {
                tempId = "22000" + "2002000000" + serverId.substring(13, 14);
            } else if (serverId.length() == 15) {
                tempId = "22000" + "200200000" + serverId.substring(13, 15);
            } else if (serverId.length() >= 16) {
                tempId = "22000" + "20020000" + serverId.substring(13, 16);
            }

            if (onLineServerKey.contains(key)) {
                host.setStatus(1);
            }

            host.setId(tempId);
            System.out.println(host.getId());
            nextCodeNum.setNum(nextCodeNum.getNum() + 1);
            nextCodeNumMapper.updateByPrimaryKey(nextCodeNum);
            host.setIp1(key.substring(0, 14));


            host.setCpuCoreCount(cpuJSObject.getLong(key));
            host.setMemoryCount(memoryJSObject.getLong(key) / 1024 / 1024 / 1024);
            Date date = new Date();
            host.setInsertionTime(FormatUtils.forMatYearMonthYearHMS(date));
            host.setUpdateTime(FormatUtils.forMatYearMonthYearHMS(date));
            host.setServerPosition("北京南站");
            hostMapper.insertSelective(host);
        }
        return "OK";
    }


    @PostMapping(value = "/getServersInfo")
    public Map<String, Object> getServersInfo() {
        Map<String, Object> result = new HashMap<>();
        List<Host> hosts = hostMapper.findAllServerInfo();
        if (hosts != null) {

            String param = "node";
            JSONObject serverJson = restTemplate.getForEntity(getPrometheusUrl("query", param), JSONObject.class).getBody();
            JSONArray onlineJSONArray = serverJson.getJSONArray("online_node_list");
            JSONArray offJSONArray = serverJson.getJSONArray("offline_node_list");
            //判断服务器在线还是离线
            if (onlineJSONArray != null && offJSONArray != null) {
                serverService.updateServerStatus(onlineJSONArray, offJSONArray, hosts);
            }

            result.put("hosts", hosts);
            result.put("code", 200);
        } else {
            result.put("code", 500);
        }
        return result;
    }


    @PostMapping(value = "/findByServerInfo")
    public Map<String, Object> findByServerInfo(@RequestParam(value = "ip1") String ip1) {
        Map<String, Object> result = new HashMap<>();
        String param = null;
        param = "cpu";
        JSONObject cpuJson = restTemplate.getForEntity(getPrometheusUrl("query", param), JSONObject.class).getBody();
        if (cpuJson != null) {
            Double cpuIdleTotal = cpuJson.getJSONObject("cpu_idle_total").getDouble(ip1 + ":9100");
            result.put("cpuIdleTotal", df.format(cpuIdleTotal / 100));
        }

        param = "memory";
        JSONObject memoryJson = restTemplate.getForEntity(getPrometheusUrl("query", param), JSONObject.class).getBody();
        if (memoryJson != null) {
            Double memoryUsageRate = memoryJson.getJSONObject("memory_usage_rate").getDouble(ip1 + ":9100");
            result.put("memoryUsageRate", df.format(memoryUsageRate / 100));
        }
        param = "filesystem";
        JSONObject filesystemJson = restTemplate.getForEntity(getPrometheusUrl("query", param), JSONObject.class).getBody();
        if (filesystemJson != null) {
            JSONArray fileJsonArray = filesystemJson.getJSONArray("filesystem_avail_rate");
            if (fileJsonArray.size() > 0) {
                for (int i = 0; i < fileJsonArray.size(); i++) {
                    if (ip1.equals(fileJsonArray.getJSONObject(i).getString("ip"))) {
                        JSONArray subJsonArray = fileJsonArray.getJSONObject(i).getJSONArray("list");

                        for (int i1 = 0; i1 < subJsonArray.size(); i1++) {
                            if (subJsonArray.getJSONObject(i1).getString("name").equals("/")) {
                                result.put("rootUse", df.format(subJsonArray.getJSONObject(i1).getDouble("size")));
                            } else if (subJsonArray.getJSONObject(i1).getString("name").equals("home")) {
                                result.put("homeUse", df.format(subJsonArray.getJSONObject(i1).getDouble("size")));
                            }
                        }

                    }
                }
            }
        }
        return result;
    }

    @RequestMapping(value = "alarmInfos")
    public String getAlarmInfos(@RequestBody String jsonString){
        System.out.println(jsonString);
        return "ok";
    }


    private String getPrometheusUrl(String key, String value) {
        return "http://" + prometheusIp + ":" + prometheusPort + "/api/query/?" + key + "=" + value;
    }

    public static void main(String[] args) {
        String str = "172.17.166.142:9100";
        System.out.println(str.substring(0, 14));
    }


}
