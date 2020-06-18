package com.mingsoft.nvssauthor.controller.alarminfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.nvssauthor.constant.Constant;
import com.mingsoft.nvssauthor.domain.*;
import com.mingsoft.nvssauthor.mapper.*;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
    @Autowired
    private ServerMapper serverMapper;
    @Autowired
    private DmuAlarmInfoMapper dmuAlarmInfoMapper;

    @Autowired
    private TbAlarmTypeMapper tbAlarmTypeMapper;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    @GetMapping(value = "getStorageAlarmInfos")

    public String getStorageAlarmInfos(@RequestParam(value = "ip", required = true) String ip,
                                       @RequestParam(value = "level", required = true) String level,
                                       @RequestParam(value = "alarmType", required = true) String alarmType,
                                       @RequestParam(value = "desc", required = true) String desc,
                                       @RequestParam(value = "time", required = true) String time

    ) {
        System.out.println(ip + level + alarmType + desc+time);

        TbAlarmType tbAlarmType = tbAlarmTypeMapper.findByTypeId(alarmType);
        Server server = serverMapper.findByIp(ip);
        if (server != null) {
            DmuAlarmInfo dmuAlarmInfo = new DmuAlarmInfo();
            dmuAlarmInfo.setName(server.getName());
            dmuAlarmInfo.setSourceId(server.getId());
            dmuAlarmInfo.setAlarmType(alarmType);
            dmuAlarmInfo.setBeginTime(time);
            dmuAlarmInfo.setEndTime(time);
            dmuAlarmInfo.setAlarmLevel(tbAlarmType.getLevel());
            dmuAlarmInfo.setStatus("1");
            dmuAlarmInfo.setState(1);
            dmuAlarmInfo.setDealState(0);
            switch (tbAlarmType.getLevel()) {
                case 1:
                    dmuAlarmInfo.setTargetInfo("重要告警");
                    break;
                case 2:
                    dmuAlarmInfo.setTargetInfo("次要告警");
                    break;
                case 3:
                    dmuAlarmInfo.setTargetInfo("一般告警");
                    break;
            }
            dmuAlarmInfo.setDescription(desc);

            dmuAlarmInfoMapper.insertSelective(dmuAlarmInfo);
        }
        return "ok";
    }

    @PostMapping(value = "alarmInfos")
    public String getAlarmInfos(@RequestBody String receiver) {
        System.out.println(receiver);
        JSONObject resultJSONObject = JSON.parseObject(receiver);
        JSONArray resultJSONArray = resultJSONObject.getJSONArray("alerts");
        for (int i = 0; i < resultJSONArray.size(); i++) {
            System.out.println("annotations");
            if (resultJSONArray.getJSONObject(i).getJSONObject("labels").getString("instance") != null) {
                int endPosition = resultJSONArray.getJSONObject(i).getJSONObject("labels").getString("instance").indexOf(":");
                String ip = resultJSONArray.getJSONObject(i).getJSONObject("labels").getString("instance").substring(0, endPosition);
                String alarmType = resultJSONArray.getJSONObject(i).getJSONObject("annotations").getString("alrm_type");
                TbAlarmType tbAlarmType = tbAlarmTypeMapper.findByTypeId(alarmType);
                Server server = serverMapper.findByIp(ip);
                if (server != null) {
                    DmuAlarmInfo dmuAlarmInfo = new DmuAlarmInfo();
                    dmuAlarmInfo.setName(server.getName());
                    dmuAlarmInfo.setSourceId(server.getId());
                    dmuAlarmInfo.setAlarmType(alarmType);
                    dmuAlarmInfo.setBeginTime(format.format(new Date()));
                    dmuAlarmInfo.setEndTime(format.format(new Date()));
                    dmuAlarmInfo.setAlarmLevel(tbAlarmType.getLevel());
                    dmuAlarmInfo.setStatus("1");
                    dmuAlarmInfo.setState(1);
                    dmuAlarmInfo.setDealState(0);
                    switch (tbAlarmType.getLevel()) {
                        case 1:
                            dmuAlarmInfo.setTargetInfo("重要告警");
                            break;
                        case 2:
                            dmuAlarmInfo.setTargetInfo("次要告警");
                            break;
                        case 3:
                            dmuAlarmInfo.setTargetInfo("一般告警");
                            break;
                    }
                    dmuAlarmInfo.setDescription(resultJSONArray.getJSONObject(i).getJSONObject("annotations").getString("description"));

                    dmuAlarmInfoMapper.insertSelective(dmuAlarmInfo);
                }
            }
        }
        return "Ok";
    }

    private JSONObject getHttpJsonObject(String url, String param) {
        url = url + param;
        JSONObject serverJson = restTemplate.getForEntity(url, JSONObject.class).getBody();
        return serverJson;
    }

    public static void main(String[] args) throws IOException {
        //判断服务器是否在线
        int  timeOut =  3000 ;  //超时应该在3钞以上
        boolean status = InetAddress.getByName("192.168.31.109").isReachable(timeOut);     // 当返回值是true时，说明host是可用的，false则不可。
        System.out.println(status);
    }


}
