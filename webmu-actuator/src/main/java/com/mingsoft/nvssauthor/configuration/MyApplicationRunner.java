package com.mingsoft.nvssauthor.configuration;

import com.mingsoft.nvssauthor.snmp.MultiThreadedTrapReceiver;
import com.mingsoft.nvssauthor.snmp.SnmpUtilSendTrap;
import com.mingsoft.nvssauthor.utils.SnmpUtil;
import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        String ip = "192.168.8.4";
//        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
//        CommunityTarget target = new CommunityTarget();
//        target.setCommunity(new OctetString("public"));
//        target.setVersion(SnmpConstants.version2c);
//        target.setAddress(new UdpAddress(ip + "/162"));
//        target.setTimeout(3000);
//        target.setRetries(1);
//        snmp.listen();
//        SnmpUtil.sendCloudStorageAsyncRequest(snmp, SnmpUtil.createGetPduList(new String[]{".1.3.6.1.4.1.99999.1.1"}), target);
//        MultiThreadedTrapReceiver multiThreadedTrapReceiver = new MultiThreadedTrapReceiver();
//        multiThreadedTrapReceiver.run();

        try {
            SnmpUtilSendTrap util = new SnmpUtilSendTrap();
            util.initComm();
            util.sendPDU();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}