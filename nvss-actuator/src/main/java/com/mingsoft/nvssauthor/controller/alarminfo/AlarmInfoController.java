package com.mingsoft.nvssauthor.controller.alarminfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.nvssauthor.constant.Constant;
import com.mingsoft.nvssauthor.domain.AlarmCloudStorage;
import com.mingsoft.nvssauthor.domain.AlarmCpuInfo;
import com.mingsoft.nvssauthor.domain.AlarmDiskInfo;
import com.mingsoft.nvssauthor.domain.CpuMemoryInfo;
import com.mingsoft.nvssauthor.mapper.AlarmCloudStorageMapper;
import com.mingsoft.nvssauthor.mapper.AlarmCpuInfoMapper;
import com.mingsoft.nvssauthor.mapper.AlarmDiskInfoMapper;
import com.mingsoft.nvssauthor.service.ServerService;
import com.mingsoft.nvssauthor.utils.SnmpUtil;
import org.apache.commons.lang3.StringUtils;
import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("api/v1/alarm")
public class AlarmInfoController {
    @Autowired
    private AlarmCloudStorageMapper alarmCloudStorageMapper;
    @Autowired
    private AlarmCpuInfoMapper alarmCpuInfoMapper;
    @Autowired
    private AlarmDiskInfoMapper diskInfoMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ServerService serverService;

    @RequestMapping(value = "/alarmInfo", method = RequestMethod.POST)
    public void alarmInfo(@RequestParam(value = "ip", required = false) String ip,
                          @RequestParam(value = "oids", required = false) String[] oids
    ) throws IOException, InterruptedException {
        Map<String, Object> result = new HashMap<>(16);
//        ip = "10.10.24.201";
////        HttpUtils.post("", JSON.toJSONString("{}"));
//
//        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
//        oids = new String[]{Constant.CPU_USAGE_RATE, Constant.CPU_TEMPERATURE};
//        CommunityTarget target = new CommunityTarget();
//        target.setCommunity(new OctetString("private"));
//        target.setVersion(SnmpConstants.version2c);
//        target.setAddress(new UdpAddress(ip + "/161"));
//        target.setTimeout(3000);
//        target.setRetries(1);
//        snmp.listen();
//        while (true) {
//            TimeUnit.SECONDS.sleep(5);
//            SnmpUtil.sendCpuAsyncRequest(snmp, SnmpUtil.createGetPduList(new String[]{Constant.CPU_USAGE_RATE, Constant.CPU_TEMPERATURE}), target);
//        }

    }

    @RequestMapping(value = "getAlarmInfo", method = RequestMethod.GET)
    public Map<String, Object> getAlarmInfo(@RequestParam(value = "type", required = false) String type,
                                            @RequestParam(value = "level", required = false, defaultValue = "1") int level,
                                            @RequestParam(value = "ip", required = false) String ip,
                                            @RequestParam(value = "content", required = false) String content,
                                            @RequestParam(value = "list", required = false) String list
    ) throws IOException {
        long beginTime = System.currentTimeMillis();
        Map<String, Object> result = new HashMap<>(16);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isEmpty(content)) {
            switch (type) {
                case Constant.ALARM_TYPE_DD:
                    Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
                    snmp.listen();

                    CommunityTarget target = new CommunityTarget();
                    target.setCommunity(new OctetString("private"));
                    target.setVersion(SnmpConstants.version2c);
                    target.setAddress(new UdpAddress(ip + "/161"));
                    target.setTimeout(10000);
                    target.setRetries(1);
                    System.out.println("type==" + type + "level==" + level + "ip===" + ip);
                    Vector<? extends VariableBinding> vbs = SnmpUtil.sendRequest(snmp, SnmpUtil.createGetPdu(Constant.DISK_PULL_ALARM), target);
                    if (vbs != null) {
                        AlarmCloudStorage alarmCloudStorage = new AlarmCloudStorage();
                        alarmCloudStorage.setIp(ip);
                        alarmCloudStorage.setCurrentTime(simpleDateFormat.format(new Date()));
                        alarmCloudStorage.setLevel(level);
                        alarmCloudStorage.setType(type);
                        alarmCloudStorage.setContent(vbs.get(0).toValueString());
                        int count = alarmCloudStorageMapper.insertSelective(alarmCloudStorage);
                        result.put("count", count);

                    }
                    break;

            }
        } else {
            AlarmCloudStorage alarmCloudStorage = new AlarmCloudStorage();
            alarmCloudStorage.setIp(ip);
            alarmCloudStorage.setCurrentTime(simpleDateFormat.format(new Date()));
            alarmCloudStorage.setLevel(level);
            alarmCloudStorage.setType(type);
            alarmCloudStorage.setContent(content);
            int count = alarmCloudStorageMapper.insertSelective(alarmCloudStorage);
            result.put("count", count);
        }


        long endTime = System.currentTimeMillis();
        result.put("success_time", endTime - beginTime);
        System.out.println(result);
        return result;

    }

    @RequestMapping(value = "getInfoList", method = RequestMethod.POST)
    public Map<String, Object> getInfoList() {
        Map<String, Object> result = new HashMap<>(16);
        List<AlarmCloudStorage> alarmCloudStorageList = alarmCloudStorageMapper.findNewAlarmInfo();
        if (alarmCloudStorageList != null) {
            result.put("code", 200);
            result.put("data", alarmCloudStorageList);
        } else {
            result.put("code", 500);
        }
        return result;
    }

    @RequestMapping(value = "/saveDiskInfo", method = RequestMethod.GET)
    public Map<String, Object> saveDiskInfo(@RequestParam(value = "diskCount", required = false) String diskCount,
                                            @RequestParam(value = "usedCapacity", required = false) String usedCapacity,
                                            @RequestParam(value = "countCapacity", required = false) String countCapacity,
                                            @RequestParam(value = "ip", required = false) String ip
    ) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(16);
        AlarmDiskInfo diskInfo = new AlarmDiskInfo();
        diskInfo.setIp(ip);
        diskInfo.setDiskCapacityUsed(Integer.parseInt(usedCapacity));
        diskInfo.setDiskCountCapacity(Integer.parseInt(countCapacity));
        diskInfo.setDiskCount(Integer.parseInt(diskCount));
        int result = diskInfoMapper.insertSelective(diskInfo);
        if (result > 0) {
            concurrentHashMap.put("code", 200);
            concurrentHashMap.put("msg", "success");
        } else {
            concurrentHashMap.put("code", 500);
            concurrentHashMap.put("msg", "error");
        }
        return concurrentHashMap;
    }

    @RequestMapping(value = "getDiskCapacity", method = RequestMethod.POST)
    public Map<String, Object> getDiskCapacity() throws IOException, InterruptedException {
        ConcurrentHashMap result = new ConcurrentHashMap(16);
        int remainingCapacity = 0;
        int usedCapacity = 0;

        int countCapacity = 0;
        int diskCount = 0;


        List<AlarmDiskInfo> alarmDiskInfoList = diskInfoMapper.findByNewDiskInfo();

        for (AlarmDiskInfo alarmDiskInfo : alarmDiskInfoList) {
            usedCapacity += alarmDiskInfo.getDiskCapacityUsed();
            if (alarmDiskInfo.getDiskCount() != null) {
                diskCount += alarmDiskInfo.getDiskCount();
            }
            countCapacity += alarmDiskInfo.getDiskCountCapacity();
        }

        remainingCapacity = countCapacity - usedCapacity;
        result.put("countCapacity", countCapacity);
        result.put("usedCapacity", usedCapacity);
        result.put("remainingCapacity", remainingCapacity);
        result.put("diskCount", diskCount);
        return result;
    }


    @RequestMapping(value = "helloWorld")
    public Map<String, Object> helloWorld(@RequestParam(value = "cpu_usage", required = true) String cpu_usage,
                                          @RequestParam(value = "cpu_temperature", required = true) String cpu_temperature
    ) {
        Map<String, Object> map = new HashMap<>();
        AlarmCpuInfo alarmCpuInfo = new AlarmCpuInfo();
        alarmCpuInfo.setCpuUsage(cpu_usage);
        alarmCpuInfo.setCpuTemperature(cpu_temperature);
        int result = alarmCpuInfoMapper.insertSelective(alarmCpuInfo);
        if (result > 0) {
            map.put("code", 200);
            map.put("msg", "success");
        } else {
            map.put("code", 500);
            map.put("msg", "error");
        }
        return map;
    }

    @RequestMapping(value = "getCpuInfo", method = RequestMethod.POST)
    public Map<java.lang.String, Object> getCpuInfo() {
        Map<String, Object> result = new ConcurrentHashMap<>();
        AlarmCpuInfo alarmCpuInfo = alarmCpuInfoMapper.findNewCpuInfo();
        if (alarmCpuInfo != null) {
            result.put("cpu_usage_rate", alarmCpuInfo.getCpuUsage());
            result.put("cpu_temperature", alarmCpuInfo.getCpuTemperature());
            result.put("code", 200);
        } else {
            result.put("code", 500);
            result.put("msg", "获取数据失败");
        }
        return result;
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?[0-9]+.*[0-9]*");
    }


    @RequestMapping(value = "getCpuTotalAndMemory", method = RequestMethod.POST)
    public Map<String, Object> getCpuTotal() throws IOException {
        Map<String, Object> result = new HashMap<>();
        CpuMemoryInfo cpuMemoryInfo = serverService.getCpuAndMemoryInfo();
        if (cpuMemoryInfo != null) {
            result.put("code", 0);
            result.put("cpu_memory_info", cpuMemoryInfo);
        } else {
            result.put("code", 1);
        }
        return result;
    }

    @RequestMapping(value = "getServerInfo", method = RequestMethod.POST)
    public String getServerInfo() {


        return "ok";
    }

    private JSONObject getHttpJsonObject(String url, String param) {
        url = url + param;
        JSONObject serverJson = restTemplate.getForEntity(url, JSONObject.class).getBody();
        return serverJson;
    }


}
