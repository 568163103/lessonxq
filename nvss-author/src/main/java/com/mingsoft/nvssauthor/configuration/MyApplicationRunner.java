package com.mingsoft.nvssauthor.configuration;

import com.mingsoft.nvssauthor.constant.Constant;
import com.mingsoft.nvssauthor.utils.SnmpUtil;
import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String ip = "192.168.8.230";
        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("private"));
        target.setVersion(SnmpConstants.version2c);
        target.setAddress(new UdpAddress(ip + "/161"));
        target.setTimeout(3000);
        target.setRetries(1);
        snmp.listen();
        while (true) {
            TimeUnit.SECONDS.sleep(5);
            SnmpUtil.sendCpuAsyncRequest(snmp, SnmpUtil.createGetPduList(new String[]{Constant.CPU_USAGE_RATE, Constant.CPU_TEMPERATURE}), target);
        }

    }


}
