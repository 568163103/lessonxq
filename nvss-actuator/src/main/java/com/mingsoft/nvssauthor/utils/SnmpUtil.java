package com.mingsoft.nvssauthor.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SnmpUtil {

    public static void main(String[] args) throws IOException, InterruptedException {
        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen();

        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("private"));
        target.setVersion(SnmpConstants.version2c);
        target.setAddress(new UdpAddress("192.168.0.100/161"));
        target.setTimeout(3000);    //3s
        target.setRetries(1);

//        sendRequest(snmp, createGetPdu("1.3.6.1.2.1.1.3"), target);
        sendRequest(snmp, createGetNextPdu(), target);
        sendRequest(snmp, createGetBulkPdu(), target);
        snmpWalk(snmp, target);

        target.setCommunity(new OctetString("private"));
        sendRequest(snmp, createSetPdu(), target);

        CommunityTarget broadcastTarget = new CommunityTarget();
        broadcastTarget.setCommunity(new OctetString("public"));
        broadcastTarget.setVersion(SnmpConstants.version2c);
        broadcastTarget.setAddress(new UdpAddress("192.168.0.255/161"));
        broadcastTarget.setTimeout(5000);    //5s
        sendCpuAsyncRequest(snmp, createGetNextPdu(), broadcastTarget);
        Thread.sleep(6000);    //main thread wait 6s for the completion of asynchronous request
    }

    public static PDU createGetPdu(String oid) {
        PDU pdu = new PDU();
        pdu.setType(PDU.GET);

        pdu.add(new VariableBinding(new OID(oid)));    //sysUpTime

        return pdu;
    }


    public static PDU createGetPduList(String[] oids) {
        PDU pdu = new PDU();
        pdu.setType(PDU.GET);
        for (int i = 0; i < oids.length; i++) {
            pdu.add(new VariableBinding(new OID(oids[i])));
        }
        //sysUpTime

        return pdu;
    }

    public static PDU createGetNextPdu() {
        PDU pdu = new PDU();
        pdu.setType(PDU.GETNEXT);
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.3")));    //sysUpTime
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.5")));    //sysName

        return pdu;
    }

    public static PDU createGetBulkPdu() {
        PDU pdu = new PDU();
        pdu.setType(PDU.GETBULK);
        pdu.setMaxRepetitions(10);    //must set it, default is 0
        pdu.setNonRepeaters(0);
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1")));        //system
        return pdu;
    }

    public static PDU createSetPdu() {
        PDU pdu = new PDU();
        pdu.setType(PDU.SET);
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.1.1.5.0"), new OctetString("sysname")));    //sysName
        return pdu;
    }

    public static Vector<? extends VariableBinding> sendRequest(Snmp snmp, PDU pdu, CommunityTarget target)
            throws IOException {
        ResponseEvent responseEvent = snmp.send(pdu, target);
        PDU response = responseEvent.getResponse();

        if (response == null) {
            System.out.println("TimeOut...");
            return null;
        } else {
            if (response.getErrorStatus() == PDU.noError) {
                Vector<? extends VariableBinding> vbs = response.getVariableBindings();
                System.out.println(vbs);
                return vbs;
            } else {
                System.out.println("Error:" + response.getErrorStatusText());
                return null;

            }
        }
    }

    public static void sendCpuAsyncRequest(Snmp snmp, PDU pdu, CommunityTarget target)
            throws IOException {

        snmp.send(pdu, target, null, new ResponseListener() {

            @Override
            public void onResponse(ResponseEvent event) {
                PDU response = event.getResponse();
                System.out.println("Got response from " + event.getPeerAddress());
                if (response == null) {
                    System.out.println("TimeOut...");
                } else {
                    if (response.getErrorStatus() == PDU.noError) {
                        Vector<? extends VariableBinding> vbs = response.getVariableBindings();
                        if (vbs!=null) {
                            if (StringUtils.isNotBlank(vbs.get(0).toValueString()) && StringUtils.isNotBlank(vbs.get(1).toValueString())) {
                                StringBuffer sb = new StringBuffer();
                                String str = "cpu_usage=" + vbs.get(0).toValueString() + "&cpu_temperature=" + vbs.get(1).toValueString();
                                sb.append(str);
                                HttpUtils.doPost("http://localhost:8090/api/v1/alarm/helloWorld", sb.toString());
                            }
                        }

                    } else {
                        System.out.println("Error:" + response.getErrorStatusText());
                    }
                }
            }
        });


    }

    public static void sendDiskAsyncRequest(Snmp snmp, PDU pdu, CommunityTarget target)
            throws IOException {

        snmp.send(pdu, target, null, new ResponseListener() {

            @Override
            public void onResponse(ResponseEvent event) {
                PDU response = event.getResponse();
                System.out.println("Got response from " + event.getPeerAddress());
                if (response == null) {
                    System.out.println("TimeOut...");
                } else {
                    if (response.getErrorStatus() == PDU.noError) {
                        Vector<? extends VariableBinding> vbs = response.getVariableBindings();
                        System.out.println(vbs);
                    } else {
                        System.out.println("Error:" + response.getErrorStatusText());
                    }
                }
            }
        });


    }

    public static void snmpWalk(Snmp snmp, CommunityTarget target) {
        TableUtils utils = new TableUtils(snmp, new DefaultPDUFactory(PDU.GETBULK));//GETNEXT or GETBULK
        utils.setMaxNumRowsPerPDU(5);    //only for GETBULK, set max-repetitions, default is 10
        OID[] columnOids = new OID[]{
                new OID("1.3.6.1.2.1.1.9.1.2"),    //sysORID
                new OID("1.3.6.1.2.1.1.9.1.3"),    //sysORDescr
                new OID("1.3.6.1.2.1.1.9.1.5")    //wrong OID, expect an null in in VariableBinding array
        };
        // If not null, all returned rows have an index in a range (lowerBoundIndex, upperBoundIndex]
        List<TableEvent> l = utils.getTable(target, columnOids, new OID("3"), new OID("10"));
        for (TableEvent e : l) {
            System.out.println(e);
        }
    }
    public static boolean isNumeric(String str) {
        return str.matches("-?[0-9]+.*[0-9]*");
    }

}
