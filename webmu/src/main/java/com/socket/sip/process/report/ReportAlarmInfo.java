package com.socket.sip.process.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.DmuAlarmInfo;
import com.beyeon.nvss.dmu.model.bpo.DmuAlarmInfoBpo;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;

public class ReportAlarmInfo extends SIPSocketUtil {
    public Map<String, Object> report(Map<String, Object> requestMap) {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        String command = (String) requestMap.get("@command");
        responseMap.put("@command", command);
        HashMap<String, Object> parameters = (HashMap<String, Object>) requestMap.get("parameters");
        if (parameters != null) {
            String dmuId = (String) parameters.get("dmuId");
            HashMap<String, Object> rgroup = (HashMap<String, Object>) parameters.get("group");
            if (rgroup != null && dmuId != null) {
                HashMap<String, Object> url = (HashMap<String, Object>) rgroup.get("URL");
                DmuAlarmInfoBpo dmuAlarmInfoBpo = (DmuAlarmInfoBpo) SpringInit.getApplicationContext().getBean("dmuAlarmInfoBpo");
                String id = (String) url.get("id");
                String type = (String) url.get("type");
                String startTime = (String) url.get("startTime");
                String endTime = (String) url.get("endTime");
                String targetInfo = (String) url.get("targetInfo");
                Integer level = Integer.parseInt(String.valueOf(url.get("level")));
                Integer state = Integer.parseInt(String.valueOf(url.get("state")));
                String description = (String) url.get("description");
                String ref_id = id.substring(0, 16);
                String alarm_type = id.substring(16, 21);
                DmuAlarmInfo info = new DmuAlarmInfo();
                info.setSourceId(ref_id);
                info.setAlarmType(alarm_type);
                info.setBeginTime(startTime);
                info.setEndTime(endTime);
                info.setDescription(description);
                info.setState(state);
                info.setLevel(level);
                info.setTargetInfo(targetInfo);
                info.setName(type);
                info.setStatus("0");
                List list = dmuAlarmInfoBpo.findByParams(ref_id, alarm_type, startTime);
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        DmuAlarmInfo dmuAlarmInfo = (DmuAlarmInfo) list.get(i);
                        dmuAlarmInfoBpo.updateState(dmuAlarmInfo.getId(), state, "0");
                    }
                } else {
                    dmuAlarmInfoBpo.save(info);
                }
                addResult(responseMap, "0", "success");
            } else {
                addResult(responseMap, "37", "parameter error"); //参数错误
            }
        } else {
            addResult(responseMap, "37", "parameter error"); //参数错误
        }
        return responseMap;

    }
}
