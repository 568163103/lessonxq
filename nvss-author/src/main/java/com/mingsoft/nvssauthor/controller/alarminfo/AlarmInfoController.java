package com.mingsoft.nvssauthor.controller.alarminfo;

import com.mingsoft.nvssauthor.utils.SnmpUtil;
import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@RestController
@RequestMapping("api/v1/alarm")
public class AlarmInfoController {

    @RequestMapping(value = "/alarmInfo", method = RequestMethod.POST)
    public Map<String, Object> alarmInfo(@RequestParam(value = "ips", required = false) String [] ips,
                                         @RequestParam(value = "oid", required = false) String oid
    ) throws IOException {
        Map<String,Object> result = new HashMap<>();
        ips[0]= "10.10.24.201";
        oid= "1.3.6.1.4.1.3470.12.1.6.3.0";
        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen();

        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("private"));
        target.setVersion(SnmpConstants.version2c);
        target.setAddress(new UdpAddress(ips[0] + "/161"));
        target.setTimeout(3000);
        target.setRetries(1);

        Vector<? extends VariableBinding> vbs =  SnmpUtil.sendRequest(snmp, SnmpUtil.createGetPdu(oid), target);
        if (vbs!=null){
            result.put("data",vbs.get(0).toValueString());
        }
        SnmpUtil.snmpWalk(snmp, target);
        return result;
    }

    @RequestMapping(value = "getAlarmInfo", method = RequestMethod.GET)
    public String getAlarmInfo(@RequestParam(value = "type", required = false) String type,
                               @RequestParam(value = "level", required = false) int level,
                               @RequestParam(value = "ip", required = false) String ip
                               ) throws IOException {

        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen();

        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("private"));
        target.setVersion(SnmpConstants.version2c);
        target.setAddress(new UdpAddress("10.10.24.201/161"));
        target.setTimeout(3000);
        target.setRetries(1);


        switch (type) {


        }
        SnmpUtil.snmpWalk(snmp, target);

        return "";

    }
}
