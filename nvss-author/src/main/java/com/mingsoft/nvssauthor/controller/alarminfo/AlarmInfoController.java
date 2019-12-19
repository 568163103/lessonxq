package com.mingsoft.nvssauthor.controller.alarminfo;

import com.mingsoft.nvssauthor.utils.SnmpUtil;
import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Action;
import java.io.IOException;

@RestController
@RequestMapping("api/v1/alarm")
public class AlarmInfoController {
    @Autowired
    private SnmpUtil snmpUtil;

    @RequestMapping(value = "/alarmInfo", method = RequestMethod.POST)
    public String alarmInfo() throws IOException {
        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen();

        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("private"));
        target.setVersion(SnmpConstants.version2c);
        target.setAddress(new UdpAddress("10.10.24.201/161"));
        target.setTimeout(3000);
        target.setRetries(1);

        SnmpUtil.sendRequest(snmp, SnmpUtil.createGetPdu(), target);
        SnmpUtil.snmpWalk(snmp, target);
        return "";
    }
}
